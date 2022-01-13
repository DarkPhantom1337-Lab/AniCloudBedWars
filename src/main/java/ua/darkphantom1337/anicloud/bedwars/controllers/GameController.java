package ua.darkphantom1337.anicloud.bedwars.controllers;

import org.bukkit.event.Listener;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;

import java.util.HashMap;

public class GameController implements Listener {

    private AniCloudBedWars aniCloudBedWars;
    private static GameController hubController;
    private HashMap<String, AniCloudBedWarsModule> availableGames = new HashMap<>();

    public GameController(AniCloudBedWars aniCloudBedWars) {
        this.aniCloudBedWars = aniCloudBedWars;
        getAniCloudBedWars().info("Initializing 'HubController' ...");
        hubController = this;
        initializeHub();
    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    public static GameController getHubController() {
        return hubController;
    }


    public HashMap<String, AniCloudBedWarsModule> getAvailableGames() {
        return availableGames;
    }

    public void initializeHub() {
        getAniCloudBedWars().info("Initializing hub...");
        /**
         * other modules
         */
    }


}
