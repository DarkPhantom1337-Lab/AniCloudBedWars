package ua.darkphantom1337.anicloud.bedwars.entitys;

import org.bukkit.plugin.Plugin;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.configurations.DarkYAMLFileAPI;
import ua.darkphantom1337.anicloud.bedwars.enums.OperationResult;

public class AniCloudBedWarsSessionID {

    private static String sessionID = "GameNotRunning";

    protected AniCloudBedWarsSessionID(String initiator) {
        sessionID = DarkYAMLFileAPI.getRandomID(16);
        AniCloudBedWars.inst().info("Created new BedWarsSessionID: " + sessionID + " Initiator: " + initiator);
    }

    public static String get() {
        return sessionID;
    }

    private OperationResult createNewSessionID(Plugin plugin) {
        try {
            new AniCloudBedWarsSessionID(plugin.getName() + "/" + plugin.getDescription().getAuthors().get(0));
            return OperationResult.SUCCESSFULLY;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OperationResult.PERFORMANCE_ERROR;
    }


}
