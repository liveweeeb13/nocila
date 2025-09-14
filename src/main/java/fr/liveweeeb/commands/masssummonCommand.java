package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.entity.EntityType;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.World;
import fr.liveweeeb.Nocila;
import java.util.ArrayList;
import java.util.List;

public class masssummonCommand implements CommandExecutor, TabCompleter {
    
    private final Nocila plugin;
    private final List<String> vanillaEntities;

    public masssummonCommand(Nocila plugin) {
        this.plugin = plugin;
        
        vanillaEntities = new ArrayList<>();
        for (EntityType type : EntityType.values()) {
            if (type.isSpawnable() && type.isAlive()) {
                vanillaEntities.add(type.name().toLowerCase());
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by players");
            return true;
        }

        Player player = (Player) sender;

        int maxAmount = plugin.getConfig().getInt("masssummon.max-amount", 50);



        if (args.length < 2) {
            player.sendMessage(plugin.getPrefix() + " §cUsage: /masssummon <entity> <amount>");
            // player.sendMessage("§7For modded entities, use exact entity ID");
            return true;
        }

        String entityName = args[0];
        int amount;

        try {
            amount = Integer.parseInt(args[1]);
            if (amount < 1) amount = 1;
            if (amount > maxAmount) {
                player.sendMessage(plugin.getPrefix() + " §cMaximum amount " + maxAmount);
                amount = maxAmount;
            }
        } catch (NumberFormatException e) {
            player.sendMessage(plugin.getPrefix() + " §cInvalid amount! Use a number between 1-" + maxAmount);
            return true;
        }

        Location spawnLocation = player.getLocation();
        World world = player.getWorld();
        int spawnedCount = 0;

        try {
            EntityType vanillaType = EntityType.valueOf(entityName.toUpperCase());
            if (vanillaType.isSpawnable() && vanillaType.isAlive()) {
                for (int i = 0; i < amount; i++) {
                    world.spawnEntity(spawnLocation, vanillaType);
                    spawnedCount++;
                }
                player.sendMessage(plugin.getPrefix() + " §aSummoned §e" + spawnedCount + " §a" + entityName + "(s)!");
                return true;
            }
        } catch (IllegalArgumentException e) {
            // Entity non vanilla -> pass
        }

        try {
            for (int i = 0; i < amount; i++) {
                String summonCommand = String.format(
                    "execute as %s at @s run summon %s ~ ~ ~",
                    player.getName(),
                    entityName
                );
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), summonCommand);
                spawnedCount++;
            }
            player.sendMessage(plugin.getPrefix() + " §aSummoned §e" + spawnedCount + " §a" + entityName + "(s)!");
        } catch (Exception ex) {
            player.sendMessage(plugin.getPrefix() + " §cFailed to summon entity: " + entityName);
            player.sendMessage("§7Make sure the entity ID is correct and supported");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        int maxAmountF = plugin.getConfig().getInt("masssummon.max-amount", 50);


        if (args.length == 1) {
            String input = args[0].toLowerCase();
            
            for (String entity : vanillaEntities) {
                if (entity.startsWith(input)) {
                    completions.add(entity);
                }
            }

            if (completions.isEmpty()) {
                String[] commonModdedEntities = {
                    "minecraft:", "modid:entity_", "twilightforest:", "iceandfire:",
                    "lycanitesmobs:", "mowziesmobs:", "alexsmobs:", "create:"
                };
                for (String modEntity : commonModdedEntities) {
                    if (modEntity.startsWith(input)) {
                        completions.add(modEntity);
                    }
                }
            }
        } else if (args.length == 2) {
            completions.add("1");
            completions.add("5");
            completions.add("10");
            completions.add("20");
            completions.add("50");
            if (maxAmountF > 50) {
                completions.add(String.valueOf(maxAmountF));
            }       
        }

        return completions;
    }
}