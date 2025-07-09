package ru.LoyderskiYy;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

public class UpdateChecker {
    static final String GITHUB_REPO = "LoyderskiYy/lkUpdateNotifier2";
    private final JavaPlugin plugin;
    private String latestVersion;
    private boolean updateAvailable = false;

    public UpdateChecker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void checkForUpdates() {
        try {
            URL url = new URL("https://api.github.com/repos/" + GITHUB_REPO + "/releases/latest");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");

            if (conn.getResponseCode() != 200) {
                plugin.getLogger().log(Level.WARNING, "Не удалось проверить обновления. Код ответа: " + conn.getResponseCode());
                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            JSONObject json = (JSONObject) new JSONParser().parse(reader);
            latestVersion = (String) json.get("tag_name");

            // Удаляем 'v' из версии если есть (v1.0.0 → 1.0.0)
            if (latestVersion.startsWith("v")) {
                latestVersion = latestVersion.substring(1);
            }

            String currentVersion = plugin.getDescription().getVersion();
            updateAvailable = !currentVersion.equals(latestVersion);

            if (updateAvailable) {
                plugin.getLogger().log(Level.INFO, "Доступно обновление! Текущая версия: " + currentVersion
                        + ", Новая версия: " + latestVersion);
            }

        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Ошибка при проверке обновлений: " + e.getMessage());
        }
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }
}

