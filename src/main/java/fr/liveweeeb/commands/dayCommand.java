package fr.liveweeeb.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.liveweeeb.Nocila;

public class dayCommand implements CommandExecutor {
      private final Nocila plugin;

    public dayCommand(Nocila plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players");
            return true;
        }
        
        Player player = (Player) sender;

        World world = player.getWorld();

        // Mettre l'heure à 1000 ticks (6h du matin - début du jour)
        world.setTime(1000);
        
        // Désactiver la pluie/orage
        world.setStorm(false);
        world.setThundering(false);
        
         player.sendMessage(plugin.getPrefix() + " §aDay has been set");
         //  player.sendMessage(plugin.getPrefix() + " §aLe jour a été défini dans le monde §e" + world.getName());

        return true;
    }
}