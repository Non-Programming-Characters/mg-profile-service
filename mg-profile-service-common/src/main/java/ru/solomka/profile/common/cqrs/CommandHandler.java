package ru.solomka.profile.common.cqrs;

public interface CommandHandler<A, R> {
    R handle(A command);
}