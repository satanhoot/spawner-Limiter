package me.duckblade.spawnerlimiter.commands;

import me.duckblade.spawnerlimiter.manager.ConfigManager;
import me.duckblade.spawnerlimiter.manager.PlayerSpawnerManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1 && args[0].equalsIgnoreCase("info")) {
            if (sender instanceof Player player) {
                return info(sender, player);
            }
            return false;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(mini("<red>Player not found.</red>"));
                return true;
            }
            return info(sender, target);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null){
                sender.sendMessage(mini("<red>Player not found.</red>"));
                return true;
            }
            Integer maxSpawnerCount = parseInteger(args[2]);
            if (maxSpawnerCount == null) {
                sender.sendMessage(mini("<red>Invalid max spawner count.</red>"));
                return true;
            }

            setSpawner(maxSpawnerCount, sender, target);
            return true;
        }
        return false;
    }

    private void setSpawner(Integer maxSpawnerCount, CommandSender sender, Player target) {

        if (maxSpawnerCount == 0) { // if set is 0 then delete the player from the config
            ConfigManager.getConfig().set("players." + target.getUniqueId(), null);
            ConfigManager.saveConfig();
            sender.sendMessage(mini("<green>Set " + target.getName() + "'s max spawner count to " + maxSpawnerCount + "</green>"));
        }

        if (PlayerSpawnerManager.getSpawnerCount(target.getUniqueId()) == 0) { // if player no in config than add them name
            ConfigManager.getConfig().set("players." + target.getUniqueId() + ".name", target.getName());
        }

        ConfigManager.getConfig().set("players." + target.getUniqueId() + ".used-spawner", maxSpawnerCount); // finally set the max spawner count
        ConfigManager.saveConfig();
        sender.sendMessage(mini("<green>Set " + target.getName() + "'s max spawner count to " + maxSpawnerCount + "</green>"));
    }



    private boolean info(CommandSender sender, Player target) {
            String message = "--------------------\n" +
                    "Spawner Limiter\n" +
                    "name: " + target.getName() + " \n" +
                    "used spawner: " + PlayerSpawnerManager.getSpawnerCount(target.getUniqueId()) + "\n" +
                    "max spawner : " + PlayerSpawnerManager.getMaxSpawner(target) + "\n" +
                    "--------------------";
            sender.sendMessage(mini(message));
            return true;
    }

    private Component mini(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static Integer parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
