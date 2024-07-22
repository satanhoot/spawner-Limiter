package me.duckblade.spawnerlimiter.utils;

import me.duckblade.spawnerlimiter.SpawnerLimiter;

import java.util.logging.Level;

public class Logger {
    private static final String prefix = "[SpawnerLimiter] ";

    public static boolean enebledDebug;

    public static void info(String message) {
        info(message, false);
    }

    public static void info(String message, boolean debug) {
        if (debug) {
            if (enebledDebug) {
                SpawnerLimiter.getPlugin().getLogger().log(Level.INFO, prefix + message);
            }
        } else {
            SpawnerLimiter.getPlugin().getLogger().log(Level.INFO, prefix + message);
        }
    }

    public static void warning(String message) {
        warning(message, false);
    }

    public static void warning(String message, boolean debug) {
        if (debug) {
            if (enebledDebug) {
                SpawnerLimiter.getPlugin().getLogger().log(Level.WARNING, prefix + message);
            }
        } else {
            SpawnerLimiter.getPlugin().getLogger().log(Level.WARNING, prefix + message);
        }
    }

}
