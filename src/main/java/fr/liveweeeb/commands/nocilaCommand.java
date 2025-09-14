package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.liveweeeb.Nocila;

import java.util.ArrayList;
import java.util.List;

public class nocilaCommand implements CommandExecutor, TabCompleter {

    private final Nocila plugin;

    public nocilaCommand(Nocila plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(plugin.getPrefix() + " §cUsage: /nocila <arg>");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§eList of available Nocila commands:");
            sender.sendMessage("§6/nocila help §7- View help");
            sender.sendMessage("§6/about §7- About Nocila");
            sender.sendMessage("§6/broadcast §7- Send a message to all players");
            sender.sendMessage("§6/craft §7- Open a Workbench");
            sender.sendMessage("§6/rename <name> §7- Rename the item in your hand");
            sender.sendMessage("§6/hat §7- Wear the item in your hand as a hat");
            sender.sendMessage("§6/day §7- Set the day");
            sender.sendMessage("§6/night §7- Set the night");
            sender.sendMessage("§6/lore <text> §7- Add lore to the item in your hand");
            sender.sendMessage("§6/repair §7- Repair the item in your hand");
            sender.sendMessage("§6/delfire §7- Remove fire around you");
            sender.sendMessage("§6/smite <player> §7- Strike a player with lightning");
            sender.sendMessage("§6§o/masssummon <entity> <amount> §7- Spawn the number of entities requested");
            return true;

        } else if (args[0].equalsIgnoreCase("debug")) {
            sender.sendMessage("§eList of available Nocila debug commands:");
            sender.sendMessage("§6/nocila debug-update §7- See if update checking is activated");
            sender.sendMessage("§6/nocila debug-maxsummon §7- See the maximum amount for the /masssummon command");
            return true;

        } else if (args[0].equalsIgnoreCase("debug-update")) {
            boolean updateEnabled = plugin.isUpdateEnabled();
            sender.sendMessage(plugin.getPrefix() + " §c§lDEBUG: §7" + updateEnabled);
            return true;

        } else if (args[0].equalsIgnoreCase("debug-maxsummon")) {
            int maxAmount = plugin.getConfig().getInt("masssummon.max-amount", 50);
            sender.sendMessage(plugin.getPrefix() + " §c§lDEBUG: §7" + maxAmount);
            return true;

        } else if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("nocila.reload")) {
                sender.sendMessage(plugin.getPrefix() + " §cYou don't have permission to reload the config!");
                return true;
            }

            plugin.reloadPluginConfig();
            sender.sendMessage(plugin.getPrefix() + " §aConfiguration reloaded successfully!");
            return true;

        } else {
            sender.sendMessage(plugin.getPrefix() + " §cUnknown subcommand: " + args[0]);
            return true;
        }
    }

    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("help");
            completions.add("debug");
            completions.add("reload");
        }

        return completions;
    }
}
