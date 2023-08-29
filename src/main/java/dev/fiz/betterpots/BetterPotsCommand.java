package dev.fiz.betterpots;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BetterPotsCommand implements CommandExecutor, TabCompleter {

    private BetterPots plugin;

    public BetterPotsCommand(BetterPots plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("betterpots.admin")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("PermissionMSG")));
                return true;
            }
            if (args.length == 0) {
                String msg = "&8[&5BetterPots&8] &fcustomizable potions plugin" +
                        "\n&f/betterpots reload &8- &7Reloads the config" +
                        "\n&f/betterpots set <number> &8- &7Sets the PotSpeed value";

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                return true;
            } else if (args.length == 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl"))) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5BetterPots&8] &fReloading config..."));
                plugin.reloadConfig();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5BetterPots&8] &fConfig reloaded!"));
                return true;
            } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
                double potSpeed;
                try {
                    potSpeed = Double.parseDouble(args[1]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid number!");
                    return true;
                }

                FileConfiguration config = plugin.getConfig();
                config.set("PotSpeed", potSpeed);
                plugin.saveConfig();

                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5BetterPots&8] &fPotSpeed set to " + potSpeed));
                plugin.reloadConfig();
                return true;
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&5BetterPots&8] &fInvalid arguments!"));
                return false;
            }
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> completion = new ArrayList<>();
            completion.add("reload");
            completion.add("set");
            return completion;
        }

        return null;
    }
}