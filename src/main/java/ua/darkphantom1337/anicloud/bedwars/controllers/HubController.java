package ua.darkphantom1337.anicloud.bedwars.controllers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsPlayer;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;
import ua.darkphantom1337.anicloud.bedwars.messages.HubMessageModule;

import java.util.HashMap;

public class HubController implements Listener {

    private AniCloudBedWars aniCloudBedWars;
    private static HubController hubController;
    private HashMap<String, AniCloudBedWarsModule> availableGames = new HashMap<>();

    public HubController(AniCloudBedWars aniCloudBedWars) {
        this.aniCloudBedWars = aniCloudBedWars;
        getAniCloudBedWars().info("Loading 'HubController' ...");
        hubController = this;
        initializeHub();
    }

    public void enableHubController() {
        Bukkit.getPluginManager().registerEvents(this, getAniCloudBedWars());
    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    public static HubController getHubController() {
        return hubController;
    }

    public HashMap<String, AniCloudBedWarsModule> getAvailableGames() {
        return availableGames;
    }

    public void initializeHub() {
        getAniCloudBedWars().info("Initializing hub controller...");
        /**
         * other modules
         */
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onJoinToAniBedWarsHub(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        AniCloudBedWarsPlayer bedWarsPlayer = new AniCloudBedWarsPlayer(p);
        if (bedWarsPlayer.joinToHub()) {
            p.sendMessage(aniCloudBedWars.getHubPrefix() + HubMessageModule.getHubJoinMessage());
            /**
             * handle player hub join
             */
        } else {
            p.sendMessage(aniCloudBedWars.getHubPrefix() + HubMessageModule.getHubJoinErrorMessage());
            p.kickPlayer(HubMessageModule.getHubJoinErrorMessage());
            /**
             * handle player hub quit
             */
        }
    }

}
