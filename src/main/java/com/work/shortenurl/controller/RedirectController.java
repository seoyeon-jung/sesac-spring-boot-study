package com.work.shortenurl.controller;

import com.work.shortenurl.service.ShortenUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final ShortenUrlService shortenUrlService;

    @GetMapping("/{shortenUrlKey:[a-zA-Z0-9]{8}}")
    public ResponseEntity<Void> redirect(@PathVariable String shortenUrlKey) {
        String originalUrl = shortenUrlService.getOriginalUrlForRedirect(shortenUrlKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
