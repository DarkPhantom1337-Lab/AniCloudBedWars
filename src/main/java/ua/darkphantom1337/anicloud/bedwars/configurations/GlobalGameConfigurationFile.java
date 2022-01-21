package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsTeamColor;

public class GlobalGameConfigurationFile extends DarkYAMLFileAPI {

    public GlobalGameConfigurationFile(Plugin plugin) {
        super(plugin, "globalGameConfiguration", plugin.getDataFolder());
    }

    @Override
    public void firstFill() {
        getFileConfiguration().set(getPlugin().getName(), "Filename: " + getFileName() + " || Author: DarkPhantom1337");
        setString("GamePrefix", "[§a§lAniCloudBedWars-GAME]");
        for (AniCloudBedWarsTeamColor teamColor : AniCloudBedWarsTeamColor.values()){
            setString("Team." + teamColor.name() + ".Name", teamColor.name().toLowerCase() + "Team");
        }
        setString("GameJoinMessage", "%gamePrefix% -> Игрок %gameJoinPlayer% зашёл в игру." +
                " Для старта игры нужно ещё %gameNeedToStartPlayer%");
        setString("GamePlayerJoinMessage", "%gamePrefix% -> Вы зашли в игру." +
                " Для старта игры нужно ещё %gameNeedToStartPlayer%");
        setString("GameStarting", "%gamePrefix% -> До старта игры осталось %gameTimeToStart% секунд.");
        setString("GameStartingCancel", "%gamePrefix% -> Старт игры отменён. Не хватает игроков для старта.");
        saveFileConfiguration();
    }

    public String getGamePrefix() {
        return getString("GamePrefix");
    }

    public String getSValue(String valueID) {
        return getString(valueID);
    }

    public Integer getIValue(String valueID) {
        return getInt(valueID);
    }

    public Location getLValue(String valueID) {
        return getFileConfiguration().getLocation(valueID);
    }

}
