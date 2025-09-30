package ru.solomka.profile.principal;

import java.util.Optional;

public interface PrincipalRepository {

    Optional<PrincipalEntity> findPrincipal();
    PrincipalEntity setPrincipal(PrincipalEntity principal);
    boolean isAuthenticated();
}