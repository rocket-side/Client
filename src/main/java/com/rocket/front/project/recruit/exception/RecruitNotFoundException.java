package com.rocket.front.project.recruit.exception;

public class RecruitNotFoundException extends RuntimeException {

    public RecruitNotFoundException() {
        super();
    }

    public RecruitNotFoundException(String message) {
        super(message);
    }

    public RecruitNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}