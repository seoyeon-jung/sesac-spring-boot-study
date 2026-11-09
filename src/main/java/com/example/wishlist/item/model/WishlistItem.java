package com.example.wishlist.item.model;

public class WishlistItem {

    private final Long id;
    private final String name;
    private final int price;

    public WishlistItem(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "WishlistItem{id=" + id + ", name='" + name + "', price=" + price + "}";
    }

    // id 부여할때 같은 내용에 id만 붙여서 새로운 객체 생성
    public WishlistItem withId(Long id) {
        return new WishlistItem(id, this.name, this.price);
    }
}
