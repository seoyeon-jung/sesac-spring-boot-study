package com.work.shortenurl.exception;

public class LackOfShortenUrlKeyException extends RuntimeException {
    public LackOfShortenUrlKeyException() {
        super("단축 URL 키 생성에 실패했습니다. 잠시 후 다시 시도해주세요.");
    }
}