package ru.LoyderskiYy;


import org.bukkit.plugin.java.JavaPlugin;

public class UpdateNotifier extends JavaPlugin {
    private UpdateChecker updateChecker;

    @Override
    public void onEnable() {
        updateChecker = new UpdateChecker(this);
        updateChecker.checkForUpdates();

        // Периодическая проверка каждые 30 минут
        getServer().getScheduler().runTaskTimerAsynchronously(this, updateChecker::checkForUpdates, 36000L, 36000L);

        // Регистрация слушателя событий
        getServer().getPluginManager().registerEvents(new PlayerLoginListener(updateChecker), this);
    }
}

