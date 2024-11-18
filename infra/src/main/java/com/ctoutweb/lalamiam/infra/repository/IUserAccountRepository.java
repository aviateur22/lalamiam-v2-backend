package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccountEntity, Long> {
}
