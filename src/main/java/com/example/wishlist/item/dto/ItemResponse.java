package com.example.wishlist.item.dto;

import com.example.wishlist.item.model.WishlistItem;

public record ItemResponse(Long id, String name, int price) {
    public static ItemResponse from(WishlistItem item) {
        return new ItemResponse(item.getId(), item.getName(), item.getPrice());
    }
}
