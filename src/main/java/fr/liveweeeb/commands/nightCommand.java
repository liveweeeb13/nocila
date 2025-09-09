package fr.liveweeeb.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.liveweeeb.Nocila;

public class nightCommand implements CommandExecutor {
      private final Nocila plugin;

    public nightCommand(Nocila plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by one player");
            return true;
        }
        
        Player player = (Player) sender;

    // Vérifier la permission (optionnel)
        if (sender instanceof Player && !sender.hasPermission("nocila.night")) {
            sender.sendMessage(plugin.getPrefix() + " §cYou don't have permission to use this command !");
            return true;
        }

        World world = player.getWorld();

        // Défini a 13000 ticks 
        world.setTime(13000);
        
        // Désactiver la pluie/orage
        world.setStorm(false);
        world.setThundering(false);
        
         player.sendMessage(plugin.getPrefix() + " §aNight has been set");
       //player.sendMessage(plugin.getPrefix() + " §aLa nuit a été défini dans le monde §e" + world.getName());

        return true;
    }
}