package com.work.shortenurl.exception;

public class NotFoundShortenUrlException extends RuntimeException {
    public NotFoundShortenUrlException(String shortenUrlKey) {
        super("단축 URL을 찾을 수 없습니다. key=" + shortenUrlKey);
    }
}