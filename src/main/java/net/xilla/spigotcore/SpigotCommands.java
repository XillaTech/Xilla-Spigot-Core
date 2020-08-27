package net.xilla.spigotcore;

import com.tobiassteely.tobiasapi.api.manager.CoreManager;
import com.tobiassteely.tobiasapi.api.manager.ManagerParent;
import com.tobiassteely.tobiasapi.api.worker.Worker;
import net.xilla.spigotcore.command.SpigotCommand;

import java.util.ArrayList;
import java.util.Arrays;

public class SpigotCommands extends SpigotObject {

    public SpigotCommands() {
        coreCommand();
    }

    public void coreCommand() {
        new SpigotCommand(SpigotCore.getInstance(), "spigotcore", new ArrayList<>(), (data -> {
            StringBuilder stringBuilder = new StringBuilder();
            if(data.getArgs().length >= 1 && data.getArgs()[0].equalsIgnoreCase("status")) {
                stringBuilder.append("&8&m                        &f");

                StringBuilder mtb = new StringBuilder();
                for (ManagerParent manager : CoreManager.getInstance().getManagers()) {
                    mtb.append(" &8- &f").append(manager.getIdentifier()).append(" (" ).append(manager.getList().size()).append(")\n");
                }

                stringBuilder.append(" &cManagers: \n").append(mtb.toString()).append("\n");

                StringBuilder wtb = new StringBuilder();
                for (Worker worker : getAPI().getWorkerManager().getList()) {
                    wtb.append(" &8- &f").append(worker.getKey()).append(" (" ).append(worker.getStatus()).append(")\n");
                }

                stringBuilder.append(" &cWorkers: \n").append(wtb.toString()).append("\n");

                stringBuilder.append("&8&m                        &f");
            } else if(data.getArgs().length >= 1 && data.getArgs()[0].equalsIgnoreCase("tps")) {
                stringBuilder.append("&8&m                        &f");
                stringBuilder.append(" &cTPS: ").append(getTPS()).append("/20.0").append("\n");

                Runtime r = Runtime.getRuntime();
                stringBuilder.append(" &cMax Ram: &f").append(r.maxMemory() / 1048576).append("MB").append("\n");
                stringBuilder.append(" &cAllocated Ram: &f").append(r.totalMemory() / 1048576).append("MB").append("\n");
                stringBuilder.append(" &cUsed Ram: &f").append((r.totalMemory() - r.freeMemory()) / 1048576).append("MB").append("\n");
                stringBuilder.append(" &cOS Name: &f").append(System.getProperty("os.name")).append("\n");
                stringBuilder.append(" &cCPU Identifier: &f").append(System.getenv("PROCESSOR_IDENTIFIER")).append("\n");
                stringBuilder.append(" &cAvailable CPU Core(s): &f").append(r.availableProcessors()).append("\n");
                stringBuilder.append("&8&m                        &f");
            } else {
                stringBuilder.append("&8&m                        &f\n");
                stringBuilder.append(" &c/spigotcore status - View the core status\n");
                stringBuilder.append(" &c/spigotcore tps - View the server status\n");
                stringBuilder.append("&8&m                        &f");
            }

            data.getSender().sendMessage(colorize(stringBuilder.toString()));
            return true;
        }));
    }

    public double getTPS() {
        double total = 0;
        for(double x : getServer().getTPS()) {
            total += x;
        }
        return (int)((total / getServer().getTPS().length) * 100) / 100.0;
    }

}
