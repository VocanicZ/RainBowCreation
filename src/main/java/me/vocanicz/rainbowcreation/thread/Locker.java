package me.vocanicz.rainbowcreation.thread;

import java.util.concurrent.atomic.AtomicBoolean;

public class Locker {
    public AtomicBoolean locked = new AtomicBoolean(false);

    public void lock() {
        while (locked.compareAndSet(false, true)) {}
    }

    public void unlock() {
        locked.set(false);
    }
}
