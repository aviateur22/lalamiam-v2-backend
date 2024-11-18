package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.DelayLoginEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDelayLoginRepository extends JpaRepository<DelayLoginEntity, Long> {
}
