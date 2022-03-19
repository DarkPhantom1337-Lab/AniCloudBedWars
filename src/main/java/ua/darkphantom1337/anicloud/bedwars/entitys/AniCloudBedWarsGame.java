package ua.darkphantom1337.anicloud.bedwars.entitys;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.scoreboard.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.configurations.GameConfigurationFile;
import ua.darkphantom1337.anicloud.bedwars.enums.*;
import ua.darkphantom1337.anicloud.bedwars.messages.GameMessageModule;

import java.util.*;
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
    static List<AniCloudBedWarsTeamColor> availableTeamColors;
    static List<Player> allPlayersInGame, survivingPlayersInGame;
    static Boolean isInitializeStartMin = false, isInitializeStartMax= false, isInitializeDeathMatchStart= false, isInitializeEndGame= false;

    static HashMap<AniCloudBedWarsResource, ACBWTeamResourceSpawnLocation> resourceSpawnLocations;
    static HashMap<AniCloudBedWarsTeamColor, Location> teamSpawnLocations;
    static HashMap<AniCloudBedWarsTeamColor, Location> sellerSpawnLocations;
    static HashMap<AniCloudBedWarsTeamColor, Location> upgradeSpawnLocations;
    static HashMap<AniCloudBedWarsTeamColor, ACBWBedSpawnLocation> bedSpawnLocations;

    /**
     * spawn resources delays
     */

    static ACBWShop inGameShop;
    static ACBWUpgradeShop inGameUpgradeShop;

    static Scoreboard wait_start_board;

    static List<String> scoreboard = new ArrayList<>();

    public static Integer getTimeToStartGame() {
        return timeToStartGame;
    }

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
                setGameID(AniCloudBedWarsSessionID.get());
                setArenaName(gameData.getGameSValue("ArenaName"));
                setArenaDescription(gameData.getGameSValue("ArenaDescription"));
                setWorldName(gameData.getGameSValue("WorldName"));
                Bukkit.getWorld(getWorldName())
                        .getEntitiesByClasses(Player.class)
                        .stream()
                        .map(p -> (Player) p)
                        .collect(Collectors.toList())
                        .forEach(p -> p.kickPlayer("�c��������, ��� �������� ����� ���� �� ������ �����. " +
                                "\n��������� ���� �����," +
                                "\n �� ������ ��� ��� ����� ����"));
                setServerName(AniCloudBedWars.inst().getConfigurationsModule().getGlobalConfigurationFile().getString("ServerName"));
                setTeamAmount(gameData.getGameIValue("TeamAmount"));
                setTeamSize(gameData.getGameIValue("TeamSize"));
                setSurvivingPlayersAmount(0);
                setMaxInGameTime(gameData.getGameIValue("MaxInGameTime"));
                timeToEndGame = maxInGameTime;
                setMinPlayers(gameData.getGameIValue("MinPlayers"));
                setTimeToDeathMatch(gameData.getGameIValue("TimeToDeathMatch"));
                setDeathMatchFloorY(gameData.getGameIValue("DeathMatchFloorY"));
                setDeathMatchCeilingY(gameData.getGameIValue("DeathMatchCeilingY"));
                setArenaSize(gameData.getGameIValue("ArenaSize"));
                setAllPlayersAmount(0);
                setTimeToStartGameMin(gameData.getGameIValue("TimeToStartGameMin"));
                setTimeToStartGameMax(gameData.getGameIValue("TimeToStartGameMax"));
                timeToStartGame = timeToStartGameMin;
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
             /*   getArenaWaitSpawnLoc().getWorld().loadChunk(arenaWaitSpawnLoc.getChunk());
                getArenaCenter().getWorld().loadChunk(arenaCenter.getChunk());*/
                // wait_start_board = TabAPI.getInstance().getScoreboardManager().createScoreboard("startboard", "��?� ������", Arrays.asList("�����: ", "���: 4*4", "�� ������: " + timeToStartGame.toString()));
                setGameStatus(AniCloudBedWarsGameStatus.WAIT_PLAYERS);
                updateWaitStartBoardTask();
                AniCloudBedWars.inst().getGameController().setCurrentBedWarsGame(this);
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

    public static List<String> getScoreboard() {
        return scoreboard;
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
    public List<AniCloudBedWarsTeamColor> getAvailableTeamColors() {
        return availableTeamColors;
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
    public HashMap<AniCloudBedWarsTeamColor, Location> getTeamSpawnLoc(AniCloudBedWarsTeamColor team) {
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
        return GameType.BASIC;
    }

    @Override
    public AniCloudBedWarsGameStatus getGameStatus() {
        return gameStatus;
    }

    @Override
    public String getServerName() {
        return "bw2";
    }

    @Override
    public OperationResult joinToGame(Player player) {
        /**
         * handle player join
         */
        getAniCloudBedWars().info("JG1");
        if (player == null) {
            getAniCloudBedWars().info("JG2");
            AniCloudBedWars.inst().error("Player not identified.");
            return OperationResult.UNSUCCESSFUL;
        }
        getAniCloudBedWars().info("JG3");
        if (allPlayersAmount < getMaxPlayers()) {
            getAniCloudBedWars().info("JG4");
            allPlayersAmount++;
            allPlayersInGame.add(player);
            player.teleport(getArenaWaitSpawnLoc());
            getAniCloudBedWars().info("JG4.5");
            String joinMessage = GameMessageModule.getGameJoinMessage(player.getName(), getMaxPlayers() - allPlayersAmount);
            getAllPlayersInGame().forEach(gamePlayer -> gamePlayer.sendMessage(joinMessage));
            getAllPlayersInGame().add(player);
            player.sendMessage(GameMessageModule.getGamePlayerJoinMessage(player.getName(), getMaxPlayers() - allPlayersAmount));
            getAniCloudBedWars().info("JG5");
            // TabAPI.getInstance().getScoreboardManager().showScoreboard(TabAPI.getInstance().getPlayer(player.getUniqueId()), getWaitStartBoard());
            if (giveWaitStartGameItems(player).equals(OperationResult.SUCCESSFULLY)) {
                getAniCloudBedWars().info("JG5.5");
                updatePlayersAmount();
                getAniCloudBedWars().info("JG6");
                return OperationResult.SUCCESSFULLY;
            } else {
                getAniCloudBedWars().info("JG7");
                AniCloudBedWars.inst().error("GameWaitItems not give for player " + player.getName() + ". Error printed...");
                return OperationResult.PERFORMANCE_ERROR;
            }
        }
        getAniCloudBedWars().info("JG8");
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
            p.getInventory().addItem(getAniCloudBedWars().getConfigurationsModule().getItemConfigurationFile().getItem("SelectTeam"));
            p.getInventory().addItem(getAniCloudBedWars().getConfigurationsModule().getItemConfigurationFile().getItem("ExitGame"));
            return OperationResult.SUCCESSFULLY;
        } catch (Exception e) {

            e.printStackTrace();
            return OperationResult.PERFORMANCE_ERROR;
        }
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
            if (survivingPlayersInGame.contains(player)){
                survivingPlayersAmount = allPlayersAmount-1;
                survivingPlayersInGame.remove(player);
            }
            allPlayersAmount = allPlayersAmount - 1;
            allPlayersInGame.remove(player);
            allPlayersInGame.forEach(p -> p.sendMessage("�7����� " + player.getName() + " ������� ����."));
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
            player.kickPlayer("� ��������� ��� �� ��������� ������ ������.\n"
                    + cause);
        return true;
    }

    public void updateStartGameBoard() {
        // Scoreboard scoreboard = TabAPI.getInstance().getScoreboardManager().createScoreboard("WaitStartBoard", "�e������� ������",
        if (getGameStatus().equals(AniCloudBedWarsGameStatus.WAIT_PLAYERS))
            scoreboard = Arrays.asList("", "�a�� ������: " + timeToStartGame.toString(),
                    "�a�������: �7" + allPlayersAmount + "/" + getMaxPlayers(),
                    "�a���������� ���: �7" + (minPlayers - allPlayersAmount),
                    "�a�����: �7" + getArenaName(),
                    "�a������: �7" + getGameStatus(),
                    "�a������: �7" + getServerName(),
                    "�a���: �7" + getGameType().name(),
                    "�aGameID: �7" + getGameID());
        if (getGameStatus().equals(AniCloudBedWarsGameStatus.STARTING_WAIT_PLAYERS))
            scoreboard = Arrays.asList("�e���� ����������...", "�a�� ������: " + timeToStartGame.toString(),
                    "�a�������: �7" + allPlayersAmount + "/" + getMaxPlayers(),
                    "�a�����: �7" + getArenaName(),
                    "�a������: �7" + getGameStatus(),
                    "�a������: �7" + getServerName(),
                    "�a���: �7" + getGameType().name(),
                    "�aGameID: �7" + getGameID());
        if (getGameStatus().equals(AniCloudBedWarsGameStatus.STARTING))
            scoreboard = Arrays.asList("�e���� ����������...", "�a�� ������: " + timeToStartGame.toString(),
                    "�a�������: �7" + allPlayersAmount + "/" + getMaxPlayers(),
                    "�a�����: �7" + getArenaName(),
                    "�a������: �7" + getGameStatus(),
                    "�a������: �7" + getServerName(),
                    "�a���: �7" + getGameType().name(),
                    "�aGameID: �7" + getGameID());
        if (getGameStatus().equals(AniCloudBedWarsGameStatus.IN_THE_GAME))
            scoreboard = Arrays.asList("�e� ����",
                    "�a�������: �7" + allPlayersAmount + "/" + getMaxPlayers(),
                    "�a�����: �7" + getArenaName(),
                    "�a������: �7" + getGameStatus(),
                    "�a������: �7" + getServerName(),
                    "�a���: �7" + getGameType().name(),
                    "�aGameID: �7" + getGameID());
        // );
    }

    public Scoreboard getWaitStartBossBar() {
        Scoreboard scoreboard = TabAPI.getInstance().getScoreboardManager().createScoreboard("WaitStartBoard", "�e������� ������",

                Arrays.asList("", "�a�� ������: " + timeToStartGame.toString(),
                        "�a�������: " + allPlayersAmount,
                        "�a���������� ���: " + (minPlayers - allPlayersAmount),
                        "�����: " + getGameID(),
                        "������: " + getGameStatus())
        );
        return scoreboard;
    }

    public void updateWaitStartBoardTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                updateStartGameBoard();
            }
        }.runTaskTimer(getAniCloudBedWars(), 20, 20);
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
        allPlayersInGame.forEach(p -> p.sendMessage("�a���� ����������..."));

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
                    setGameStatus(AniCloudBedWarsGameStatus.IN_THE_GAME);
                    onGameStart();
                    this.cancel();
                }
                if (timeToStartGame < 30) {
                    String msg = GameMessageModule.getGameStartingMessage(timeToStartGame);
                    allPlayersInGame.forEach(p -> p.sendMessage(msg));
                }
                if (timeToStartGame < 10) {
                    //String title = GameMessageModule.getGameStartingMessage(timeToStartGame);
                    allPlayersInGame.forEach(p -> p.sendTitle("�e���� �������� �����", "�a" + timeToStartGame));
                }
                timeToStartGame--;
            }
        }.runTaskTimer(getAniCloudBedWars(), 20, 20);
    }

}
