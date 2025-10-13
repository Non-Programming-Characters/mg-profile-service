package ru.solomka.profile.spring.configuration.exception;

import org.jetbrains.annotations.NotNull;

public interface ExceptionFormatFactory {
    @NotNull ExceptionFormat create(@NotNull Exception var1);
}
