package net.xilla.spigotcore;

import net.xilla.core.library.worker.Worker;

public abstract class SpigotWorker extends Worker {

    public SpigotWorker(String name, long timer) {
        super(name, timer);
    }

}
