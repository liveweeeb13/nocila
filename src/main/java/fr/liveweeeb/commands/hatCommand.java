package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import fr.liveweeeb.Nocila;

public class hatCommand implements CommandExecutor {

    private final Nocila plugin;

    public hatCommand(Nocila plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by a player");
            return true;
        }

        Player player = (Player) sender;
        PlayerInventory inventory = player.getInventory();
        ItemStack handItem = inventory.getItemInMainHand();

        // Vérifier si le joueur tient un item
        if (handItem == null || handItem.getType().isAir()) {
            player.sendMessage(plugin.getPrefix() + " §cYou must hold an item to wear as a hat!");
            return true;
        }

        // Récupérer l'item actuellement sur la tête
        ItemStack currentHelmet = inventory.getHelmet();
        
        // Échanger l'item en main avec le casque
        inventory.setHelmet(handItem);
        inventory.setItemInMainHand(currentHelmet);

        player.sendMessage(plugin.getPrefix() + " §aItem placed on your head!");

        return true;
    }
}