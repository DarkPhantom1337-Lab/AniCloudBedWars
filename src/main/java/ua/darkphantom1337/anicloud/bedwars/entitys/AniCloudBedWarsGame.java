package ua.darkphantom1337.anicloud.bedwars.entitys;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsGameStatus;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsResource;
import ua.darkphantom1337.anicloud.bedwars.enums.AniCloudBedWarsTeamColor;
import ua.darkphantom1337.anicloud.bedwars.enums.GameType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AniCloudBedWarsGame implements ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsGame {

    static String gameID, arenaName, arenaDescription, serverName, winnerName;
    static Integer teamAmount, teamSize, maxPlayers, survivingPlayersAmount,
            survivingTeamAmount,
            maxInGameTime, timeToDeathMatch, deathMatchFloorY, deathMatchCeilingY,
            arenaSize, allPlayersAmount, timeToStartGameMin, timeToStartGameMax;
    static Location arenaWaitSpawnLoc, playerInGameSpawnLoc, arenaCenter;
    static Date startedGameDate, startedDeathMatchDate, endGameDate;
    static AniCloudBedWarsGameStatus gameStatus = AniCloudBedWarsGameStatus.DISABLED;
    static List<String> buildersNames;
    static List<AniCloudBedWarsResource> availableResources;
    static List<Player> allPlayersInGame, survivingPlayersInGame;

    public AniCloudBedWarsGame(String gameID) {
        AniCloudBedWarsGame currentGame = (AniCloudBedWarsGame) AniCloudBedWars.inst().getCurrentBedWarsGame();
        if (currentGame == null || currentGame.getGameStatus().equals(AniCloudBedWarsGameStatus.DISABLED)) {
            setGameStatus(AniCloudBedWarsGameStatus.LOADING);
            /**
             * LOADING GAME DATA
             */
        }
    }

    @Override
    public String getGameID() {
        return gameID;
    }

    @Override
    public String getArenaName() {
        return arenaName;
    }

    @Override
    public String getArenaDescription() {
        return arenaDescription;
    }

    @Override
    public Location getArenaWaitSpawnLoc() {
        return arenaWaitSpawnLoc;
    }

    @Override
    public List<String> getBuilderNames() {
        return buildersNames;
    }

    @Override
    public Integer getTeamAmount() {
        return teamAmount;
    }

    @Override
    public Integer getSurvivingTeamAmount() {
        return survivingTeamAmount;
    }

    @Override
    public Integer getTeamSize() {
        return teamSize;
    }

    @Override
    public Integer getMaxPlayers() {
        return teamAmount * teamSize;
    }

    @Override
    public Integer getSurvivingPlayersAmount() {
        return survivingPlayersAmount;
    }

    @Override
    public List<Player> getAllPlayersInGame() {
        return allPlayersInGame;
    }

    @Override
    public List<Player> getSurvivingPlayers() {
        return survivingPlayersInGame;
    }

    @Override
    public Location getPlayerInGameSpawnLoc() {
        return playerInGameSpawnLoc;
    }

    @Override
    public HashMap<AniCloudBedWarsTeamColor, Location> getAllTeamSellersLoc() {
        return null;
    }

    @Override
    public Location getSellerLoc(AniCloudBedWarsTeamColor team) {
        return null;
    }

    @Override
    public List<AniCloudBedWarsResource> getAvailableResources() {
        return availableResources;
    }

    @Override
    public HashMap<AniCloudBedWarsTeamColor, HashMap<AniCloudBedWarsResource, Location>> getAllTeamResourcesLoc() {
        return null;
    }

    @Override
    public HashMap<AniCloudBedWarsResource, Location> getTeamResourcesLoc(AniCloudBedWarsTeamColor team) {
        return null;
    }

    @Override
    public Location getWaitStartRoomLoc() {
        return arenaWaitSpawnLoc;
    }

    @Override
    public Integer getTimeToStartGameMin() {
        return timeToStartGameMin;
    }

    @Override
    public Integer getTimeToStartGameMax() {
        return timeToStartGameMax;
    }

    @Override
    public Integer getMaxInGameTime() {
        return maxInGameTime;
    }

    @Override
    public Integer getTimeToDeathMatch() {
        return timeToDeathMatch;
    }

    @Override
    public Integer getDeathMatchFloorY() {
        return deathMatchFloorY;
    }

    @Override
    public Integer getDeathMatchCeilingY() {
        return deathMatchCeilingY;
    }

    @Override
    public HashMap<AniCloudBedWarsTeamColor, Location> getAllTeamBedLoc() {
        return null;
    }

    @Override
    public Location getBedLoc(AniCloudBedWarsTeamColor team) {
        return null;
    }

    @Override
    public Integer getArenaSize() {
        return arenaSize;
    }

    @Override
    public Location getArenaCenter() {
        return arenaCenter;
    }

    @Override
    public GameType getGameType() {
        return null;
    }

    @Override
    public AniCloudBedWarsGameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public Boolean joinToGame(Player player) {
        /**
         * handle player join
         */
        if (player == null) {
            AniCloudBedWars.inst().error("Player not identified.");
            return false;
        }
        if (allPlayersAmount < maxPlayers) {

            // send msg for join
            //JOIN - ALLOW
            return true;
        }
        // JOIN - DENY
        return false;
    }

    @Override
    public Boolean quitGame(Player player) {
        /**
         * handle player join
         */
        if (player == null) {
            AniCloudBedWars.inst().error("Player not identified.");
            return false;
        }
        if (allPlayersInGame.contains(player)) {
            allPlayersAmount = allPlayersAmount - 1;
            allPlayersInGame.remove(player);
            // send msg for quit
            return true;
        }
        AniCloudBedWars.inst().error("Player " + player.getName() + " is not in the game.");
        return false;
    }

    @Override
    public Boolean kickOutOfTheGame(Player player, String cause) {
        if (player == null) {
            AniCloudBedWars.inst().error("Player not identified.");
            return false;
        }
        Boolean isQuit = quitGame(player);
        if (isQuit)
            player.kickPlayer("К сожалению Вам не разрешено играть дальше.\n"
                    + cause);
        return isQuit;
    }

    @Override
    public Boolean hidePlayer(Player player) {
        if (player == null) {
            getAniCloudBedWars().error("Player not identified.");
            return false;
        }
        /**
         * QUIT WITHOUT KICK.
         */
        if (quitGame(player)) {
            player.setGameMode(GameMode.SPECTATOR);
            player.setFlying(true);
            player.setPlayerListName("AniCloudNPC");
        }
        return false;
    }

    @Override
    public Integer getAllPlayersAmount() {
        return allPlayersAmount;
    }


    @Override
    public void setEnabled(Boolean enabled) {

    }

    @Override
    public AniCloudBedWars getAniCloudBedWars() {
        return AniCloudBedWars.inst();
    }

    @Override
    public void setGameStatus(AniCloudBedWarsGameStatus gameStatus) {
        AniCloudBedWarsGame.gameStatus = gameStatus;
    }

    @Override
    public Date getStartedGameDate() {
        return startedGameDate;
    }

    @Override
    public Date getStartedDeathMatchDate() {
        return startedDeathMatchDate;
    }

    @Override
    public Date getEndGameDate() {
        return endGameDate;
    }

    @Override
    public String getWinnerName() {
        return winnerName;
    }

    @Override
    public String getGameDataForBungee() {
        return getGameID() + getArenaName() + "";
    }

    /**
     * SET METHODS
     */

    public AniCloudBedWarsGame setGameID(String gameID) {
        this.gameID = gameID;
        return this;
    }

    public AniCloudBedWarsGame setArenaName(String arenaName) {
        this.arenaName = arenaName;
        return this;
    }

    public AniCloudBedWarsGame setArenaDescription(String arenaDescription) {
        this.arenaDescription = arenaDescription;
        return this;
    }

    public AniCloudBedWarsGame setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public AniCloudBedWarsGame setWinnerName(String winnerName) {
        this.winnerName = winnerName;
        return this;
    }

    public AniCloudBedWarsGame setTeamAmount(Integer teamAmount) {
        this.teamAmount = teamAmount;
        return this;
    }

    public AniCloudBedWarsGame setTeamSize(Integer teamSize) {
        this.teamSize = teamSize;
        return this;
    }

    public AniCloudBedWarsGame setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }

    public AniCloudBedWarsGame setSurvivingPlayersAmount(Integer survivingPlayersAmount) {
        this.survivingPlayersAmount = survivingPlayersAmount;
        return this;
    }

    public AniCloudBedWarsGame setMaxInGameTime(Integer maxInGameTime) {
        this.maxInGameTime = maxInGameTime;
        return this;
    }

    public AniCloudBedWarsGame setTimeToDeathMatch(Integer timeToDeathMatch) {
        this.timeToDeathMatch = timeToDeathMatch;
        return this;
    }

    public AniCloudBedWarsGame setDeathMatchFloorY(Integer deathMatchFloorY) {
        this.deathMatchFloorY = deathMatchFloorY;
        return this;
    }

    public AniCloudBedWarsGame setDeathMatchCeilingY(Integer deathMatchCeilingY) {
        this.deathMatchCeilingY = deathMatchCeilingY;
        return this;
    }

    public AniCloudBedWarsGame setArenaSize(Integer arenaSize) {
        this.arenaSize = arenaSize;
        return this;
    }

    public AniCloudBedWarsGame setAllPlayersAmount(Integer allPlayersAmount) {
        this.allPlayersAmount = allPlayersAmount;
        return this;
    }

    public AniCloudBedWarsGame setTimeToStartGameMin(Integer timeToStartGameMin) {
        this.timeToStartGameMin = timeToStartGameMin;
        return this;
    }

    public AniCloudBedWarsGame setTimeToStartGameMax(Integer timeToStartGameMax) {
        this.timeToStartGameMax = timeToStartGameMax;
        return this;
    }

    public AniCloudBedWarsGame setArenaWaitSpawnLoc(Location arenaWaitSpawnLoc) {
        this.arenaWaitSpawnLoc = arenaWaitSpawnLoc;
        return this;
    }

    public AniCloudBedWarsGame setPlayerInGameSpawnLoc(Location playerInGameSpawnLoc) {
        this.playerInGameSpawnLoc = playerInGameSpawnLoc;
        return this;
    }

    public AniCloudBedWarsGame setArenaCenter(Location arenaCenter) {
        this.arenaCenter = arenaCenter;
        return this;
    }

    public AniCloudBedWarsGame setStartedGameDate(Date startedGameDate) {
        this.startedGameDate = startedGameDate;
        return this;
    }

    public AniCloudBedWarsGame setStartedDeathMatchDate(Date startedDeathMatchDate) {
        this.startedDeathMatchDate = startedDeathMatchDate;
        return this;
    }

    public AniCloudBedWarsGame setEndGameDate(Date endGameDate) {
        this.endGameDate = endGameDate;
        return this;
    }

    public AniCloudBedWarsGame setBuildersNames(List<String> buildersNames) {
        this.buildersNames = buildersNames;
        return this;
    }

    public AniCloudBedWarsGame setAvailableResources(List<AniCloudBedWarsResource> availableResources) {
        this.availableResources = availableResources;
        return this;
    }

    public AniCloudBedWarsGame setAllPlayersInGame(List<Player> allPlayersInGame) {
        this.allPlayersInGame = allPlayersInGame;
        return this;
    }

    public AniCloudBedWarsGame setSurvivingPlayersInGame(List<Player> survivingPlayersInGame) {
        this.survivingPlayersInGame = survivingPlayersInGame;
        return this;
    }

    /**
     * Main methods
     */

    public void initializeGameStartMin() {

    }

    public void initializeGameStartMax() {

    }

    public void initializeDeathMatchStart() {

    }

    public void initializeEndGame() {

    }

}
