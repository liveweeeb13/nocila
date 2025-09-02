package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.liveweeeb.Nocila;
import org.bukkit.ChatColor;

public class aboutCommand implements CommandExecutor {

    private final Nocila plugin;

    public aboutCommand(Nocila plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String name = plugin.getDescription().getName();
        String version = plugin.getDescription().getVersion();
        String authors = plugin.getDescription().getAuthors().toString();
        String description = plugin.getDescription().getDescription();

        // Message pour la console
        if (!(sender instanceof Player)) {
        sender.sendMessage("§c====================================================");
        sender.sendMessage(plugin.getPrefix() + " §7Name: §a" + name);
        sender.sendMessage(plugin.getPrefix() + " §7Version: §a" + version);
        sender.sendMessage(plugin.getPrefix() + " §7Auteur: §a" + authors);
        sender.sendMessage(plugin.getPrefix() + " §7Description: §a" + description);
        sender.sendMessage("§c====================================================");
            return true;
        }

        // Message pour les joueurs 
        Player player = (Player) sender;
        player.sendMessage("§c====================================================");
        player.sendMessage(plugin.getPrefix() + " §7Name: §a" + name);
        player.sendMessage(plugin.getPrefix() + " §7Version: §a" + version);
        player.sendMessage(plugin.getPrefix() + " §7Auteur: §a" + authors);
        player.sendMessage(plugin.getPrefix() + " §7Description: §a" + description);
        player.sendMessage("§c====================================================");

        return true;
    }
}