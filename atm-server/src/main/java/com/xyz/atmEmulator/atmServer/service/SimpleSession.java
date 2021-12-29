package com.xyz.atmEmulator.atmServer.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SimpleSession<K, V> {

    private HashMap<K, V> session;

    public SimpleSession() {
        session = new HashMap<>();
    }

    public void put(K k, V v){
        session.put(k, v);
    }

    public Boolean containsKey(K k){
        return session.containsKey(k);
    }

    public V get(K k){
        return session.get(k);
    }

    public V remove(K k){
        return session.remove(k);
    }
}
