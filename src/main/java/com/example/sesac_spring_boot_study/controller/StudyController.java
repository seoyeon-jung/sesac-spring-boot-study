package com.example.sesac_spring_boot_study.controller;

import com.example.sesac_spring_boot_study.service.StudyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/study", produces = "text/plain;charset=UTF-8")
public class StudyController {

    private final StudyService service;

    public StudyController(StudyService service) {
        this.service = service;
    }

    // 참가 신청
    @PostMapping("/participants")
    public String apply(@RequestParam(name = "name", required = false) String name) {
        return service.apply(name);
    }

    // 명단 조회
    @GetMapping("/participants")
    public String participants() {
        return service.getParticipants();
    }

    // 취소
    @PostMapping("/cancel")
    public String cancel(@RequestParam(name = "memberId", required = false) String memberId) {
        return service.cancel(memberId);
    }

    // 정원 변경
    @PostMapping("/capacity")
    public String changeCapacity(@RequestParam(name = "capacity", required = false) String capacity) {
        return service.changeCapacity(capacity);
    }

}
