package com.pksheldon4.integration.leaderselection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jdbc.lock.DefaultLockRepository;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.integration.jdbc.lock.LockRepository;
import org.springframework.integration.support.leader.LockRegistryLeaderInitiator;
import org.springframework.integration.support.locks.LockRegistry;

import javax.sql.DataSource;

@Configuration
public class LeaderSelectionConfig {

    @Bean
    public DefaultLockRepository lockRepository(DataSource dataSource) {
        return new DefaultLockRepository(dataSource);
    }

    @Bean
    public JdbcLockRegistry lockRegistry(LockRepository lockRepository) {
        JdbcLockRegistry registry = new JdbcLockRegistry(lockRepository);
//        registry.setIdleBetweenTries(Duration.ofMillis(500));
        return registry;
    }

    @Bean
    public LockRegistryLeaderInitiator leaderInitiator(LockRegistry lockRegistry, SampleCandidate sampleCandidate) {
        LockRegistryLeaderInitiator leaderInitiator = new LockRegistryLeaderInitiator(lockRegistry, sampleCandidate);
//        leaderInitiator.setHeartBeatMillis(500);
//        leaderInitiator.setBusyWaitMillis(50);
        return leaderInitiator;
    }
}
