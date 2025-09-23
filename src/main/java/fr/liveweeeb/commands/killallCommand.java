package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.World;
import fr.liveweeeb.Nocila;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class killallCommand implements CommandExecutor {

    private final Nocila plugin;
    private final List<String> noKillEntities;

    public killallCommand(Nocila plugin) {
        this.plugin = plugin;
        this.noKillEntities = new ArrayList<>();
        loadNoKillEntities();
    }

    private void loadNoKillEntities() {
        // Charger la liste depuis la config
        noKillEntities.clear();
        List<String> configList = plugin.getConfig().getStringList("killall.nokill");
        
        for (String entityName : configList) {
            noKillEntities.add(entityName.toUpperCase());
        }
        
        // Liste par défaut si la config est vide
        if (noKillEntities.isEmpty()) {
            noKillEntities.add("ZOMBIE");
            noKillEntities.add("VILLAGER");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by a player");
            return true;
        }

        loadNoKillEntities();
        
        if (!sender.hasPermission("minecraft.kill") && !sender.hasPermission("nocila.killall")) {
            sender.sendMessage(plugin.getPrefix() + " §cYou don't have permission to use this command!");
            return true;
        }

        World world;
        int radius = -1;

        if (sender instanceof Player) {
            Player player = (Player) sender;
            world = player.getWorld();
            
            if (args.length > 0) {
                try {
                    radius = Integer.parseInt(args[0]);
                    if (radius < 1) {
                        sender.sendMessage(plugin.getPrefix() + " §cRadius must be at least 1!");
                        return true;
                    }
                    if (radius > 500) {
                        sender.sendMessage(plugin.getPrefix() + " §cRadius cannot exceed 500 blocks!");
                        radius = 500;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(plugin.getPrefix() + " §cInvalid radius!.");
                }
            }
        } else {
            world = plugin.getServer().getWorlds().get(0);
        }

        int killedEntities = 0;
        int skippedEntities = 0;
        List<Entity> entities;

        if (radius > 0 && sender instanceof Player) {
            Player player = (Player) sender;
            entities = player.getNearbyEntities(radius, radius, radius);
        } else {
            entities = world.getEntities();
        }

        // Tuer les entités sauf celles dans la liste noKill
        for (Entity entity : entities) {
            if (entity.getType() != EntityType.PLAYER) {
                String entityName = entity.getType().name();
                
                // Vérifier si l'entité est dans la liste no-kill
                if (noKillEntities.contains(entityName)) {
                    skippedEntities++;
                    continue;
                }
                
                entity.remove();
                killedEntities++;
            }
        }

        String message = plugin.getPrefix() + " §aKilled §e" + killedEntities + " §aentities";
       // if (skippedEntities > 0) message += " (§7skipped: " + skippedEntities + "§a)";
        if (radius > 0) {
            message += " within §6" + radius + " §ablocks!";
        } else {
            message += " in the entire world!";
        }
        
        sender.sendMessage(message);
        
        // Afficher la liste des entités protégées si demandé
        if (args.length > 1 && args[1].equalsIgnoreCase("info")) {
            sender.sendMessage("§7Protected entities: §e" + String.join(", ", noKillEntities));
        }

        return true;
    }
}