package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import fr.liveweeeb.Nocila;

public class smiteCommand implements CommandExecutor {

    private final Nocila plugin;

    public smiteCommand(Nocila plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            sender.sendMessage(plugin.getPrefix() + " §cUsage: /smite <player>");
            return true;
        }

        // Trouver le joueur cible
        Player target = Bukkit.getPlayer(args[0]);
        
        if (target == null) {
            sender.sendMessage(plugin.getPrefix() + " §cPlayer not found: " + args[0]);
            return true;
        }

        // Frapper le joueur avec la foudre
        target.getWorld().strikeLightning(target.getLocation());
        
        // Message de confirmation
        sender.sendMessage(plugin.getPrefix() + " §aStruck " + target.getName() + " with lightning!");
    

        return true;
    }
}