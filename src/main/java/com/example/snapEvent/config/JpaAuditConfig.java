package com.example.snapEvent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaAuditConfig {
    // 이후에 Mock 테스트를 할 때 오류 방지위해 따로 Config를 만듦
}
