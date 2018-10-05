package com.cafe24.app.discount.core.store;

import com.cafe24.app.discount.dto.DiscountInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 할인 정보 저장소
 */
public class StoreDiscount implements Store<List<DiscountInfo>> {
    private Map<String, List<DiscountInfo>> store = new ConcurrentHashMap<>();

    @Override
    public List<DiscountInfo> get(String key) {
        return this.store.get(key);
    }

    @Override
    public void put(String key, List<DiscountInfo> value) {
        this.store.put(key, value);
    }

    @Override
    public boolean contains(String key) {
        return this.store.containsKey(key);
    }
}
