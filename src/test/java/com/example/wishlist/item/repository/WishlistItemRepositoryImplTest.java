package com.example.wishlist.item.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.wishlist.item.domain.WishlistItem;
import com.example.wishlist.item.repository.WishlistItemRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WishlistItemRepositoryImplTest {

    private WishlistItemRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        repository = new WishlistItemRepositoryImpl();
    }

    @Test
    @DisplayName("저장하면 1부터 차례로 id 증가한다")
    void saveSequentialId() {
        WishlistItem first = repository.save(new WishlistItem(null, "지구 끝의 온실", 18000));
        WishlistItem second = repository.save(new WishlistItem(null, "프로젝트 헤일메리", 30000));

        assertThat(first.getId()).isEqualTo(1L);
        assertThat(second.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("저장해도 넘겨준 객체는 바뀌지 않고 id가 붙은 새 객체가 반환된다")
    void saveReturnsNewObject() {
        WishlistItem input = new WishlistItem(null, "동물농장", 8000);
        WishlistItem saved = repository.save(input);

        assertThat(input.getId()).isNull();
        assertThat(saved).isNotSameAs(input);
        assertThat(saved.getName()).isEqualTo("동물농장");
        assertThat(saved.getPrice()).isEqualTo(8000);
    }

    @Test
    @DisplayName("전체 목록은 저장된 순서대로 반환된다")
    void findAllKeepsOrder() {
        repository.save(new WishlistItem(null, "지구 끝의 온실", 18000));
        repository.save(new WishlistItem(null, "프로젝트 헤일메리", 30000));
        repository.save(new WishlistItem(null, "동물농장", 8000));

        assertThat(repository.findAll()).extracting(WishlistItem::getName)
            .containsExactly("지구 끝의 온실", "프로젝트 헤일메리", "동물농장");
    }

    @Test
    @DisplayName("수정해도 등록 순서는 그대로이고, 삭제하면 목록에서 빠진다")
    void updateKeepsOrderAndDeleteRemoves() {
        WishlistItem first = repository.save(new WishlistItem(null, "지구 끝의 온실", 18000));
        WishlistItem second = repository.save(new WishlistItem(null, "동물농장", 8000));

        repository.update(new WishlistItem(first.getId(), "지구 끝의 온실 (개정판)", 19000));
        boolean deleted = repository.deleteById(second.getId());

        assertThat(deleted).isTrue();
        assertThat(repository.deleteById(second.getId())).isFalse();
        assertThat(repository.findAll()).extracting(WishlistItem::getName)
            .containsExactly("지구 끝의 온실 (개정판)");
    }

    @Test
    @DisplayName("외부 상품 id로 가져온 아이템을 찾을 수 있다")
    void findByExternalId() {
        repository.save(new WishlistItem(null, "직접 등록", 1000));
        repository.save(WishlistItem.imported(7L, "외부 상품", 9990, 3));

        assertThat(repository.findByExternalId(7L)).isPresent();
        assertThat(repository.findByExternalId(8L)).isEmpty();
    }
}