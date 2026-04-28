package com.daw.cinemadaw.repository;

import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.cinemadaw.domain.menjar.Menjar;

public interface MenjarRepository extends JpaRepository<Menjar, Long> {
    
}
