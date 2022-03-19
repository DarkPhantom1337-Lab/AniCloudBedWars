package ua.darkphantom1337.anicloud.bedwars.controllers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.configurations.GameConfigurationFile;
import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsGame;
import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsPlayer;
import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsSessionID;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsGameStatus;
import ua.darkphantom1337.anicloud.bedwars.enums.OperationResult;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;

import java.util.HashMap;
import java.util.Random;

public class GameController implements Listener {

    private AniCloudBedWars aniCloudBedWars;
    private static GameController gameController;
    private HashMap<String, AniCloudBedWarsModule> availableGames = new HashMap<>();
    private Boolean isEnabled = false;

    public GameController setCurrentBedWarsGame(AniCloudBedWarsGame currentBedWarsGame) {
        this.currentBedWarsGame = currentBedWarsGame;
        return this;
    }

    private AniCloudBedWarsGame currentBedWarsGame;
    private static HashMap<String, GameConfigurationFile> gameConfigurationFileHashMap = new HashMap<>();

    public GameController(AniCloudBedWars aniCloudBedWars) {
        try {
            this.aniCloudBedWars = aniCloudBedWars;
            getAniCloudBedWars().info("Loading 'GameController' ...");
            gameController = this;
            initializeGameController();
        } catch (Exception e) {
            AniCloudBedWars.inst().error("Error in loading game controller. Error:");
            e.printStackTrace();
        }
    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    public static GameController getGameController() {
        return gameController;
    }

    public HashMap<String, AniCloudBedWarsModule> getAvailableGames() {
        return availableGames;
    }

    public void initializeGameController() {
        try {
            getAniCloudBedWars().info("Initializing game controller...");
            Bukkit.getPluginManager().registerEvents(this, aniCloudBedWars);

            isEnabled = true;
        } catch (Exception e) {
            AniCloudBedWars.inst().error("Error in initializing game controller. Error:");
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onJoinToBedWarsGame(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        aniCloudBedWars.info("J1");
        AniCloudBedWarsPlayer bedWarsPlayer = new AniCloudBedWarsPlayer(p.getUniqueId());
        aniCloudBedWars.info("J2");
        if (aniCloudBedWars.getWorkType().equals(WorkType.CREATING)) {
            aniCloudBedWars.info("J3");
            if (p.isOp()) {
                p.sendMessage(aniCloudBedWars.getInGamePrefix() + "§e-> Вы в режиме создания/редактирования игр. \n/acbw editHelp - для помощи.");
            } else {
                p.kickPlayer("Game not started, please wait." + "\nby DarkPhantom1337.");
            }
            aniCloudBedWars.info("J4");
            return;
        }
        aniCloudBedWars.info("J5");
        if (getCurrentBedWarsGame() != null
                && getCurrentBedWarsGame().getGameStatus()
                .equals(AniCloudBedWarsGameStatus.WAIT_PLAYERS)) {
            aniCloudBedWars.info("J6");
            if (getCurrentBedWarsGame().joinToGame(p).equals(OperationResult.SUCCESSFULLY)){
                aniCloudBedWars.info("J7");
                aniCloudBedWars.info("Player " + p.getName() + " join to game. Game: " + getCurrentBedWarsGame().getGameID());
            } else {
                p.kickPlayer("GameFULL"
                        + "\nby DarkPhantom1337.");
            }
            aniCloudBedWars.info("J8");
        } else {
            aniCloudBedWars.info("J9");
            aniCloudBedWars.error("Game not started, please wait."
                    + "LastSessionId: " + AniCloudBedWarsSessionID.get()
                    + "Nick: " + p.getName()
                    + "\nby DarkPhantom1337.");
            p.kickPlayer("Game not started, please wait."
                    + "LastSessionId: " + AniCloudBedWarsSessionID.get()
                    + "\nby DarkPhantom1337.");
            aniCloudBedWars.info("J10");
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (AniCloudBedWars.inst().getGameController().getCurrentBedWarsGame() != null
                            && AniCloudBedWars.inst().getGameController().getCurrentBedWarsGame().getGameStatus()
                            .equals(AniCloudBedWarsGameStatus.DISABLED)) {
                        AniCloudBedWars.inst().getGameController()
                                .startAniCloudBedWarsGame(AniCloudBedWars.inst().getGamesID()
                                        .get(new Random()
                                                .nextInt(AniCloudBedWars.inst()
                                                        .getGamesID().size() - 1)));
                    } else
                        this.cancel();
                }
            }.runTaskLater(aniCloudBedWars, 20 * 60);
            aniCloudBedWars.info("J11");
        }
        aniCloudBedWars.info("J12");
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    public AniCloudBedWarsGame getCurrentBedWarsGame() {
        return currentBedWarsGame;
    }

    public String getCurrentGameSessionID() {
        return AniCloudBedWarsSessionID.get();
    }

    public OperationResult startAniCloudBedWarsGame(String gameID) {
        try {
            AniCloudBedWarsGame bedWarsGame = new AniCloudBedWarsGame(gameID);
            return OperationResult.SUCCESSFULLY.setDescription("Game '" + AniCloudBedWarsSessionID.get() + "' started.");
        } catch (Exception e) {
            aniCloudBedWars.error("Error in start bed wars game.");
            e.printStackTrace();
            return OperationResult.UNSUCCESSFUL;
        }
    }

    public GameConfigurationFile getGameConfigurationFile(String gameID) {
        if (gameConfigurationFileHashMap.containsKey(gameID))
            return gameConfigurationFileHashMap.get(gameID);
        return new GameConfigurationFile(aniCloudBedWars, gameID);
    }
}
