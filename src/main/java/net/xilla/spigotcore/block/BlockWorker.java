package net.xilla.spigotcore.block;

import lombok.Getter;
import lombok.Setter;
import net.xilla.spigotcore.SpigotAPI;
import net.xilla.spigotcore.SpigotCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class BlockWorker {

    private long lastRun = -1;

    @Getter
    private static BlockWorker instance = new BlockWorker();

    private final Vector<QueuedBlock> addQueue = new Vector<>();

    private final LinkedList<QueuedBlock> queue = new LinkedList<>();

    public void run() {
        long start = System.currentTimeMillis();

        while (!queue.isEmpty()) {
            if(System.currentTimeMillis() - start > 25) {
                break;
            }

            QueuedBlock block = queue.remove();
            if(!block.place()) {
                addQueue.add(block);
            }
        }

        queue.addAll(addQueue);
        addQueue.clear();

        lastRun = start;
    }

    public LinkedList<QueuedBlock> getQueue() {
        if(running && !started) {
            start();
        }
        return queue;
    }

    @Setter
    public boolean running = true;

    @Setter
    public static boolean started = false;

    public void start() {
        started = true;
        running = true;
        schedule(0);
    }

    public void addBlock(Location location, Material material, int data) {
        if(running && !started) {
            start();
        }
        addQueue.add(new QueuedBlock(location, material, data));
    }

    private void schedule(int delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(SpigotAPI.getInstance().getPlugin(), () -> {
            if(running) {
                run();
                if(queue.size() > 0) {
                    schedule(1);
                } else {
                    schedule(20);
                }
            }
        }, delay);
    }

}
