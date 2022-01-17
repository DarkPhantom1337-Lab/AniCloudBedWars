package ua.darkphantom1337.anicloud.bedwars.entitys;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.configurations.GameConfigurationFile;
import ua.darkphantom1337.anicloud.bedwars.enums.*;
import ua.darkphantom1337.anicloud.bedwars.messages.GameMessageModule;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AniCloudBedWarsGame implements ua.darkphantom1337.anicloud.bedwars.interfaces.AniCloudBedWarsGame {

    static String gameID, arenaName, arenaDescription, worldName, serverName, winnerName;
    static Integer teamAmount, teamSize, minPlayers, maxPlayers, survivingPlayersAmount,
            survivingTeamAmount,
            maxInGameTime, timeToDeathMatch, deathMatchFloorY, deathMatchCeilingY,
            arenaSize, allPlayersAmount, timeToStartGameMin, timeToStartGameMax, timeToEndGame, timeToStartGame;
    static Location arenaWaitSpawnLoc, playerInGameSpawnLoc, arenaCenter;
    static Date startedGameDate, startedDeathMatchDate, endGameDate;
    static AniCloudBedWarsGameStatus gameStatus = AniCloudBedWarsGameStatus.DISABLED;
    static List<String> buildersNames;
    static List<AniCloudBedWarsResource> availableResources;
    static List<Player> allPlayersInGame, survivingPlayersInGame;
    static Boolean isInitializeStartMin, isInitializeStartMax, isInitializeDeathMatchStart, isInitializeEndGame;

    public AniCloudBedWarsGame(String gameID) {
        if (!AniCloudBedWars.inst().getWorkType().equals(WorkType.GAME)) {
            AniCloudBedWars.inst().error("Game '" + gameID + "' cannot be started because the plugin works in mode " + AniCloudBedWars.inst().getWorkType().name());
            return;
        }
        AniCloudBedWarsGame currentGame = (AniCloudBedWarsGame) AniCloudBedWars.inst().getGameController().getCurrentBedWarsGame();
        if (currentGame == null || currentGame.getGameStatus().equals(AniCloudBedWarsGameStatus.DISABLED)) {
            setGameStatus(AniCloudBedWarsGameStatus.LOADING);
            new AniCloudBedWarsSessionID("AniCloudBedWars/DarkPhantom1337");
            /**
             * LOADING GAME DATA
             */
            AniCloudBedWars.inst().info("Loading '" + gameID + "' game...");
            if (AniCloudBedWars.inst().getGamesID().contains(gameID)) {
                /**
                 * Creating and setting up an arena.
                 */
                GameConfigurationFile gameData = AniCloudBedWars.inst().getGameData(gameID);
                setArenaName(gameData.getGameSValue("ArenaName"));
                setArenaDescription(gameData.getGameSValue("ArenaDescription"));
                setWorldName(gameData.getGameSValue("WorldName"));
                Bukkit.getWorld(getWorldName())
                        .getEntitiesByClasses(Player.class)
                        .stream()
                        .map(p -> (Player) p)
                        .collect(Collectors.toList())
                        .forEach(p -> p.kickPlayer("§cИзвините, для создания новой игры Вы должны выйти. " +
                                "\nПодождите пару минут," +
                                "\n мы создаём для Вас новую игру"));
                setServerName(AniCloudBedWars.inst().getConfigurationsModule().getGlobalConfigurationFile().getString("ServerName"));
                setTeamAmount(gameData.getGameIValue("TeamAmount"));
                setTeamSize(gameData.getGameIValue("TeamSize"));
                setSurvivingPlayersAmount(0);
                setMaxInGameTime(gameData.getGameIValue("MaxInGameTime"));
                timeToEndGame = maxInGameTime;
                timeToStartGame = timeToStartGameMin;
                setMinPlayers(gameData.getGameIValue("MinPlayers"));
                setTimeToDeathMatch(gameData.getGameIValue("TimeToDeathMatch"));
                setDeathMatchFloorY(gameData.getGameIValue("DeathMatchFloorY"));
                setDeathMatchCeilingY(gameData.getGameIValue("DeathMatchCeilingY"));
                setArenaSize(gameData.getGameIValue("ArenaSize"));
                setAllPlayersAmount(0);
                setTimeToStartGameMin(gameData.getGameIValue("TimeToStartGameMin"));
                setTimeToStartGameMax(gameData.getGameIValue("TimeToStartGameMax"));
                setArenaWaitSpawnLoc(gameData.getGameLValue("ArenaWaitSpawnLoc"));
                setArenaCenter(gameData.getGameLValue("ArenaCenter"));
                setStartedGameDate(new Date());
                setStartedDeathMatchDate(new Date());
                setEndGameDate(new Date());
                setBuildersNames(gameData.getStringList("BuildersNames"));
                setAvailableResources(gameData.getStringList("AvailableResources").stream()
                        .map(s -> AniCloudBedWarsResource.valueOf(s)).collect(Collectors.toList()));
                setSurvivingPlayersInGame(new ArrayList<Player>());
                setAllPlayersInGame(new ArrayList<Player>());
                setGameStatus(AniCloudBedWarsGameStatus.LOADING_MAP);
                AniCloudBedWars.inst().info("Loading MAP for the '" + gameID + "' game...");
                getArenaWaitSpawnLoc().getWorld().loadChunk(arenaWaitSpawnLoc.getChunk());
                getArenaCenter().getWorld().loadChunk(arenaCenter.getChunk());
            } else {
                AniCloudBedWars.inst().error("Error on load '" + gameID + "' game.Game not configured or created.");
                return;
            }
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
    public String getWorldName() {
        return worldName;
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
    public Integer getMinPlayers() {
        return minPlayers;
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
    public OperationResult joinToGame(Player player) {
        /**
         * handle player join
         */
        if (player == null) {
            AniCloudBedWars.inst().error("Player not identified.");
            return OperationResult.UNSUCCESSFUL;
        }
        if (allPlayersAmount < maxPlayers) {
            allPlayersAmount++;
            player.teleport(getArenaWaitSpawnLoc());
            String joinMessage = GameMessageModule.getGameJoinMessage(player.getName(), maxPlayers - allPlayersAmount);
            getAllPlayersInGame().forEach(gamePlayer -> gamePlayer.sendMessage(joinMessage));
            getAllPlayersInGame().add(player);
            player.sendMessage(GameMessageModule.getGamePlayerJoinMessage(player.getName(), maxPlayers - allPlayersAmount));
            if (giveWaitStartGameItems(player).equals(OperationResult.SUCCESSFULLY)) {
                updatePlayersAmount();
                return OperationResult.SUCCESSFULLY;
            } else {
                AniCloudBedWars.inst().error("GameWaitItems not give for player " + player.getName() + ". Error printed...");
                return OperationResult.PERFORMANCE_ERROR;
            }
        }
        return OperationResult.UNSUCCESSFUL;
    }

    public void updatePlayersAmount() {
        if (allPlayersAmount == getMaxPlayers()) {
            if (isInitializeStartMin && !isInitializeStartMax) {
                initializeGameStartMax();
                return;
            }
        }
        if (allPlayersAmount >= getMinPlayers()) {
            if (!isInitializeStartMin) {
                initializeGameStartMin();
                return;
            }
        }
        if (!isInitializeStartMin && !isInitializeStartMax && gameStatus.equals(AniCloudBedWarsGameStatus.WAIT_PLAYERS)) {

        }
    }

    public OperationResult giveWaitStartGameItems(Player p) {
        try {
            /**
             * GIVE ITEM TO PLAYER (CHOOSE COMMAND AND ABILITIES
             */

        } catch (Exception e) {
            e.printStackTrace();
            return OperationResult.PERFORMANCE_ERROR;
        }
        return OperationResult.UNSUCCESSFUL;
    }

    @Override
    public OperationResult quitGame(Player player) {
        /**
         * handle player join
         */
        if (player == null) {
            AniCloudBedWars.inst().error("Player not identified.");
            return OperationResult.UNSUCCESSFUL;
        }
        if (allPlayersInGame.contains(player)) {
            allPlayersAmount = allPlayersAmount - 1;
            allPlayersInGame.remove(player);
            // send msg for quit
            return OperationResult.SUCCESSFULLY;
        }
        AniCloudBedWars.inst().error("Player " + player.getName() + " is not in the game.");
        return OperationResult.PERFORMANCE_ERROR;
    }

    @Override
    public Boolean kickOutOfTheGame(Player player, String cause) {
        if (player == null) {
            AniCloudBedWars.inst().error("Player not identified.");
            return false;
        }
        OperationResult isQuit = quitGame(player);
        if (isQuit.equals(OperationResult.SUCCESSFULLY))
            player.kickPlayer("К сожалению Вам не разрешено играть дальше.\n"
                    + cause);
        return true;
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
        if (quitGame(player).equals(OperationResult.SUCCESSFULLY)) {
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

    public AniCloudBedWarsGame setWorldName(String worldName) {
        this.worldName = worldName;
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

    public static void setMinPlayers(Integer minPlayers) {
        AniCloudBedWarsGame.minPlayers = minPlayers;
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
        isInitializeStartMin = true;
        setGameStatus(AniCloudBedWarsGameStatus.STARTING_WAIT_PLAYERS);
        startTimeToStartTicker();
    }

    public void initializeGameStartMax() {
        isInitializeStartMax = true;
        setGameStatus(AniCloudBedWarsGameStatus.STARTING);
        if (timeToStartGame > timeToStartGameMax)
            timeToStartGame = timeToStartGameMax;
    }

    public void initializeDeathMatchStart() {
        isInitializeDeathMatchStart = true;
    }

    public void initializeEndGame() {
        isInitializeEndGame = true;
    }

    public void onGameStart() {
        startMainTimeTicker();
        /**
         * send msg to all
         */
    }


    /**
     * TIMERS
     */

    public void startMainBossBarUpdater() {
        /**
         * BossBar Updater
         */

    }

    public void startMainTimeTicker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (timeToEndGame <= 0) {
                    initializeEndGame();
                    this.cancel();
                }
                if (timeToEndGame - timeToDeathMatch <= 0) {
                    initializeDeathMatchStart();
                }
                timeToEndGame--;
            }
        }.runTaskTimer(getAniCloudBedWars(), 20, 20);
    }

    public void startTimeToStartTicker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (allPlayersAmount < minPlayers) {
                    timeToStartGame = timeToStartGameMin;
                    isInitializeStartMin = false;
                    isInitializeStartMax = false;
                    setGameStatus(AniCloudBedWarsGameStatus.WAIT_PLAYERS);
                    String msg = GameMessageModule.getGameStartingCancelMessage();
                    allPlayersInGame.forEach(p -> p.sendMessage(msg));
                    this.cancel();
                }
                if (timeToStartGame <= 0) {
                    onGameStart();
                    this.cancel();
                }
                if (timeToStartGame < 30) {
                    String msg = GameMessageModule.getGameStartingMessage(timeToStartGame);
                    allPlayersInGame.forEach(p -> p.sendMessage(msg));
                }
                if (timeToStartGame < 10) {
                    //String title = GameMessageModule.getGameStartingMessage(timeToStartGame);
                    allPlayersInGame.forEach(p -> p.sendTitle("§eИгра начнёться через", "§a" + timeToStartGame));
                }
                timeToStartGame--;
            }
        }.runTaskTimer(getAniCloudBedWars(), 20, 20);
    }

}
