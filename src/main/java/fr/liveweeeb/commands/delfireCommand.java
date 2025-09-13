package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.Location;
import org.bukkit.World;
import fr.liveweeeb.Nocila;

public class delfireCommand implements CommandExecutor {
    
    private final Nocila plugin;

    public delfireCommand(Nocila plugin) {
        this.plugin = plugin;
    }
   
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players");
            return true;
        }

        Player player = (Player) sender;
        
        // Récupérer les valeurs de la config
        int defaultRadius = plugin.getConfig().getInt("fire.default-radius", 10);
        int maxRadius = plugin.getConfig().getInt("fire.max-radius", 50);
        
        int radius = defaultRadius; // Rayon par défaut depuis la config

        // Si radios custom
        if (args.length > 0) {
            try {
                radius = Integer.parseInt(args[0]);
                if (radius < 1) radius = 1;
                if (radius > maxRadius) {
                    player.sendMessage(plugin.getPrefix() + " §cRadius cannot exceed " + maxRadius + " blocks!");
                    radius = maxRadius;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(plugin.getPrefix() + " §cInvalid radius! Using default " + defaultRadius + " blocks.");
                radius = defaultRadius;
            }
        }

        Location playerLoc = player.getLocation();
        World world = player.getWorld();
        int fireRemoved = 0;

        // Parcourir tous les blocs dans le rayon
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLoc = playerLoc.clone().add(x, y, z);
                    
                    if (world.getBlockAt(blockLoc).getType() == Material.FIRE) {
                        world.getBlockAt(blockLoc).setType(Material.AIR);
                        fireRemoved++;
                    }
                }
            }
        }

        if (fireRemoved > 0) {
            player.sendMessage(plugin.getPrefix() + " §aRemoved §e" + fireRemoved + " §afire blocks within §6" + radius + " §ablocks!");
        } else {
            player.sendMessage(plugin.getPrefix() + " §7No fire found within §6" + radius + " §7blocks.");
        }

        return true;
    }
}