package com.example.wishlist.dto;

import com.example.wishlist.model.WishlistItem;

public record ItemResponse(Long id, String name, int price) {
    public static ItemResponse from(WishlistItem item) {
        return new ItemResponse(item.getId(), item.getName(), item.getPrice());
    }
}
