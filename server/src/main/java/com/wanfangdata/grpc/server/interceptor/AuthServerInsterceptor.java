package com.wanfangdata.grpc.server.interceptor;


import com.wanfangdata.grpc.server.constants.GrpcContextKey;
import com.wanfangdata.grpc.server.util.AuthUtil;
import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import net.sf.json.JSONObject;
import org.bigdata.framework.utils.HttpUrl;
import org.bigdata.framework.utils.IPConvertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import java.util.HashMap;
import java.util.Map;

@GrpcGlobalServerInterceptor
public class AuthServerInsterceptor implements ServerInterceptor {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${auth.url}")
    private String authUrl;

    private static final  Metadata.Key<String> originalPath = Metadata.Key.of("x-envoy-original-path", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> client = Metadata.Key.of("x-forwarded-for", Metadata.ASCII_STRING_MARSHALLER);
    private static final Metadata.Key<String> cookie = Metadata.Key.of("Cookie", Metadata.ASCII_STRING_MARSHALLER);

//    private static final String cookieKey = "CASTGC=";


    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call,
                                                                 Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        //是否请求用户信息接口
        String path= headers.get(originalPath);
        if(path!=null&&!path.contains(GrpcContextKey.USERPATH)){
            Context ctx=Context.current().withValue( GrpcContextKey.USER_META_KEY, null);
            return Contexts.interceptCall(ctx, call, headers, next);
        }
        //获取客户端参数
        Map<String,String> meta =new HashMap();
        String ticket= AuthUtil.getTicket(headers.get(cookie));
        String clientAddr= getClientIp(headers);
        meta.put(GrpcContextKey.CLIENT_IP,clientAddr);
        meta.put(GrpcContextKey.TICKIT,ticket);
        logger.info("client IP:{} ticket:{} ",clientAddr,ticket);

        //已有Cookie信息,直接传递接口处理
        if(ticket!=null){
            Context ctx=Context.current().withValue( GrpcContextKey.USER_META_KEY, meta);
            return Contexts.interceptCall(ctx, call, headers, next);
        }

        //无Cookie信息，检查是否有IP自动登录用户，无直接传递处理
        Object ipUser = HttpUrl.httpGetUrl(authUrl + "rest/ip.json?ip_num=" + IPConvertHelper.IPToNumber(clientAddr)).get("userList");
        if(ipUser==null||"[]".equals(ipUser.toString())){
            Context ctx=Context.current().withValue( GrpcContextKey.USER_META_KEY, meta);
           return Contexts.interceptCall(ctx, call, headers, next);
        }
        //有IP登录用户，去登录
        ticket=getIPLogIn(clientAddr,headers);
        meta.put(GrpcContextKey.CLIENT_IP,clientAddr);
        meta.put(GrpcContextKey.TICKIT,ticket);
        Context ctx=Context.current().withValue( GrpcContextKey.USER_META_KEY, meta);

        //传递给下层接口TICKET信息，并写回客户端Cookie参数,
        final String cookieString=ticket;
        ServerCall<ReqT, RespT> serverCall = new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
            @Override
            public void sendHeaders(Metadata headers) {
                headers.put(cookie,cookieString);
                super.sendHeaders(headers);
            }
        };
        return Contexts.interceptCall(ctx, serverCall, headers, next);
    }

    /**
     * 根据Header获取客户端IP
     * */
    private String getClientIp(Metadata headers){
        String clientAddr= headers.get(client);
        if(clientAddr!=null&&clientAddr.contains(",")){
            clientAddr=clientAddr.split(",")[0];
        }
        return clientAddr;
    }
    /**
     * 进行IP登录
     * */
    private String getIPLogIn(String clientAddr,Metadata headers){
        String ticket="";
        JSONObject loginTicket = HttpUrl.httpGetUrl(authUrl + "user/iplogin.do?ticket=&ip_num=" + IPConvertHelper.IPToNumber(clientAddr));
        if (loginTicket != null && loginTicket.size() > 0 && "200".equals(loginTicket.get("status").toString())) {
            ticket=loginTicket.get("ticketGrantingTicket").toString();
        }
        return ticket;
    }
}