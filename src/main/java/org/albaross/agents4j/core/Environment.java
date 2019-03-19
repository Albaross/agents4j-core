package org.albaross.agents4j.core;

public interface Environment<S, A> {

    S getState(long agentId);

    void execute(long agentId, A action);

    boolean hasChanged();

}
