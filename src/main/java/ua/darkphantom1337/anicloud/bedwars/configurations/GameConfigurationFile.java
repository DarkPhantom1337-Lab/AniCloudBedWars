package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.plugin.Plugin;

public class GameConfigurationFile extends DarkYAMLFileAPI {

    public GameConfigurationFile(Plugin plugin, String gameID) {
        super(plugin, "game_" + gameID, plugin.getDataFolder() + "/" + "Games");
    }

    @Override
    public void firstFill() {
        getFileConfiguration().set(getPlugin().getName(), "GameName: " + getFileName().split("_")[1] + " || Author: DarkPhantom1337");
        setString("GamePrefix", "[§a§lAniCloudBedWars-GAME]");
        setString("GamePrefix", "[§a§lAniCloudBedWars-GAME]");
        saveFileConfiguration();
    }

    public String getGamePrefix(){
        return getString("GamePrefix");
    }


}
