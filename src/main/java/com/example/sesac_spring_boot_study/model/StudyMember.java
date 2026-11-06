package com.example.sesac_spring_boot_study.model;

public class StudyMember {

    public enum Status {
        CONFIRMED("확정"),
        WAITING("대기");

        private final String label;

        Status(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    private final long id;
    private final String name;
    private Status status;

    public StudyMember(long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isConfirmed() {
        return status == Status.CONFIRMED;
    }

    public boolean isWaiting() {
        return status == Status.WAITING;
    }
}