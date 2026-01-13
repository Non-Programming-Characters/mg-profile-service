package ru.solomka.profile.friendship.exception;

public class RelationAlreadyExists extends RuntimeException {
    public RelationAlreadyExists(String message) {
        super(message);
    }
}