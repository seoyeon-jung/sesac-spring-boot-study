package com.example.wishlist.dto;

import com.example.wishlist.model.WishlistItem;
import java.util.List;

public record RecommendationResponse (int budget, int count, List<ItemResponse> items) {

    public static RecommendationResponse of(int budget, List<WishlistItem> recommended) {
        List<ItemResponse> items = recommended.stream()
            .map(ItemResponse::from)
            .toList();

        return new RecommendationResponse(budget, items.size(), items);
    }
}
