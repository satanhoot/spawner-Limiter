package me.duckblade.spawnerlimiter.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MainTabcomlater implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return List.of("set", "info");
        }
        if (args.length == 2) {
            return null; // return online player names
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("set")) {
                return List.of(" <number>");
            }
        }
        return List.of(); // nothing
    }
}
