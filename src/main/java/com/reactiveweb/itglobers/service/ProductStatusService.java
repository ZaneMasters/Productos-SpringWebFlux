package com.reactiveweb.itglobers.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProductStatusService {

    @Cacheable("productStatus")
    public String getStatusName(Integer status) {
        Map<Integer, String> statusCache = new ConcurrentHashMap<>();
        statusCache.put(1, "Active");
        statusCache.put(0, "Inactive");

        return statusCache.getOrDefault(status, "Unknown");
    }
}