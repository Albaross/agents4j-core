package org.albaross.agents4j.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

public class SimpleEngine<S, A> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(SimpleEngine.class);

    private final Environment<S, A> environment;
    private final List<Agent<S, A>> agents;

    public SimpleEngine(Environment<S, A> environment, List<Agent<S, A>> agents) {
        this.environment = Objects.requireNonNull(environment, "environment must not be null");
        this.agents = Objects.requireNonNull(agents, "agents must not be null");
    }

    @Override
    public void run() {
        long clock = 0;

        do {
            long id = 0;
            for (Agent<S, A> agent : agents) {
                if (agent.isActive()) {
                    try {
                        S state = environment.getState(id);
                        log.debug("<{}> agent {} perceives {}", clock, id, state);
                        A action = agent.next(state);
                        log.debug("<{}> agent {} executes {}", clock, id, action);
                        environment.execute(id, action);
                    } catch (Exception e) {
                        log.warn("<{}> agent {} has encountered an error", clock, id, e);
                    }
                }
            }
            id++;

            clock++;
        } while (environment.hasChanged());

        log.debug("<{}> simulation has terminated", clock);
    }
}