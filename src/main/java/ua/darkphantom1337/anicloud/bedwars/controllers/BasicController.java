package ua.darkphantom1337.anicloud.bedwars.controllers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;

public class BasicController implements Listener {

    private AniCloudBedWars aniCloudBedWars;
    private static BasicController basicController;
    private static Boolean isEnabled = false;

    public BasicController(AniCloudBedWars aniCloudBedWars) {
        try {
            this.aniCloudBedWars = aniCloudBedWars;
            getAniCloudBedWars().info("Loading 'BasicController' ...");
            basicController = this;
            initializeBasicController();
            isEnabled = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void enableBasicController() {
        Bukkit.getPluginManager().registerEvents(this, getAniCloudBedWars());
    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    public static BasicController getBasicController() {
        return basicController;
    }

    public Boolean isEnabled(){
        return isEnabled;
    }

    public void initializeBasicController() {
        getAniCloudBedWars().info("Initializing basic controller...");
        /**
         *
         */
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onJoinToAniBedWarsServer(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.isOp()) {
            p.sendMessage("§a[AniCloudBedWars] §f-> §eСпециализация сервера не установлена, для установки: "
                    + "\n§f- §a/acbw global changeworktype <СПЕЦИАЛИЗАЦИЯ> (acbw.global.changeworktype)"
                    + "\n§eДоступные специализации: §aHUB§e, §aGAME§e.");
        } else {
            /**
             * It's unreal
             */
        }
    }

    @EventHandler
    public void onLoginToACBWServer(PlayerLoginEvent e) {
        if (e.getPlayer() == null || !e.getPlayer().isOp()) {
            e.setKickMessage("§e-> AniCloud <-"
                    + "\n§cВход разрешен только администраторам "
                    + "\n§cданного сервера."
                    + "\n§4Administrator rights required(*)."
                    + "\nKick done on behalf of AniCloudBedWars.");
            e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        }
    }

}
