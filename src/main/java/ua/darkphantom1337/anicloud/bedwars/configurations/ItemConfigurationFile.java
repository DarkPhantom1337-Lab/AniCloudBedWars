package ua.darkphantom1337.anicloud.bedwars.configurations;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ItemConfigurationFile extends DarkYAMLFileAPI {

    public ItemConfigurationFile(Plugin plugin) {
        super(plugin, "itemConfiguration", plugin.getDataFolder());
    }

    @Override
    public void firstFill() {
        getFileConfiguration().set(getPlugin().getName(), "Filename: " + getFileName() + " || Author: DarkPhantom1337");
        setItem("Item.SelectTeam", getItem("§eВыбор команды", new ArrayList<String>(), Material.CHEST, 1337, 1));
        setItem("Item.ExitGame", getItem("§eВыход", new ArrayList<String>(), Material.RED_BED, 1338, 1));
        saveFileConfiguration();
    }

    public ItemStack getItem(String id) {
        return getItemStack("Item." + id);
    }


    public ItemStack getItem(String name, List<String> lore, Material material, Integer cmd, Integer amount) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.setCustomModelData(cmd);
        item.setAmount(amount);
        item.setItemMeta(meta);
        return item;
    }

}
