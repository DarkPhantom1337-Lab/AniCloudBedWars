package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.plugin.Plugin;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;

import java.util.ArrayList;
import java.util.List;

public class GlobalConfigurationFile extends DarkYAMLFileAPI {

    public GlobalConfigurationFile(Plugin plugin) {
        super(plugin, "globalConfiguration", plugin.getDataFolder());
    }

    @Override
    public void firstFill() {
        getFileConfiguration().set(getPlugin().getName(), "Filename: " + getFileName() + " || Author: DarkPhantom1337");
        setString("ServerName", "PHANTOM-HUB");
        setString("WorkType", WorkType.HUB.name());
        setStringList("GamesID", new ArrayList<String>());
        setString("InGamePrefix", "§a§lAniCloudBedWars");

        saveFileConfiguration();
    }

    public WorkType getWorkType() {
        return WorkType.valueOf(getString("WorkType"));
    }

    public String getInGamePrefix() {
        return getString("InGamePrefix");
    }

    public List<String> getGamesID() {
        return getStringList("GamesID");
    }

}
