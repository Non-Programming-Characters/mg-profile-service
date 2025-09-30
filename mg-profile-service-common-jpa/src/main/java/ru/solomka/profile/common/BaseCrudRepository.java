package ru.solomka.profile.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseCrudRepository<I> extends CrudRepository<I, UUID> {
}