package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.plugin.Plugin;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;

public class HubConfigurationFile extends DarkYAMLFileAPI {

    public HubConfigurationFile(Plugin plugin) {
        super(plugin, "hubConfiguration", plugin.getDataFolder());
    }

    @Override
    public void firstFill() {
        getFileConfiguration().set(getPlugin().getName(), "Filename: " + getFileName() + " || Author: DarkPhantom1337");
        setString("HubPrefix", "[§a§lAniCloudBedWars-HUB]");
        setString("HubJoinMessage", "§aÌû ğàäè ïğèâåòñòâîâàòü òåáÿ íà AniCloudBedWars.");
        saveFileConfiguration();
    }

    public String getHubPrefix(){
        return getString("HubPrefix");
    }

    public String getHubJoinMessage(){
        return getString("HubJoinMessage");
    }

}
