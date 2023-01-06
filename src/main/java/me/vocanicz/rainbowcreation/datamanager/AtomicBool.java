package me.vocanicz.rainbowcreation.datamanager;

import me.vocanicz.rainbowcreation.chat.Console;

import java.util.concurrent.atomic.AtomicBoolean;

public class AtomicBool {
    private final AtomicBoolean locked = new AtomicBoolean(false);

    public void lock() {
        for (int i = 0; i < 100; i++) {
            if (!locked.getAndSet(true)) {
                // successfully acquired the lock
                return;
            }
            // wait for a short period of time before trying again
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Console.info("{ERROR} thread locked!");
    }

    public void unlock() {
        locked.set(false);
    }
}
