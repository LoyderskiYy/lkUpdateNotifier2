package ru.LoyderskiYy;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {
    private final UpdateChecker updateChecker;

    public PlayerLoginListener(UpdateChecker updateChecker) {
        this.updateChecker = updateChecker;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (!updateChecker.isUpdateAvailable()) return;

        Player player = event.getPlayer();
        if (player.hasPermission("yourplugin.admin")) {
            player.sendMessage(ChatColor.YELLOW + "[" + updateChecker.getLatestVersion() + "] " +
                    ChatColor.RED + "Доступно обновление плагина!");
            player.sendMessage(ChatColor.YELLOW + "Текущая версия: " + ChatColor.WHITE + updateChecker.getLatestVersion());
            player.sendMessage(ChatColor.YELLOW + "Новая версия: " + ChatColor.WHITE + updateChecker.getLatestVersion());
            player.sendMessage(ChatColor.YELLOW + "Скачать: " + ChatColor.AQUA +
                    "https://github.com/" + UpdateChecker.GITHUB_REPO + "/releases/latest");
        }
    }
}

