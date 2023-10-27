package me.alpho320.fabulous.farm.workload;

import com.google.common.collect.Queues;
import me.alpho320.fabulous.core.bukkit.util.debugger.Debug;
import me.alpho320.fabulous.farm.provider.Provider;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;

public class LoadFarmsWorkloadThread extends BukkitRunnable {

    private final int MAX_MS_PER_TICK;
    private final ArrayDeque<Workload> deque = Queues.newArrayDeque();
    private Provider.Callback callback;
    private boolean cancelled = false;

    public LoadFarmsWorkloadThread(int MAX_MS_PER_TICK, Provider.Callback callback) {
        this.MAX_MS_PER_TICK = MAX_MS_PER_TICK;
        this.callback = callback;
    }

    public void addLoad(Workload workload) {
        deque.add(workload);
    }

    @Override
    public void run() {
        if (cancelled) return;
        try {
            if (deque.isEmpty()) {
                Debug.debug(2, "Deque isempty!");
                FarmAPI.checkCallback(callback, true);
                cancel();
                cancelled = true;
            } else {
                long stop = System.currentTimeMillis() + MAX_MS_PER_TICK;
                Debug.debug(2," | LoadRealmWThread: " + stop + " (" + deque.size() + ")");
                while (!deque.isEmpty() && System.currentTimeMillis() <= stop) {
                    deque.poll().compute();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (deque.isEmpty()) {
                FarmAPI.checkCallback(callback, true);
                cancel();
                cancelled = true;
            }
        }
    }

}