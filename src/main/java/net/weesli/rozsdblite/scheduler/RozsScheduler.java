package net.weesli.rozsdblite.scheduler;

import net.weesli.rozsdblite.model.DatabaseImpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RozsScheduler {

    private static ScheduledExecutorService scheduler;

    public static void open(DatabaseImpl database) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        int autoSaveInterval = database.getDatabaseSettings().getAutoSaveInterval();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                database.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, autoSaveInterval, autoSaveInterval, TimeUnit.SECONDS);
    }

    public static void schedule(Runnable runnable) {
        scheduler.submit(runnable);
    }

    public static void close() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }
}
