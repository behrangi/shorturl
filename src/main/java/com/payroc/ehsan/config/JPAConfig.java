package com.payroc.ehsan.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.payroc.ehsan.data.repository")
@EnableTransactionManagement
@EntityScan("com.payroc.ehsan.data.entity")
public class JPAConfig {
}
