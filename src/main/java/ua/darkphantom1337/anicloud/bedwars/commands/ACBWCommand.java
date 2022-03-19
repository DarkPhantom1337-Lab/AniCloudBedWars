package ua.darkphantom1337.anicloud.bedwars.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ua.darkphantom1337.anicloud.bedwars.AniCloudBedWars;
import ua.darkphantom1337.anicloud.bedwars.configurations.GameConfigurationFile;

import java.util.Arrays;

public class ACBWCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equals("createNewGame")) {
                    if (args.length == 11) {
                        /**
                         * QUICK CREATING GAME
                         */
                        String gameID = args[1], arenaName = args[2], arenaDescription = args[3];
                        String arenaSize = args[4], shopID = args[5], upgradeShopID = args[6];
                        String builderNames = args[7], teamAmount = args[8], teamSize = args[9];
                        String minPlayers = args[10];
                        if (AniCloudBedWars.inst().getGamesID().contains(gameID)) {
                            player.sendMessage("§a-> Поле gameID уже сущевствует на данном сервере. Игра: " + gameID);
                            return true;
                        }
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        gameConfigurationFile.setString("ArenaName", arenaName);
                        gameConfigurationFile.setString("ArenaDescription", arenaDescription);
                        gameConfigurationFile.setInt("ArenaSize", Integer.parseInt(arenaSize));
                        gameConfigurationFile.setString("ShopID", shopID);
                        gameConfigurationFile.setString("UpgradeShopID", upgradeShopID);
                        gameConfigurationFile.setStringList("BuildersNames", Arrays.asList(builderNames.split(";").clone()));
                        gameConfigurationFile.setInt("TeamAmount", Integer.parseInt(teamAmount));
                        gameConfigurationFile.setInt("TeamSize", Integer.parseInt(teamSize));
                        gameConfigurationFile.setInt("MinPlayers", Integer.parseInt(minPlayers));
                        player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точку спавна в лобби..."
                                + "\n- /acbw setGameLobbyLoc " + gameID);
                    } else {
                        player.sendMessage("§a-> /acbw createNewGame gameID arenaName arenaDescription " +
                                "arenaSize shopID upgradeShopID builderNamesSplit; teamAmount teamSize minPlayers");
                    }
                    return true;
                }
                if (args[0].equals("setGameLobbyLoc")) {
                    if (args.length == 2) {
                        String gameID = args[1];
                        AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID)
                                .setLocation("ArenaWaitSpawnLoc", player.getLocation());
                        player.sendMessage("§aДанные о игре " + gameID + " сохранены, укажите цвета команд..."
                                + "\n- /acbw setTeamColors " + gameID + " BLACK;white;red;BluE");
                    } else {
                        player.sendMessage("§a-> /acbw setGameLobby gameID");
                    }
                    return true;
                }
                if (args[0].equals("setTeamColors")) {
                    if (args.length == 3) {
                        String gameID = args[1], teamColors = args[2].toUpperCase();
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        if (gameConfigurationFile.getInt("TeamAmount") == teamColors.split(";").length) {
                            gameConfigurationFile.setStringList("AvailableTeamColors", Arrays.asList(teamColors.split(";").clone()));
                            player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точки спавна команд..."
                                    + "\n- /acbw setTeamSpawnLoc " + gameID + " TeamColor");
                        } else {
                            player.sendMessage("§aКоличество команд в игре " + gameID + " не = количеству указанных цветов.");
                        }
                        return true;
                    } else {
                        player.sendMessage("§a-> /acbw setGameLobby gameID");
                    }
                    return true;
                }
                if (args[0].equals("setGameResources")) {
                    if (args.length == 3) {
                        String gameID = args[1], gameResources = args[2].toUpperCase();
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        gameConfigurationFile.setStringList("AvailableTeamColors", Arrays.asList(gameResources.split(";").clone()));
                        player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точки спавна ресурсов..."
                                + "\n- /acbw setTeamResourceSpawnLoc " + gameID + " TeamName ResourceName");
                        return true;
                    }
                 else {
                    player.sendMessage("§a-> /acbw setGameLobby gameID");
                }
            }

                if (args[0].equals("setTeamSpawnLoc")) {
                    if (args.length == 3) {
                        String gameID = args[1], teamColor = args[2].toUpperCase();
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        if (gameConfigurationFile.getStringList("AvailableTeamColors").contains(teamColor)) {
                            gameConfigurationFile.setLocation("Team." + teamColor.toUpperCase() + ".SpawnLoc", player.getLocation());
                            player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точки спавна остальных команд если таковы есть..."
                                    + "\n- /acbw setTeamSpawnLoc " + gameID + " TeamColor" +
                                    "\nЕсли вы установили точки спавна всем используйте: " +
                                    "\n - /acbw setTeamResourceSpawnLoc " + gameID + " TeamName ResourceName");
                        } else {
                            player.sendMessage("§aТакого цвета команды не существует в данной игре.");
                        }
                        return true;
                    } else {
                        player.sendMessage("§a-> /acbw setTeamSpawnLoc gameID TeamColor");
                    }
                    return true;
                }
                if (args[0].equals("setTeamResourceSpawnLoc")) {
                    if (args.length == 4) {
                        String gameID = args[1], teamColor = args[2].toUpperCase(), resourceName = args[3].toUpperCase();
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        if (gameConfigurationFile.getStringList("AvailableTeamColors").contains(teamColor)) {
                            if (gameConfigurationFile.getStringList("AvailableResources").contains(resourceName)) {
                                gameConfigurationFile.setLocation("Team." + teamColor.toUpperCase() + ".Resource." + resourceName + ".SpawnLoc", player.getLocation());
                                player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точки спавна остальных команд если таковы есть..."
                                        + "\n- /acbw setTeamSpawnLoc " + gameID + " TeamColor" +
                                        "\nЕсли вы установили точки спавна ресурсов всем используйте: " +
                                        "\n - /acbw setTeamBedSpawnLoc " + gameID + " TeamName");
                            } else {
                                player.sendMessage("§aТакого ресурса не существует в данной игре.");
                            }
                        } else {
                            player.sendMessage("§aТакого цвета команды не существует в данной игре.");
                        }
                        return true;
                    } else {
                        player.sendMessage("§a-> /acbw setGameLobby gameID");
                    }
                    return true;
                }
                if (args[0].equals("setTeamBedSpawnLoc")) {
                    if (args.length == 3) {
                        String gameID = args[1], teamColor = args[2].toUpperCase();
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        if (gameConfigurationFile.getStringList("AvailableTeamColors").contains(teamColor)) {
                            gameConfigurationFile.setLocation("Team." + teamColor.toUpperCase() + ".BedLoc", player.getLocation());
                            player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точки кровати остальных команд если таковы есть..."
                                    + "\n- /acbw setTeamBedSpawnLoc " + gameID + " TeamColor" +
                                    "\nЕсли вы установили точки спавна всем используйте: " +
                                    "\n - /acbw setTeamShopLoc " + gameID + " TeamName");
                        } else {
                            player.sendMessage("§aТакого цвета команды не существует в данной игре.");
                        }
                        return true;
                    } else {
                        player.sendMessage("§a-> /acbw setTeamBedSpawnLoc gameID TeamColor");
                    }
                    return true;
                }
                if (args[0].equals("setTeamShopLoc")) {
                    if (args.length == 3) {
                        String gameID = args[1], teamColor = args[2].toUpperCase();
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        if (gameConfigurationFile.getStringList("AvailableTeamColors").contains(teamColor)) {
                            gameConfigurationFile.setLocation("Team." + teamColor.toUpperCase() + ".ShopLoc", player.getLocation());
                            player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точки магазина остальных команд если таковы есть..."
                                    + "\n- /acbw setTeamShopLoc " + gameID + " TeamColor" +
                                    "\nЕсли вы установили точки спавна всем используйте: " +
                                    "\n - /acbw setTeamUpgradeShopLoc " + gameID + " TeamColor");
                        } else {
                            player.sendMessage("§aТакого цвета команды не существует в данной игре.");
                        }
                        return true;
                    } else {
                        player.sendMessage("§a-> /acbw setTeamShopLoc gameID TeamColor");
                    }
                    return true;
                }
                if (args[0].equals("setTeamUpgradeShopLoc")) {
                    if (args.length == 3) {
                        String gameID = args[1], teamColor = args[2].toUpperCase();
                        GameConfigurationFile gameConfigurationFile = AniCloudBedWars.inst().getGameController().getGameConfigurationFile(gameID);
                        if (gameConfigurationFile.getStringList("AvailableTeamColors").contains(teamColor)) {
                            gameConfigurationFile.setLocation("Team." + teamColor.toUpperCase() + ".UpgradeShopLoc", player.getLocation());
                            player.sendMessage("§aДанные о игре " + gameID + " сохранены, установите точки магазина прокачки остальных команд если таковы есть..."
                                    + "\n- /acbw setTeamUpgradeShopLoc " + gameID + " TeamColor" +
                                    "\nЕсли вы установили точки спавна всем используйте: " +
                                    "\n - /acbw validate " + gameID);
                        } else {
                            player.sendMessage("§aТакого цвета команды не существует в данной игре.");
                        }
                        return true;
                    } else {
                        player.sendMessage("§a-> /acbw setTeamUpgradeShopLoc gameID TeamColor");
                    }
                    return true;
                }
                if (args.equals("validate")) {
                    if (args.length == 2) {
                        String gameID = args[1];
                        AniCloudBedWars.getInstance().getConfigurationsModule().getGlobalConfigurationFile().addGameID(gameID);
                        player.sendMessage("§aДанные о игре " + gameID + " сохранены, game on"
                                + "\n- Please restart server");
                    }
                }
            }
        } else {

        }
        return false;
    }

}
