package com.wanfangdata.grpc.server.service;

import com.wanfangdata.cas.entity.User;
import com.wanfangdata.grpc.*;
import com.wanfangdata.grpc.server.constants.GrpcContextKey;
import com.wanfangdata.grpc.server.util.AuthUtil;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import net.sf.json.JSONObject;
import org.bigdata.framework.utils.HttpUrl;
import org.bigdata.framework.utils.IPConvertHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${auth.url}")
    private String authUrl;

    @Override
    public void getUsers(UsersRequest request, StreamObserver<UsersResponse> responseObserver) {
        UsersResponse.Builder usersResponse = UsersResponse.newBuilder();
        Map<String, String> meta = GrpcContextKey.USER_META_KEY.get();
        if (meta == null) {
            responseObserver.onNext(usersResponse.build());
            responseObserver.onCompleted();
            return;
        }
        Map userInfo = HttpUrl.httpGetUrl(authUrl + "rest/list.json?token=" + meta.get(GrpcContextKey.TICKIT) + "&ip=" + meta.get(GrpcContextKey.CLIENT_IP) + "&token_type=PC");
        if (userInfo.size() < 1) {
            responseObserver.onNext(usersResponse.build());
            responseObserver.onCompleted();
            return;
        }
        List<User> userList = null;
        try {
            JSONObject userInfoJson = (JSONObject) userInfo;
            userList = AuthUtil.getUserList(userInfoJson);
        } catch (Exception e) {
            logger.error("获取用户信息出错");
        }
        if (userList == null || userList.size() < 1) {
            responseObserver.onNext(usersResponse.build());
            responseObserver.onCompleted();
            return;
        }
        for (User user : userList) {
            if (user.getUserType() != null && user.getUserType() == 10) {
                continue;
            }
            Users userGrpc = Users.newBuilder()
                    .setUserId(user.getUserId())
                    .setNickName(user.getUserNickname())
                    .setUserName(user.getUserRealName())
                    .setInstitution(user.getInstitution())
                    .setUserType(String.valueOf(user.getUserType()))
                    .build();
            usersResponse.addUsers(userGrpc);
        }
        logger.info("当前用户信息：{}", userList);
        responseObserver.onNext(usersResponse.build());
        responseObserver.onCompleted();
    }

    public static void main(String[] args) {
        System.out.println(IPConvertHelper.IPToNumber("106.37.240.38"));

    }
}