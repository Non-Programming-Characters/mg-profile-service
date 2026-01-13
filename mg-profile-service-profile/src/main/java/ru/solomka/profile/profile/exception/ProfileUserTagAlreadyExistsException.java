package ru.solomka.profile.profile.exception;

public class ProfileUserTagAlreadyExistsException extends RuntimeException {
    public ProfileUserTagAlreadyExistsException(String message) {
        super(message);
    }
}
