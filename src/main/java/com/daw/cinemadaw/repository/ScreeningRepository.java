package com.daw.cinemadaw.repository;

import com.daw.cinemadaw.domain.cinema.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

    @Repository
    public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    }

