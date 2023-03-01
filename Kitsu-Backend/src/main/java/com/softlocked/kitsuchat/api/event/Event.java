package com.softlocked.kitsuchat.api.event;

public interface Event {
    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
