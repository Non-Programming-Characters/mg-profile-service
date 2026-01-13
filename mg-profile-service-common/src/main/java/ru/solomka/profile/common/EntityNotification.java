package ru.solomka.profile.common;

public interface EntityNotification<E extends Entity> {

    void notifyCreate(E entity);

    void notifyUpdate(E entity);

    void notifyDelete(E entity);
}