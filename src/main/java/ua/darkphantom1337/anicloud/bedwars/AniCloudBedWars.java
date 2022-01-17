package ua.darkphantom1337.anicloud.bedwars;

import org.bukkit.plugin.java.JavaPlugin;
import ua.darkphantom1337.anicloud.bedwars.configurations.ConfigurationsModule;
import ua.darkphantom1337.anicloud.bedwars.configurations.GameConfigurationFile;
import ua.darkphantom1337.anicloud.bedwars.controllers.GameController;
import ua.darkphantom1337.anicloud.bedwars.controllers.HubController;
import ua.darkphantom1337.anicloud.bedwars.controllers.ModuleController;
import ua.darkphantom1337.anicloud.bedwars.entitys.AniCloudBedWarsSessionID;
import ua.darkphantom1337.anicloud.bedwars.enums.ModuleNames;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkStatus;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsGame;
import ua.darkphantom1337.anicloud.bedwars.messages.HubMessageModule;

import java.util.List;

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
    private HubController hubController;
    private GameController gameController;

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

    public HubController getHubController() {
        return hubController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public ConfigurationsModule getConfigurationsModule() {
        return configurationsModule;
    }

    public void onLoad() {
        info("Initializing AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        setWorkStatus(WorkStatus.LOADING);
        aniCloudBedWars = this;
        moduleController = new ModuleController(this);
        info("Loading AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        getModuleController().loadAllModules();
        configurationsModule = (ConfigurationsModule) getModuleController().getModule(ModuleNames.CONFIGURATION);
        hubMessageModule = (HubMessageModule) getModuleController().getModule(ModuleNames.HubMessage);

    }

    public void onEnable() {
        info("Enabling AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        setWorkStatus(WorkStatus.ENABLING);
        getModuleController().enableAllModules();
        if (workType.equals(WorkType.HUB)){
            info("Plugin work type: HUB. Creating and configuring games on this server is not possible.");
            hubController = new HubController(inst());
        }
        if (workType.equals(WorkType.GAME)){
            info("Plugin work type: GAME. It is not possible to create and configure a hub on this server.");
            gameController = new GameController(inst());
            if (getGameController().isEnabled()){
                info("GameController successfully enabled.");
            } else {
                error("GameController not enabled. Error printed...");
            }
        }
        if (workType.equals(WorkType.UNSPECIFIED)){
            error("Plugin work type: UNSPECIFIED. /acbw global changeworktype <TYPE> to change. " +
                    "\nAvailable types: HUB, GAME.");
        }
    }

    public void onDisable() {
        info("Disabling AniCloudBedWars plugin, please wait. by DarkPhantom1337, 2022.");
        setWorkStatus(WorkStatus.DISABLING);
        getModuleController().disableAllModules();
    }

    public void info(String message) {
        System.out.println(this.consolePrefix + " §a[INFO] " + message);
    }

    public void error(String message) {
        System.out.println(this.consolePrefix + " §4[ERROR] " + message);
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
