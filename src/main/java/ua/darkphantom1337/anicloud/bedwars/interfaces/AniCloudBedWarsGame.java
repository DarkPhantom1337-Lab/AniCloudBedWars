package ua.darkphantom1337.anicloud.bedwars.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.enums.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface AniCloudBedWarsGame {

    String getGameID();

    String getArenaName();

    String getArenaDescription();

    String getWorldName();

    Location getArenaWaitSpawnLoc();

    List<String> getBuilderNames();

    Integer getTeamAmount();

    Integer getSurvivingTeamAmount();

    Integer getTeamSize();

    Integer getMinPlayers();

    Integer getMaxPlayers();

    Integer getSurvivingPlayersAmount();

    List<Player> getAllPlayersInGame();

    List<Player> getSurvivingPlayers();

    Location getPlayerInGameSpawnLoc();

    HashMap<AniCloudBedWarsTeamColor, Location> getAllTeamSellersLoc();

    Location getSellerLoc(AniCloudBedWarsTeamColor team);

    List<AniCloudBedWarsResource> getAvailableResources();

    HashMap<AniCloudBedWarsTeamColor, HashMap<AniCloudBedWarsResource, Location>> getAllTeamResourcesLoc();

    HashMap<AniCloudBedWarsResource, Location> getTeamResourcesLoc(AniCloudBedWarsTeamColor team);

    Location getWaitStartRoomLoc();

    Integer getTimeToStartGameMin();

    Integer getTimeToStartGameMax();

    Integer getMaxInGameTime();

    Integer getTimeToDeathMatch();

    Integer getDeathMatchFloorY();

    Integer getDeathMatchCeilingY();

    HashMap<AniCloudBedWarsTeamColor, Location> getAllTeamBedLoc();

    Location getBedLoc(AniCloudBedWarsTeamColor team);

    Integer getArenaSize();

    Location getArenaCenter();

    GameType getGameType();

    AniCloudBedWarsGameStatus getGameStatus();

    String getServerName();

    OperationResult joinToGame(Player player);

    OperationResult quitGame(Player player);

    Boolean kickOutOfTheGame(Player player, String cause);

    Boolean hidePlayer(Player player);

    Integer getAllPlayersAmount();

    void setEnabled(Boolean enabled);

    AniCloudBedWars getAniCloudBedWars();

    void setGameStatus(AniCloudBedWarsGameStatus gameStatus);

    Date getStartedGameDate();

    Date getStartedDeathMatchDate();

    Date getEndGameDate();

    String getWinnerName();

    String getGameDataForBungee();

}
