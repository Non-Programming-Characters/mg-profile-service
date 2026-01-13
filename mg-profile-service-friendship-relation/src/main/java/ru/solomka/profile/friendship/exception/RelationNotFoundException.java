package ru.solomka.profile.friendship.exception;

public class RelationNotFoundException extends RuntimeException {
    public RelationNotFoundException(String message) {
        super(message);
    }
}
