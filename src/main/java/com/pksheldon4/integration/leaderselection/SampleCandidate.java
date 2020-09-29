package com.pksheldon4.integration.leaderselection;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.leader.AbstractCandidate;
import org.springframework.integration.leader.Context;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SampleCandidate extends AbstractCandidate {

    private boolean shouldProcess;

    public SampleCandidate() {
        log.info("################ SampleCandidate: {} - {}", getRole(), getId());
    }

    public SampleCandidate(String id, String role) {
        super(id, role);
    }


    @Scheduled(initialDelay = 1000, fixedDelay = 2000)
    public void doSomething() {
        if (shouldProcess) {
            log.info("****** Some other Process - is being processed by Application {}-{}", getRole(), getId());
        }
    }


    @Override
    public void onGranted(Context ctx) {
        this.shouldProcess = true;
        if (log.isInfoEnabled()) {
            log.info(this + " has been granted leadership; context: " + ctx);
        }
    }

    @Override
    public void onRevoked(Context ctx) {
        this.shouldProcess = false;
        if (log.isInfoEnabled()) {
            log.info(this + " leadership has been revoked: " + ctx);
        }
    }

    @Override
    public String toString() {
        return String.format("Candidate{role=%s, id=%s}", getRole(), getId());
    }
}
