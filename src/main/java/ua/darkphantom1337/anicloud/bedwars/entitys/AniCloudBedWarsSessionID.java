package ua.darkphantom1337.anicloud.bedwars.entitys;

import ua.darkphantom1337.anicloud.bedwars.configurations.DarkYAMLFileAPI;

public class AniCloudBedWarsSessionID {

    private static String sessionID = "GameNotRunning";

    protected AniCloudBedWarsSessionID() {
        sessionID = DarkYAMLFileAPI.getRandomID(16);
    }

    public static String get() {
        return sessionID;
    }


}
