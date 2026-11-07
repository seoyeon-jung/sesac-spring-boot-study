package com.example.wishlist.repository;

import com.example.wishlist.model.WishlistItem;
import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository {
    // item 저장
    WishlistItem save(WishlistItem item);

    // 전체 목록
    List<WishlistItem> findAll();

    // id로 item 찾기
    Optional<WishlistItem> findById(Long id);
}
