package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.liveweeeb.Nocila;
import java.util.ArrayList;
import java.util.List;

public class loreCommand implements CommandExecutor {

    private final Nocila plugin;

    public loreCommand(Nocila plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cThis command can only be used by a player");
            return true;
        }

        Player player = (Player) sender;


    // Vérifier la permission (optionnel)
        if (sender instanceof Player && !sender.hasPermission("nocila.lore")) {
            sender.sendMessage(plugin.getPrefix() + " §cYou don't have permission to use this command !");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        // Vérifier si le joueur tient un item
        if (item == null || item.getType().isAir()) {
            player.sendMessage(plugin.getPrefix() + " §cYou must hold an item to add lore");
            return true;
        }

        // Vérifier s'il y a une lore
        if (args.length == 0) {
            player.sendMessage(plugin.getPrefix() + " §cUsage: /lore <text>");
            player.sendMessage("§7§oUse & for colors: &aGreen &bBlue &cRed");
            player.sendMessage("§7§oUse \\n for new line");
            player.sendMessage("§7§oFormats: &lBold &oItalic &nUnderline");
            return true;
        }

        // Construire le texte de la lore
        StringBuilder loreBuilder = new StringBuilder();
        for (String arg : args) {
            loreBuilder.append(arg).append(" ");
        }
        String rawLore = loreBuilder.toString().trim();

        // Formater les couleurs et gérer les sauts de ligne
        List<String> formattedLore = formatLore(rawLore);

        // Appliquer la lore à l'item
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setLore(formattedLore);
            item.setItemMeta(meta);
            
            player.sendMessage(plugin.getPrefix() + " §aLore added successfully!");
            player.sendMessage("§7Preview:");
            for (String line : formattedLore) {
                player.sendMessage("§8- §r" + line);
            }
        } else {
            player.sendMessage(plugin.getPrefix() + " §cError: Could not add lore to item");
        }

        return true;
    }

    /**
     * Convertit le texte en lore formatée avec couleurs et sauts de ligne
     */
    private List<String> formatLore(String text) {
        List<String> lore = new ArrayList<>();
        
        // Remplacer les & par § et gérer les sauts de ligne
        String formattedText = text.replace('&', '§');
        
        // Séparer par les sauts de ligne
        String[] lines = formattedText.split("\\\\n");
        
        for (String line : lines) {
            lore.add(line.trim());
        }
        
        return lore;
    }
}