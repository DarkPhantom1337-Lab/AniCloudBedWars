package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class GameConfigurationFile extends DarkYAMLFileAPI {

    public GameConfigurationFile(Plugin plugin, String gameID) {
        super(plugin, "game_" + gameID, plugin.getDataFolder() + "/" + "Games");
    }

    @Override
    public void firstFill() {
        getFileConfiguration().set(getPlugin().getName(), "GameName: " + getFileName().split("_")[1] + " || Author: DarkPhantom1337");
        setString("ArenaName", "[�a�lGAME$" + DarkYAMLFileAPI.getRandomID(4) + "]");
        setString("ArenaDescription", "[�a�lGAME_Description$" + DarkYAMLFileAPI.getRandomID(4) + "]");
        setString("WorldName", "world");
        setInt("TeamAmount", 16);
        setInt("TeamSize", 16);
        setInt("MaxInGameTime", 60 * 60);
        setInt("DeathMatchFloorY", 0);
        setInt("DeathMatchCeilingY", 255);
        setInt("ArenaSize", 333);
        setInt("TimeToStartGameMin", 60);
        setInt("TimeToStartGameMax", 15);
        setInt("TimeToDeathMatch", 60 * 45);
        setString("ArenaWaitSpawnLoc", "world;-1337;66;+1337");
        setString("ArenaCenter", "world;-1337;66;+1337");
        setStringList("BuildersNames", new ArrayList<String>());
        setStringList("AvailableResources", new ArrayList<String>());
        saveFileConfiguration();
    }

    public String getGamePrefix() {
        return getString("GamePrefix");
    }

    public String getGameSValue(String valueID){
        return getString(valueID);
    }

    public Integer getGameIValue(String valueID){
        return getInt(valueID);
    }

    public Location getGameLValue(String valueID){
        return getFileConfiguration().getLocation(valueID);
    }

}
