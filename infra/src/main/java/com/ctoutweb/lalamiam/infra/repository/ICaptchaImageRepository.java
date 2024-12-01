package com.ctoutweb.lalamiam.infra.repository;

import com.ctoutweb.lalamiam.infra.repository.entity.CaptchaImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICaptchaImageRepository extends JpaRepository<CaptchaImageEntity, Long> {
}
