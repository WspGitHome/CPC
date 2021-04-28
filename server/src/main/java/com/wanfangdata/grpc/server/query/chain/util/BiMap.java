package com.wanfangdata.grpc.server.query.chain.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Bidirectional Map 的模拟实现
 * Google guava有标准实现，但是使用麻烦，这里应用场景简单故模拟实现
 *
 * Created on 2019-11-20
 *
 * @author FLY
 */
public class BiMap<K,V> {

    Map<K,V> map = new HashMap<K, V>();
    Map<V,K> inversedMap = new HashMap<V, K>();

    void put(K k, V v) {
        map.put(k, v);
        inversedMap.put(v, k);
    }

    V get(K k) {
        return map.get(k);
    }

    K getKey(V v) {
        return inversedMap.get(v);
    }

}
