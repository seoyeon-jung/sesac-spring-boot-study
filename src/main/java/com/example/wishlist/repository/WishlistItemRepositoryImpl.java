package com.example.wishlist.repository;

import com.example.wishlist.model.WishlistItem;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WishlistItemRepositoryImpl implements WishlistItemRepository{

    private final Map<Long, WishlistItem> store = new LinkedHashMap<>();
    private long sequence = 0L;

    @Override
    public synchronized WishlistItem save(WishlistItem item) {
        WishlistItem saved = item.withId(++sequence);
        store.put(saved.getId(), saved);
        return saved;
    }

    @Override
    public synchronized List<WishlistItem> findAll() {
        return List.copyOf(new ArrayList<>(store.values()));
    }

    @Override
    public synchronized Optional<WishlistItem> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
}
