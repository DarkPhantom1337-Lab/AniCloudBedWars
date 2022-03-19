package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

/**
 * @Author DarkPhantom1337
 * @Version 1.0.0
 * @Desctiption API for working with YAML files
 */
public class DarkYAMLFileAPI {

    private FileConfiguration fileConfiguration;
    private Plugin plugin;
    private File file;
    private String filename;
    private String pluginname;
    private String author;
    private File dataFolder;
    private String ID;
    private static char[] symbolsForID = ("ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase() + "0123456789").toCharArray();
    private static Random random = new SecureRandom();
    private LinkedList<String> allKeys = new LinkedList<String>();

    private static List<String> allFilesID = new ArrayList<String>();
    private static HashMap<String, Class<? extends DarkYAMLFileAPI>> allFiles = new HashMap<String, Class<? extends DarkYAMLFileAPI>>();

    /**
     * @param plugin   class extends JavaPlugin.
     * @param fileName file name without its extension.
     * @param path     the path file to the file without its name.
     */

    public DarkYAMLFileAPI(Plugin plugin, String fileName, File path) {
        this(plugin, fileName, path.getPath());
    }

    /**
     * @param plugin   class extends JavaPlugin.
     * @param fileName file name without its extension.
     * @param path     the path string to the file without its name.
     */
    public DarkYAMLFileAPI(Plugin plugin, String fileName, String path) {
        this.plugin = plugin;
        this.filename = fileName + ".yml";
        this.pluginname = plugin.getName();
        this.author = plugin.getDescription().getAuthors().size() > 0
                ? plugin.getDescription().getAuthors().get(0)
                : "DarkPhantom1337";
        this.dataFolder = new File(path);
        this.ID = generateID();
        while (allFilesID.contains(this.ID))
            this.ID = generateID();
        allFilesID.add(this.ID);
        setup();
    }

    /**
     * PRIVATE METHODS
     * by DarkPhantom1337, 2021
     */

    private void setup() {
        if (!dataFolder.exists())
            dataFolder.mkdirs();
        file = new File(dataFolder, filename);
        if (!file.exists()) {
            try {
                file.createNewFile();
                fileConfiguration = YamlConfiguration.loadConfiguration(file);
                System.out.println("[DarkYAMLFileAPI] -> File: " + filename + " ID: " + getFileID() + " created and loaded.");
                firstFill();
                return;
            } catch (IOException localIOException) {
                System.out.println("Error in creating file " + filename + "!");
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    private String replaceColors(String stToReplace) {
        return stToReplace.replace("&", "§");
    }

    private String generateID() {
        String id = "";
        for (int i = 0; i <= 8; i++)
            id += symbolsForID[random.nextInt(symbolsForID.length)];
        return id;
    }

    public static String getRandomID(int length){
        String id = "";
        for (int i = 0; i <= length; i++)
            id += symbolsForID[random.nextInt(symbolsForID.length)];
        return id;
    }

    /**
     * PUBLIC BASIC METHODS
     * by DarkPhantom1337, 2021
     */

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public final void saveFileConfiguration() {
        try {
            fileConfiguration.save(file);
        } catch (IOException localIOException) {
            System.out.println("Error in saving file " + filename + "!");
        }
/*
        try {
            FileWriter fw = new FileWriter(file);
            for (String key : allKeys) {
                if (key.equals("%DBLINE")) {
                    fw.write("\n");
                    continue;
                }
                if (key.startsWith("#")) {
                    fw.write(key + "\n");
                    continue;
                } else {
                    if (getFileConfiguration().isString(key)) {
                        fw.write(key + ": '" + new String(getString(key).getBytes("windows-1251"), "windows-1251") + "'\n");
                        continue;
                    }
                    if (getFileConfiguration().isInt(key)) {
                        fw.write(key + ": " + getInt(key) + "\n");
                        continue;
                    }
                    if (getFileConfiguration().isBoolean(key)) {
                        fw.write(key + ": " + getBoolean(key) + "\n");
                        continue;
                    }
                    if (getFileConfiguration().isLong(key)) {
                        fw.write(key + ": " + getLong(key) + "\n");
                        continue;
                    }
                    if (getFileConfiguration().isDouble(key)) {
                        fw.write(key + ": " + getDouble(key) + "\n");
                        continue;
                    }
                    if (getFileConfiguration().isList(key)) {
                        fw.write(key + ": \n");
                        for (Object line : getList(key)) {
                            if (line instanceof String) {
                                fw.write("- '" + ((String) line).replaceAll("'", "''") + "'" + "\n");
                                continue;
                            }
                            if (line instanceof Integer) {
                                fw.write("- '" + ((Integer) line).toString() + "'" + "\n");
                                continue;
                            }
                            if (line instanceof Double) {
                                fw.write("- '" + ((Double) line).toString() + "'" + "\n");
                                continue;
                            }
                            if (line instanceof Long)
                                fw.write("- '" + ((Long) line).toString() + "'" + "\n");
                        }
                    }
                }
            }
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public final void reloadFileConfiguration() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
        saveFileConfiguration();
        onFileReload(file, fileConfiguration);
    }

    public final String getFileID() {
        return this.ID;
    }

    public final String getFileName() {
        return this.filename;
    }

    public void firstFill() {
        getFileConfiguration().set(pluginname, "Filename: " + filename + " || Author: " + author);
        saveFileConfiguration();
        System.out.println("[DarkYAMLFileAPI] -> File " + filename + " successfully first filled!");
    }

    public final void writeDefaultFirstLine() {
        getFileConfiguration().set(pluginname, "Filename: " + filename + "  || Author: " + author);
        saveFileConfiguration();
        allKeys.add(pluginname);
    }

    public final void setObject(String path, Object object) {
        getFileConfiguration().set(path, object);
        saveFileConfiguration();
        allKeys.add(path);
    }

    public final void setList(String path, List<?> value) {
        setObject(path, value);
    }

    public final Object getObject(String path) {
        return getFileConfiguration().get(path);
    }

    public final List<?> getList(String path) {
        return getFileConfiguration().getList(path);
    }

    @Override
    public String toString() {
        return "DarkYAMLFile/Name:" + filename + "/ID:" + this.ID;
    }

    @Override
    public boolean equals(Object obj) {
        if ((null == obj) || (obj.getClass() != DarkYAMLFileAPI.class))
            return false;
        DarkYAMLFileAPI file = (DarkYAMLFileAPI) obj;
        return (file.getFileID().equals(this.getFileID()));
    }

    public final void addComment(String comment) {
        allKeys.add("# " + comment);
    }

    public final void addBlancLine() {
        allKeys.add("%DBLINE");
    }

    public final Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * PUBLIC Actions METHODS
     * by DarkPhantom1337, 2021
     */

    public void onFileReload(File file, FileConfiguration fileConfiguration) {
        System.out.println("[DarkYAMLFileAPI] -> File: " + filename + " ID: " + getFileID() + " reloaded.");
    }

    /**
     * PUBLIC GET <?> METHODS
     * by DarkPhantom1337, 2021
     */

    public final String getString(String path) {
        if (getFileConfiguration().isSet(path))
            return replaceColors(getFileConfiguration().getString(path));
        else {
            System.out.println("[DarkYAMLFileAPI] -> ERROR! PATH '" + path + "' NOT FOUND IN FILE{ " + toString() + " }." +
                    "+\nMethod: getString(String path);");
            return "DarkYAMLFileAPI/PathNotFound/" + path;
        }
    }

    public final Location getLocation(String path) {
        if (getFileConfiguration().isSet(path))
            return getFileConfiguration().getLocation(path);
        else {
            System.out.println("[DarkYAMLFileAPI] -> ERROR! PATH '" + path + "' NOT FOUND IN FILE{ " + toString() + " }." +
                    "+\nMethod: getLocation(String path);");
            return null;
        }
    }

    public final Integer getInt(String path) {
        if (getFileConfiguration().isSet(path))
            return getFileConfiguration().getInt(path);
        else {
            System.out.println("[DarkYAMLFileAPI] -> ERROR! PATH '" + path + "' NOT FOUND IN FILE{ " + toString() + " }." +
                    "+\nMethod: getInt(String path);");
            return 1337;
        }
    }

    public final Double getDouble(String path) {
        if (getFileConfiguration().isSet(path))
            return getFileConfiguration().getDouble(path);
        else {
            System.out.println("[DarkYAMLFileAPI] -> ERROR! PATH '" + path + "' NOT FOUND IN FILE{ " + toString() + " }." +
                    "+\nMethod: getDouble(String path);");
            return 1337.0;
        }
    }

    public final Boolean getBoolean(String path) {
        if (getFileConfiguration().isSet(path))
            return getFileConfiguration().getBoolean(path);
        else {
            System.out.println("[DarkYAMLFileAPI] -> ERROR! PATH '" + path + "' NOT FOUND IN FILE{ " + toString() + " }." +
                    "+\nMethod: getBoolean(String path);");
            return false;
        }
    }

    public final Long getLong(String path) {
        if (getFileConfiguration().isSet(path))
            return getFileConfiguration().getLong(path);
        else {
            System.out.println("[DarkYAMLFileAPI] -> ERROR! PATH '" + path + "' NOT FOUND IN FILE{" + toString() + "}." +
                    "+\nMethod: getLong(String path);");
            return 1337L;
        }
    }

    public final List<String> getStringList(String path) {
        return getFileConfiguration().getStringList(path);
    }

    public final List<Integer> getIntList(String path) {
        return getFileConfiguration().getIntegerList(path);
    }

    public final List<Double> getDoubleList(String path) {
        return getFileConfiguration().getDoubleList(path);
    }

    public final List<Long> getLongList(String path) {
        return getFileConfiguration().getLongList(path);
    }

    public final ItemStack getItemStack(String path) {
        return getFileConfiguration().getItemStack(path);
    }

    /**
     * PUBLIC SET <?> METHODS
     * by DarkPhantom1337, 2021
     */

    public final void setString(String path, String value) {
        setObject(path, value);
    }

    public final void setItem(String path, ItemStack value) {
        setObject(path, value);
    }


    public final void setInt(String path, Integer value) {
        setObject(path, value);
    }

    public final void setDouble(String path, Double value) {
        setObject(path, value);
    }

    public final void setLong(String path, Long value) {
        setObject(path, value);
    }

    public final void setLocation(String path, Location value) {
        setObject(path, value);
    }

    public final void setBoolean(String path, Boolean value) {
        setObject(path, value);
    }

    public final void setStringList(String path, List<String> value) {
        setList(path, value);
    }

    public final void setIntList(String path, List<Integer> value) {
        setList(path, value);
    }

    public final void setDoubleList(String path, List<Double> value) {
        setList(path, value);
    }

    public final void setLongList(String path, List<Long> value) {
        setList(path, value);
    }

    public final void setFloatList(String path, List<Float> value) {
        setList(path, value);
    }

    public final void deleteFile(){
        file.delete();
    }

}
