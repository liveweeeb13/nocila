package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import fr.liveweeeb.Nocila;

public class repairCommand implements CommandExecutor {
    
    private final Nocila plugin;

    public repairCommand(Nocila plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by a player");
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        // Vérifier si le joueur tient un item
        if (item == null || item.getType().isAir()) {
            player.sendMessage(plugin.getPrefix() + " §cYou must hold an item to repair");
            return true;
        }

        // Vérifier si l'item peut être endommagé
        if (!(item.getItemMeta() instanceof Damageable)) {
            player.sendMessage(plugin.getPrefix() + " §cThis item cannot be repaired");
            return true;
        }

        Damageable damageable = (Damageable) item.getItemMeta();
        
        // Vérifier si l'item est déjà en parfait état
        if (damageable.getDamage() == 0) {
            player.sendMessage(plugin.getPrefix() + " §aThis item is already in perfect condition");
            return true;
        }

        // Réparer l'item
        damageable.setDamage(0);
        item.setItemMeta(damageable);

        player.sendMessage(plugin.getPrefix() + " §aItem successfully repaired!");

        return true;


    }
}