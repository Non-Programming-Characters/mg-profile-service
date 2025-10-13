package ru.solomka.profile.spring.configuration.exception;

import org.jetbrains.annotations.NotNull;

public interface ExceptionFormatProvider {
    @NotNull ExceptionFormat create(@NotNull Exception exception);

    boolean supports(@NotNull Exception exception);
}
