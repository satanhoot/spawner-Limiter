package me.duckblade.spawnerlimiter.commands;

import me.duckblade.spawnerlimiter.SpawnerLimiter;
import me.duckblade.spawnerlimiter.manager.ConfigManager;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;

        if (args[0].equalsIgnoreCase("Info")) {
            if (!(sender instanceof Player) && args.length < 2) {
                sender.sendMessage(mini("<red>/spawnerlimiter info <player>"));
                return true;
            }

            if (sender instanceof Player && args.length < 2) {
                info(sender, (Player) sender);
                return true;
            } else {
                Player target = SpawnerLimiter.getPlugin().getServer().getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(mini("<red>Player not found.</red>"));
                    return true;
                }
                info(sender, target);
            }

        } else if (args[0].equalsIgnoreCase("set")) {
            if (args.length < 3) {
                sender.sendMessage(mini("<red>/spawnerlimiter set <player> <max-spawner-count>"));
                return true;
            }

            Player target = SpawnerLimiter.getPlugin().getServer().getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(mini("<red>Player not found.</red>"));
                return true;
            }
            try {
                int maxSpawnerCount = Integer.parseInt(args[2]);

                if (maxSpawnerCount == 0) {
                    ConfigManager.getConfig().set("players." + target.getUniqueId(), null);
                    ConfigManager.saveConfig();
                    sender.sendMessage(mini("<green>Set " + target.getName() + "'s max spawner count to " + maxSpawnerCount + "</green>"));
                    return true;
                }
                if (PlayerSpawnerManager.getSpawnerCount(target.getUniqueId()) == 0) {
                    ConfigManager.getConfig().set("players." + target.getUniqueId() + ".name", target.getName());
                }
                ConfigManager.getConfig().set("players." + target.getUniqueId() + ".used-spawner", maxSpawnerCount);
                ConfigManager.saveConfig();
                sender.sendMessage(mini("<green>Set " + target.getName() + "'s max spawner count to " + maxSpawnerCount + "</green>"));
            } catch (NumberFormatException e) {
                sender.sendMessage("Invalid number format for max spawner count.");
            }
            return true;
        }

        return false;
    }


    private void info(CommandSender sender, Player target) {
        String message = "--------------------\n" +
                "Spawner Limiter\n" +
                "name: " + target.getName() + " \n" +
                "used spawner: " + PlayerSpawnerManager.getSpawnerCount(target.getUniqueId()) + "\n" +
                "max spawner : " + PlayerSpawnerManager.getMaxSpawner(target.getUniqueId()) + "\n" +
                "--------------------";
        sender.sendMessage(mini(message));

    }

    private Component mini(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }
}
