package com.university.homework.film_distribution.repositories;

import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepo extends JpaRepository<GenreEntity, Long> {

    Optional<GenreEntity> findByName(String name);

    boolean existsByName(String name);

}
