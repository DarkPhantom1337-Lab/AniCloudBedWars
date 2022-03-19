package ua.darkphantom1337.anicloud.bedwars;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ua.darkphantom1337.anicloud.bedwars.commands.ACBWCommand;
import ua.darkphantom1337.anicloud.bedwars.configurations.ConfigurationsModule;
import ua.darkphantom1337.anicloud.bedwars.configurations.GameConfigurationFile;
import ua.darkphantom1337.anicloud.bedwars.controllers.BasicController;
import ua.darkphantom1337.anicloud.bedwars.controllers.GameController;
import ua.darkphantom1337.anicloud.bedwars.controllers.HubController;
import ua.darkphantom1337.anicloud.bedwars.controllers.ModuleController;
import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsGame;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsGameStatus;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkStatus;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;
import ua.darkphantom1337.anicloud.bedwars.messages.HubMessageModule;

import java.util.List;
import java.util.Random;

public class AniCloudBedWars extends JavaPlugin {

    private static AniCloudBedWars aniCloudBedWars;
    /**
     * Basic variables
     */
    private String consolePrefix = "[AniCloudBedWars]";
    private String InGamePrefix = "§c[AniCloudBedWarsNotLoad]";
    private String HubPrefix = "§c[AniCloudBedWarsNotLoad]";
    private WorkType workType = WorkType.UNSPECIFIED;
    private WorkStatus workStatus = WorkStatus.DISABLED;

    /**
     * CONTROLLERS
     */

    private ModuleController moduleController;
    private BasicController basicController;
    private GameController gameController;
    private HubController hubController;

    /**
     * MODULES
     */

    private ConfigurationsModule configurationsModule;
    private HubMessageModule hubMessageModule;

    public static AniCloudBedWars getInstance() {
        return aniCloudBedWars;
    }

    public static AniCloudBedWars inst() {
        return aniCloudBedWars;
    }

    public ModuleController getModuleController() {
        return moduleController;
    }

    public BasicController getBasicController() {
        return basicController;
    }

    public HubController getHubController() {
        return hubController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public ConfigurationsModule getConfigurationsModule() {
        return configurationsModule;
    }

    public AniCloudBedWars setConfigurationsModule(ConfigurationsModule configurationsModule) {
        this.configurationsModule = configurationsModule;
        return this;
    }

    public void onLoad() {
        info("Initializing AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        setWorkStatus(WorkStatus.LOADING);
        aniCloudBedWars = this;
        moduleController = new ModuleController(this);
        info("Loading AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        getModuleController().loadAllModules();
    }

    public void onEnable() {
        info("Enabling AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        setWorkStatus(WorkStatus.ENABLING);
        getModuleController().enableAllModules();
        getCommand("acbw").setExecutor(new ACBWCommand());
        if (workType.equals(WorkType.HUB)) {
            info("Plugin work type: HUB. Creating and configuring games on this server is not possible.");
            hubController = new HubController(inst());
            getHubController().enableHubController();
            if (getHubController().isEnabled()) {
                info("HubController successfully enabled.");

            } else {
                error("HubController not enabled. Error printed...");
                this.setEnabled(false);
            }
            return;
        }
        if (workType.equals(WorkType.GAME)) {
            info("Plugin work type: GAME. It is not possible to create and configure a hub on this server.");
            gameController = new GameController(inst());
            if (getGameController().isEnabled()) {
                new ACBWGamePlaceholder().register();
                info("GameController successfully enabled.");
                startAutoGameStarter();
            } else {
                error("GameController not enabled. Error printed...");
                this.setEnabled(false);
            }
            return;

        }
        if (workType.equals(WorkType.CREATING)) {
            info("Plugin work type: CREATING. It is not possible to create and configure a hub on this server.");
            gameController = new GameController(inst());
            if (getGameController().isEnabled()) {
                info("GameController successfully enabled.");
               /* gameController.getGameConfigurationFile("game1337").setLocation("Test.Loc", Bukkit.getWorlds().get(0).getSpawnLocation());
                info(gameController.getGameConfigurationFile("game1337").getLocation("Test.Loc").toString());
            */} else {
                error("GameController not enabled. Error printed...");
                this.setEnabled(false);
            }
            return;

        }
        if (workType.equals(WorkType.UNSPECIFIED)) {
            error("Plugin work type: UNSPECIFIED. /acbw global changeworktype <TYPE> to change. " +
                    "\nAvailable types: HUB, GAME.");
            basicController = new BasicController(this);
            basicController.enableBasicController();
            if (getBasicController().isEnabled()) {
                info("BasicController successfully enabled.");
            } else {
                error("BasicController not enabled. Error printed...");
            }
            return;
        }
    }

    public void startAutoGameStarter() {
        new BukkitRunnable() {
            @Override
            public void run() {
                info("[AutoGameStarter] -> I'm looking for game sessions ...");
                if (getGamesID().size() <= 0) {
                    error("[AutoGameStarter] -> There are no customized games. Please setup game...");
                    return;
                }
                AniCloudBedWarsGame currentGame = getGameController().getCurrentBedWarsGame();
                if (currentGame == null || currentGame.getGameStatus().equals(AniCloudBedWarsGameStatus.DISABLED)) {
                    info("[AutoGameStarter] -> Game not running or disabled... I'm launching a new game, please wait.");
                    if (getGamesID().size() == 1){
                        getGameController()
                                .startAniCloudBedWarsGame(getGamesID()
                                        .get(0));
                    } else {
                        getGameController()
                                .startAniCloudBedWarsGame(getGamesID()
                                        .get(new Random()
                                                .nextInt(getGamesID().size() - 1)));
                    }
                } else {
                    info("[AutoGameStarter] -> The game is already running ...");
                }
            }
        }.runTaskTimer(this,
                20 * 60, 20 * 60 * 5
        );
    }

    public void onDisable() {
        info("Disabling AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        setWorkStatus(WorkStatus.DISABLING);
        getModuleController().disableAllModules();
    }

    public void info(String message) {
        getLogger().info(this.consolePrefix + " §a[INFO] " + message);
    }

    public void error(String message) {
        getLogger().fine(this.consolePrefix + " §4[ERROR] " + message);
    }


    public String getConsolePrefix() {
        return consolePrefix;
    }

    public void setConsolePrefix(String consolePrefix) {
        this.consolePrefix = consolePrefix;
    }

    public String getInGamePrefix() {
        return InGamePrefix;
    }

    public void setInGamePrefix(String inGamePrefix) {
        this.InGamePrefix = inGamePrefix;
    }

    public String getHubPrefix() {
        return HubPrefix;
    }

    public void setHubPrefix(String hubPrefix) {
        this.HubPrefix = hubPrefix;
    }

    public WorkStatus getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(WorkStatus workStatus) {
        this.workStatus = workStatus;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public List<String> getGamesID() {
        return getConfigurationsModule().getGlobalConfigurationFile().getGamesID();
    }

    public GameConfigurationFile getGameData(String gameID) {
        return new GameConfigurationFile(this, gameID);
    }


}
