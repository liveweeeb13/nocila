package fr.liveweeeb.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import fr.liveweeeb.Nocila;
import org.bukkit.ChatColor;

public class broadcastCommand implements CommandExecutor {

    private final Nocila plugin;

    public broadcastCommand(Nocila plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        // Vérifier la permission
        if (!sender.hasPermission("nocila.broadcast")) {
            sendMessage(sender, plugin.getPrefix() + " §cVous n'avez pas la permission d'utiliser cette commande!");
            return true;
        }
        

        if (args.length == 0) {
            sendMessage(sender, plugin.getPrefix() + " §cUtilisation: /broadcast <message>");
            sendMessage(sender, "§7§oLes couleurs sont supportées avec le symbole &\nhttps://www.reddit.com/r/Minecraft/comments/c0z2jn/color_guide_youre_welcome");
         //   sendMessage(sender, "§7§oLes couleurs sont supportées avec le symbole &");
            return true;
        }

        // Construire le message
        StringBuilder messageBuilder = new StringBuilder();
        for (String arg : args) {
            messageBuilder.append(arg).append(" ");
        }
        String rawMessage = messageBuilder.toString().trim();

        // Formater les couleurs et styles
        String formattedMessage = formatColors(rawMessage);

        // Construire le message final
        String finalMessage = formattedMessage;

        // Envoyer le broadcast à tous les joueurs
        Bukkit.broadcastMessage(finalMessage);

        // Confirmation pour l'envoyeur
        sendMessage(sender, plugin.getPrefix() + " §aMessage broadcasté avec succès!");

        return true;
    }

    /**
     * Méthode pour envoyer des messages qui fonctionne avec la console et les joueurs
     */
    private void sendMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            // Pour les joueurs, envoyer normalement
            sender.sendMessage(message);
        } else {
            // Pour la console, enlever les formats qui ne fonctionnent pas
            String consoleMessage = message.replace("§l", "")
                                           .replace("§o", "") 
                                           .replace("§n", "") 
                                           .replace("§m", "") 
                                           .replace("§k", "");
            sender.sendMessage(ChatColor.stripColor(consoleMessage));
        }
    }

    private String formatColors(String message) {
        String formatted = message.replace('&', '§');
        
        if (formatted.contains("§")) {
            formatted += "§r";
        }
        
        return formatted;
    }
}