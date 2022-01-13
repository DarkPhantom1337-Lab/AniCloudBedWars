package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.plugin.Plugin;

public class GameConfigurationFile extends DarkYAMLFileAPI {

    public GameConfigurationFile(Plugin plugin) {
        super(plugin, "gameConfiguration", plugin.getDataFolder());
    }

    @Override
    public void firstFill() {
        getFileConfiguration().set(getPlugin().getName(), "Filename: " + getFileName() + " || Author: DarkPhantom1337");
        setString("GamePrefix", "[§a§lAniCloudBedWars-GAME]");
        saveFileConfiguration();
    }

    public String getGamePrefix(){
        return getString("GamePrefix");
    }


}
