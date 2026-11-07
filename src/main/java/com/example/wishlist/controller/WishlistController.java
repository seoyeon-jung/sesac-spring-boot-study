package com.example.wishlist.controller;

import com.example.wishlist.dto.ItemCreateRequest;
import com.example.wishlist.dto.ItemResponse;
import com.example.wishlist.dto.RecommendationResponse;
import com.example.wishlist.service.WishlistService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(WishlistService service) {
        this.service = service;
    }

    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse register (@RequestBody ItemCreateRequest request) {
        return ItemResponse.from(service.register(request.name(), request.price()));
    }

    @GetMapping("/items")
    public List<ItemResponse> findAll() {
        return service.findAll().stream()
            .map(ItemResponse::from)
            .toList();
    }

    @GetMapping("/items/:id")
    public ItemResponse findById(@PathVariable Long id) {
        return ItemResponse.from(service.findById(id));
    }

    @GetMapping("/recommendations")
    public RecommendationResponse recommend(@RequestParam Integer budget) {
        return RecommendationResponse.of(budget, service.recommend(budget));
    }
}
