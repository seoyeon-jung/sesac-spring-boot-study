package com.example.wishlist.item.service;

import com.example.wishlist.item.exception.InvalidBudgetException;
import com.example.wishlist.item.exception.InvalidItemException;
import com.example.wishlist.item.exception.ItemNotFoundException;
import com.example.wishlist.item.model.WishlistItem;
import com.example.wishlist.item.repository.WishlistItemRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishlistService {

    private static final int MAX_RECOMMENDATION = 3;
    private final WishlistItemRepository repository;

    public WishlistService(WishlistItemRepository repository) {
        this.repository = repository;
    }

    // item 등록
    public WishlistItem register(String name, Integer price) {
        String savedName = validateName(name);
        validatePrice(price);
        return repository.save(new WishlistItem(null, savedName, price));
    }

    // 전체 조회
    public List<WishlistItem> findAll() {
        return repository.findAll();
    }

    // 개별 조회
    public WishlistItem findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ItemNotFoundException(id));
    }

    // 추천
    public List<WishlistItem> recommend(Integer budget) {
        if (budget == null || budget < 0) {
            throw new InvalidBudgetException(budget);
        }

        return repository.findAll().stream()
            .filter(item -> item.getPrice() <= budget)
            .sorted(Comparator.comparingInt(WishlistItem::getPrice))
            .limit(MAX_RECOMMENDATION)
            .toList();
    }

    private void validatePrice(Integer price) {
        if (price == null || price < 0) {
            throw new InvalidItemException("가격은 0 이상의 정수여야 합니다.");
        }
    }

    private String validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidItemException("이름은 비어있을 수 없습니다.");
        }
        return name.strip();
    }

}
