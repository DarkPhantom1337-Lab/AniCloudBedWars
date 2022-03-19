package ua.darkphantom1337.anicloud.bedwars.messages;

import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.enums.WorkType;
import ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsModule;

public class GameMessageModule implements AniCloudBedWarsModule {

    private AniCloudBedWars aniCloudBedWars;

    private static GameMessageModule gameMessageModule;
    private static String gamePrefix;
    private static String gameJoinMessage;
    private static String gamePlayerJoinMessage;
    private static String gameStarting;
    private static String gameStartingCancel;

    @Override
    public String getModuleName() {
        return "GameMessage";
    }

    @Override
    public Boolean isEnabled() {
        return true;
    }

    @Override
    public void setEnabled(Boolean enabled) {

    }

    public AniCloudBedWars getAniCloudBedWars() {
        return aniCloudBedWars;
    }

    @Override
    public void onLoad() {
        this.aniCloudBedWars = AniCloudBedWars.inst();
        getAniCloudBedWars().info("Loading '" + getModuleName() + "' module...");
        gameMessageModule = this;

    }

    @Override
    public void onEnable() {
        if (AniCloudBedWars.getInstance().getWorkType().equals(WorkType.GAME)) {
            gamePrefix = getAniCloudBedWars().getConfigurationsModule()
                    .getGlobalGameConfigurationFile().getString("GamePrefix");
            gameJoinMessage = getAniCloudBedWars().getConfigurationsModule()
                    .getGlobalGameConfigurationFile().getString("GameJoinMessage");
            gamePlayerJoinMessage = getAniCloudBedWars().getConfigurationsModule()
                    .getGlobalGameConfigurationFile().getString("GamePlayerJoinMessage");
            System.out.println(gamePlayerJoinMessage);
            gameStarting = getAniCloudBedWars().getConfigurationsModule()
                    .getGlobalGameConfigurationFile().getString("GameStarting");
            gameStartingCancel = getAniCloudBedWars().getConfigurationsModule()
                    .getGlobalGameConfigurationFile().getString("GameStartingCancel");
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onReload() {
        getAniCloudBedWars().info("Reloading '" + getModuleName() + "' module...");
    }

    public static String getGamePrefix() {
        return gameMessageModule.gamePrefix;
    }

    public static String getGameJoinMessage(String playerName, Integer needToStartPlayer) {
        String tempJoinMessage = "" + gameJoinMessage;
        tempJoinMessage = replaceColors(tempJoinMessage);
        tempJoinMessage = replaceGamePrefix(tempJoinMessage);
        tempJoinMessage = replaceGameJoinPlayer(tempJoinMessage, playerName);
        tempJoinMessage = replaceGameNeedToStartPlayer(tempJoinMessage, needToStartPlayer);
        return tempJoinMessage;
    }

    public static String getGamePlayerJoinMessage(String playerName, Integer needToStartPlayer) {
        System.out.println(gamePlayerJoinMessage);
        String tempJoinMessage = "" + gamePlayerJoinMessage;
        tempJoinMessage = replaceColors(tempJoinMessage);
        tempJoinMessage = replaceGamePrefix(tempJoinMessage);
        tempJoinMessage = replaceGameJoinPlayer(tempJoinMessage, playerName);
        tempJoinMessage = replaceGameNeedToStartPlayer(tempJoinMessage, needToStartPlayer);
        System.out.println(tempJoinMessage + " te,p");
        return tempJoinMessage;
    }

    public static String getGameStartingMessage(Integer timeToStart) {
        String tempJoinMessage = "" + gameStarting;
        tempJoinMessage = replaceColors(tempJoinMessage);
        tempJoinMessage = replaceGamePrefix(tempJoinMessage);
        tempJoinMessage = replaceGameTimeToStart(tempJoinMessage, timeToStart);
        return tempJoinMessage;
    }

    public static String getGameStartingCancelMessage() {
        String tempJoinMessage = "" + gameStartingCancel;
        tempJoinMessage = replaceColors(tempJoinMessage);
        tempJoinMessage = replaceGamePrefix(tempJoinMessage);
        return tempJoinMessage;
    }

    private static String replaceColors(String message) {
        if (message.contains("&"))
            message = message.replace("&", "§");
        return message;
    }

    private static String replaceGamePrefix(String message) {
        if (message.contains("%gamePrefix%"))
            message = message.replace("%gamePrefix%", getGamePrefix());
        return message;
    }

    private static String replaceGameJoinPlayer(String message, String gamePlayerJoinName) {
        if (message.contains("%gameJoinPlayer%"))
            message = message.replace("%gameJoinPlayer%", gamePlayerJoinName);
        return message;
    }

    private static String replaceGameNeedToStartPlayer(String message, Integer needToStartPlayer) {
        if (message.contains("%gameNeedToStartPlayer%"))
            message = message.replace("%gameNeedToStartPlayer%", needToStartPlayer.toString());
        return message;
    }

    private static String replaceGameTimeToStart(String message, Integer timeToStart) {
        if (message.contains("%gameTimeToStart%"))
            message = message.replace("%gameTimeToStart%", timeToStart.toString());
        return message;
    }


}
