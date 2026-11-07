package com.example.wishlist.common.exception;

public class InvalidBudgetException extends RuntimeException {

    public InvalidBudgetException(Integer budget) {
        super("예산은 0 이상의 정수여야 합니다. budget=" + budget);
    }
}
