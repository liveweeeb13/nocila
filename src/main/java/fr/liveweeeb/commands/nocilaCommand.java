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
            return true;
        } else if (args[0].equalsIgnoreCase("debug-update")) {
             boolean updateEnabled = plugin.isUpdateEnabled();
                sender.sendMessage(plugin.getPrefix() + " §c§lDEBUG: §7" + updateEnabled);
        }

        return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("help");
        }

        return completions;
    }
}
