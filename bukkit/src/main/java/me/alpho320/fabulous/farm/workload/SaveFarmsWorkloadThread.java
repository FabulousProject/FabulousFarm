package me.alpho320.fabulous.farm.workload;

import com.google.common.collect.Queues;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.BukkitFarmAPI;
import me.alpho320.fabulous.farm.Callback;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;

public class SaveFarmsWorkloadThread extends BukkitRunnable {

    private final int MAX_MS_PER_TICK;
    private final ArrayDeque<Workload> deque = Queues.newArrayDeque();
    private Callback callback;
    private boolean finished = false;

    public SaveFarmsWorkloadThread(int MAX_MS_PER_TICK, Callback callback) {
        this.MAX_MS_PER_TICK = MAX_MS_PER_TICK;
        this.callback = callback;
    }

    public void addLoad(Workload workload) {
        deque.add(workload);
    }

    @Override
    public void run() {
        Debug.debug(2, "saverealmsworlkload run. " + finished);
        if (finished) return;
        try {
            long stop = System.currentTimeMillis() + MAX_MS_PER_TICK;
            while (!deque.isEmpty() && System.currentTimeMillis() <= stop) {
                deque.poll().compute();
            }
            if (deque.isEmpty() && callback != null) {
                BukkitFarmAPI.checkCallback(callback, true);
                cancel();
                finished = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (deque.isEmpty() && callback != null) {
                BukkitFarmAPI.checkCallback(callback, true);
                cancel();
                finished = true;
            }
        }
    }

}