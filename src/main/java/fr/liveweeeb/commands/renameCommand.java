package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.liveweeeb.Nocila;

public class renameCommand implements CommandExecutor {

    private final Nocila plugin;

    public renameCommand(Nocila plugin) {
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
        if (sender instanceof Player && !sender.hasPermission("nocila.rename")) {
            sender.sendMessage(plugin.getPrefix() + " §cYou don't have permission to use this command !");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        // Vérifier si le joueur tient un item
        if (item == null || item.getType().isAir()) {
            player.sendMessage(plugin.getPrefix() + " §cYou must hold an item to rename");
            return true;
        }

        // Vérifier s'il y a un nom
        if (args.length == 0) {
            player.sendMessage(plugin.getPrefix() + " §cUsage: /rename <name>");
            player.sendMessage("§7§oColors: &1-&9 &a-&f &lBold &oItalic &nUnderline");
            return true;
        }

        // Construire le nom avec les couleurs
        StringBuilder nameBuilder = new StringBuilder();
        for (String arg : args) {
            nameBuilder.append(arg).append(" ");
        }
        String rawName = nameBuilder.toString().trim();

        // Formater les couleurs et styles
        String formattedName = formatColors(rawName);

        // Appliquer le nom à l'item
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(formattedName);
            item.setItemMeta(meta);
            
            player.sendMessage(plugin.getPrefix() + " §aItem renamed to: " + formattedName);
        } else {
            player.sendMessage(plugin.getPrefix() + " §cError: Could not rename item");
        }

        return true;
    }

    /**
     * Convertit les codes de couleur (&) en codes formatés
     * Supporte les couleurs, gras, italique, etc.
     */
    private String formatColors(String message) {
        // Remplacer les & par § pour la compatibilité Bukkit
        return message.replace('&', '§');
    }
}