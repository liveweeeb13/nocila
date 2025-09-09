package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import fr.liveweeeb.Nocila;

public class craftCommand implements CommandExecutor {

    private final Nocila plugin;

    public craftCommand(Nocila plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by a player");
            return true;
        }

        if (sender instanceof Player && !sender.hasPermission("nocila.craft")) {
            sender.sendMessage(plugin.getPrefix() + " §cYou don't have permission to use this command !");
            return true;
        }

        Player player = (Player) sender;
        
        player.openWorkbench(null, true);
        
        player.sendMessage(plugin.getPrefix() + " §aCrafting table opened!");

        return true;
    }
}