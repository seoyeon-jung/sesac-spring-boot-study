package com.example.sesac_spring_boot_study.service;

import com.example.sesac_spring_boot_study.model.StudyMember;
import com.example.sesac_spring_boot_study.model.StudyMember.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class StudyService {

    private static final int MIN_CAPACITY = 1;
    private static final int MAX_CAPACITY = 10;

    private final List<StudyMember> members = new ArrayList<>();
    private long nextId = 1;
    private int capacity = 3; // 기존 capacity 3

    // 참가 신청
    public synchronized String apply(String rawName) {
        if (rawName == null || rawName.trim().isEmpty()) {
            return "[신청 실패] 이름을 입력해주세요.";
        }

        String name = rawName.trim();

        if (findByName(name) != null) {
            return "[신청 실패] 이미 신청한 이름입니다: " + name;
        }

        Status status = countConfirmed() < capacity ? Status.CONFIRMED : Status.WAITING;
        StudyMember member = new StudyMember(nextId++, name, status);
        members.add(member);

        String result = "[신청 성공]" + format(member);
        if (member.isWaiting()) {
            result += "(대기 " + waitingOrder(member) + "번째)";
        }

        return result;
    }

    // 명단 조회
    public synchronized String getParticipants() {
        if (members.isEmpty()) return "참가자가 없습니다";

        StringBuilder sb = new StringBuilder();
        sb.append("정원 ").append(capacity).append("명 | 확정 ").append(countConfirmed())
            .append("명 | 대기 ").append(members.size() - countConfirmed()).append("명\n");
        for (StudyMember member : members) {
            sb.append(format(member)).append("\n");
        }
        return sb.toString().trim();
    }

    // 취소
    public synchronized String cancel(String rawMemberId) {
        if (rawMemberId == null || rawMemberId.trim().isEmpty()) {
            return "[취소 실패] 취소할 id를 입력해주세요.";
        }

        long memberId;
        try {
            memberId = Long.parseLong(rawMemberId.trim());
        } catch (NumberFormatException e) {
            return "[취소 실패] id는 숫자로 입력해 주세요: " + rawMemberId.trim();
        }

        StudyMember target = findById(memberId);
        if (target == null) {
            return "[취소 실패] 해당 id의 참가자가 없습니다: " + memberId;
        }

        boolean wasConfirmed = target.isConfirmed();
        members.remove(target);
        String result = "[취소 성공] " + format(target);

        if (wasConfirmed) {
            List<StudyMember> promoted = promoteWaiting();
            if (!promoted.isEmpty()) {
                result += "\n[승격]" + format(promoted.getFirst());
            }
        }

        return result;
    }

    // capacity 변경
    public String changeCapacity(String rawCapacity) {
        if (rawCapacity == null || rawCapacity.trim().isEmpty()) {
            return "[정원 변경 실패] 정원을 입력해주세요. (현재 정원 " + capacity + "명)";
        }
        int newCapacity;
        try {
            newCapacity = Integer.parseInt(rawCapacity.trim());
        } catch (NumberFormatException e) {
            return "[정원 변경 실패] 정원은 " + MIN_CAPACITY + "~" + MAX_CAPACITY + " 사이의 정수로 입력해 주세요. (현재 정원 " + capacity + "명)";
        }

        if (newCapacity < MIN_CAPACITY || newCapacity > MAX_CAPACITY) {
            return "[정원 변경 실패] 정원은 " + MIN_CAPACITY + "~" + MAX_CAPACITY + " 사이여야 합니다. (현재 정원 " + capacity + "명)";
        }

        int confirmed = countConfirmed();
        if (newCapacity < confirmed) {
            return "[정원 변경 실패] 현재 확정 인원(" + confirmed + "명)보다 작게 줄일 수 없습니다. (현재 정원 "
                + capacity + "명)";
        }
        if (newCapacity == capacity) {
            return "[정원 변경 없음] 이미 정원이 " + capacity + "명입니다.";
        }

        int before = capacity;
        capacity = newCapacity;
        List<StudyMember> promoted = promoteWaiting();

        StringBuilder sb = new StringBuilder();
        sb.append("[정원 변경 성공] ").append(before).append("명 → ").append(capacity).append("명");
        for (StudyMember member : promoted) {
            sb.append("\n[승격] ").append(format(member));
        }
        return sb.toString();
    }

    // 빈자리 수만큼 대기 -> 확정 변경
    // 바뀐 사람들 목록 return
    private List<StudyMember> promoteWaiting() {
        List<StudyMember> promoted = new ArrayList<>();
        int emptySeats = capacity - countConfirmed();
        for (StudyMember member : members) {
            if (emptySeats <= 0) {
                break;
            }
            if (member.isWaiting()) {
                member.setStatus(Status.CONFIRMED);
                promoted.add(member);
                emptySeats--;
            }
        }
        return promoted;
    }

    private StudyMember findById(long id) {
        for (StudyMember m : members) {
            if (m.getId() == id) {
                return m;
            }
        }
        return null;
    }

    private int waitingOrder(StudyMember target) {
        int order = 0;
        for (StudyMember m : members) {
            if (m.isWaiting()) {
                order++;
            }
            if (m == target) {
                return order;
            }
        }
        return order;
    }

    private String format(StudyMember member) {
        return "id= " + member.getId() + " | " + member.getName() + " | " + member.getStatus().getLabel();
    }

    private int countConfirmed() {
        int count = 0;
        for (StudyMember m : members) {
            if (m.isConfirmed()) count++;
        }
        return count;
    }

    private StudyMember findByName(String name) {
        for (StudyMember m : members) {
            if (Objects.equals(m.getName(), name)) {
                return m;
            }
        }
        return null;
    }

}
