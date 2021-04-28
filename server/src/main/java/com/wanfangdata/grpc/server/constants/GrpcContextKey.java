package com.wanfangdata.grpc.server.constants;

import io.grpc.Context;

import java.util.Map;

public class GrpcContextKey {

    public static final Context.Key<Map<String,String>> USER_META_KEY = Context.key("meta");

    public static final String CLIENT_IP = "CLIENT_IP";
    public static final String TICKIT = "TICKIT";
    public static final String USERPATH = "users";

}