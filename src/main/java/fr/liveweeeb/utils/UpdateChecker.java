package fr.liveweeeb.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateChecker implements Listener {

    private final Plugin plugin;
    private final String url;
    private String currentVersion;
    private boolean updateAvailable = false;

    private final String[] ignoredKeywords = {"beta", "alpha", "snapshot"};

    public UpdateChecker(Plugin plugin, String url) {
        this.plugin = plugin;
        this.url = url;
        this.currentVersion = plugin.getDescription().getVersion();
    }

    public void check() {
        checkAndNotify(false);
    }

    private void checkAndNotify(boolean forJoin) {
        // Vérification activée ?
        if (plugin instanceof fr.liveweeeb.Nocila) {
            fr.liveweeeb.Nocila nocila = (fr.liveweeeb.Nocila) plugin;
            if (!nocila.isUpdateEnabled()) return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                URL versionUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) versionUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String latestVersion = in.readLine().trim(); // actualise la newversion à chaque check
                in.close();

                if (containsIgnoredKeyword(currentVersion) || containsIgnoredKeyword(latestVersion)) return;

                if (isNewerVersion(latestVersion, currentVersion)) {
                    updateAvailable = true;
                    String message = "§c⚠ A new version of nocila is available§r\n"
               + "§7Current version: §c" + currentVersion + "\n"
               + "§7New version: §a" + latestVersion + "\n"
               + "§9§nhttps://modrinth.com/plugin/nocila";

                    // Console
                    plugin.getLogger().warning(message);

                    // Joueurs OP en ligne ou pour l'OP qui vient de se connecter
                    Bukkit.getScheduler().runTask(plugin, () -> {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            if (p.isOp()) p.sendMessage(message);
                        }
                    });

                } else if (!forJoin) {
                    updateAvailable = false;
                    plugin.getLogger().info("Nocila is up to date !");
                }

            } catch (Exception e) {
                plugin.getLogger().warning("Unable to check for updates : " + e.getMessage());
            }
        });
    }

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) return;

        // Des qu'un op se co sa recheck
        checkAndNotify(true);

    }

    // ---------------------
    // Fonctions utilitaires
    // ---------------------

    private boolean containsIgnoredKeyword(String version) {
        version = version.toLowerCase();
        for (String keyword : ignoredKeywords) {
            if (version.contains(keyword)) return true;
        }
        return false;
    }

    private boolean isNewerVersion(String latest, String current) {
        String[] latestParts = latest.split("\\.");
        String[] currentParts = current.split("\\.");
        int length = Math.max(latestParts.length, currentParts.length);

        for (int i = 0; i < length; i++) {
            int latestNum = i < latestParts.length ? parseIntSafe(latestParts[i]) : 0;
            int currentNum = i < currentParts.length ? parseIntSafe(currentParts[i]) : 0;

            if (latestNum > currentNum) return true;
            if (latestNum < currentNum) return false;
        }

        return false;
    }

    private int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
