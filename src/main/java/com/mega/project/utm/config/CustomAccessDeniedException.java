package com.mega.project.utm.config;

public class CustomAccessDeniedException extends RuntimeException {
    public CustomAccessDeniedException() {
    }

    /**
     * Navigation exception thrown when routing fails due to a faulty navigation
     * target string.
     *
     * @param message
     *                the detail message. The detail message is saved for later
     *                retrieval by the {@link #getMessage()} method.
     */
    public CustomAccessDeniedException(String message) {
        super(message);
    }
}
