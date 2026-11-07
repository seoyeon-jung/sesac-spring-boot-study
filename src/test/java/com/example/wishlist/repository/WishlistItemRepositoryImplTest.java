package com.example.wishlist.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.wishlist.model.WishlistItem;
import java.util.List;
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
    @DisplayName("전체 목록은 저자된 순서대로 반환된다")
    void findAllKeepsOrder() {
        repository.save(new WishlistItem(null, "지구 끝의 온실", 18000));
        repository.save(new WishlistItem(null, "프로젝트 헤일메리", 30000));
        repository.save(new WishlistItem(null, "동물농장", 8000));

        assertThat(repository.findAll()).extracting(WishlistItem::getName)
            .containsExactly("지구 끝의 온실", "프로젝트 헤일메리", "동물농장");
    }
}
