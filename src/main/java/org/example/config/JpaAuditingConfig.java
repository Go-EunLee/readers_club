package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing  // 엔티티 객체가 생성이 되거나 변경이 되었을 때 자동으로 값을 등록
public class JpaAuditingConfig {
}
