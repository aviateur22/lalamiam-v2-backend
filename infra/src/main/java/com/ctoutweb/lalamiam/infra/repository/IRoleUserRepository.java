package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.RoleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleUserRepository extends JpaRepository<RoleUserEntity, Long> {
}
