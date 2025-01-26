package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.ProfessionalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProfessionalRepository extends JpaRepository<ProfessionalEntity, Long> {
}
