package org.albaross.agents4j.core;

@FunctionalInterface
public interface Agent<S, A> {

    A next(S state);

    default boolean isActive() {
        return true;
    }

}
