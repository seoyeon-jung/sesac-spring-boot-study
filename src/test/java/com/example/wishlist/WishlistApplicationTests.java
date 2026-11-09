package com.example.wishlist;

import com.example.wishlist.common.exception.ItemNotFoundException;
import com.example.wishlist.item.controller.WishlistController;
import com.example.wishlist.item.dto.ItemCreateRequest;
import com.example.wishlist.item.dto.ItemResponse;
import com.example.wishlist.item.dto.RecommendationResponse;
import com.example.wishlist.item.model.WishlistItem;
import com.example.wishlist.item.repository.WishlistItemRepository;
import com.example.wishlist.item.repository.WishlistItemRepositoryImpl;
import com.example.wishlist.item.service.WishlistService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class WishlistApplicationTests {

	@Autowired
	WishlistService service;

	@Autowired
	WishlistController controller;

	@Autowired
	WishlistItemRepository repository;

	@Test
	@DisplayName("Spring이 Repository interface에 메모리 구현체를 주입한다")
	void repositoryIsWired() {
		assertThat(repository).isInstanceOf(WishlistItemRepositoryImpl.class);
	}

	@Test
	@DisplayName("Spring이 연결한 Service로 등록 > 조회 > 추천 동작한다")
	void serviceFlowWithSpringBeans() {
		WishlistItem saved = service.register("지구 끝의 온실", 18000);
		service.register("무료 전자책 샘플", 0);
		service.register("어린 왕자", 5000);

		assertThat(service.findById(saved.getId()).getName()).isEqualTo("지구 끝의 온실");
		assertThat(repository.findAll()).hasSize(3);

		List<WishlistItem> result = service.recommend(8000);
		assertThat(result).extracting(WishlistItem::getName)
			.containsExactly("무료 전자책 샘플", "어린 왕자");
	}

	@Test
	@DisplayName("Controller > Service > Repository 연동 성공, DTO 응답 성공")
	void progressSuccess() {
		ItemResponse saved = controller.register(new ItemCreateRequest(" 지구 끝의 온실 ", 18000));
		controller.register(new ItemCreateRequest("무료 전자책 샘플", 0));

		assertThat(saved.name()).isEqualTo("지구 끝의 온실");
		assertThat(controller.findById(saved.id()).price()).isEqualTo(18000);
		assertThat(controller.findAll()).hasSize(2);
		assertThatThrownBy(() -> controller.findById(999L))
			.isInstanceOf(ItemNotFoundException.class);

		RecommendationResponse recommendation = controller.recommend(10000);
		assertThat(recommendation.budget()).isEqualTo(10000);
		assertThat(recommendation.count()).isEqualTo(1);
		assertThat(recommendation.items()).extracting(ItemResponse::name)
			.containsExactly("무료 전자책 샘플");
	}

}
