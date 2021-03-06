package net.danh.bsoul.Manager;

import net.danh.bsoul.CustomEvents.SoulChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Data {

    static HashMap<String, Integer> soul = new HashMap<>();

    public static void savePlayerData(Player p) {
        Resources.getdatafile().set("PLAYERS." + p.getName() + ".SOUL", getSoul(p));
        Resources.getdatafile().set("PLAYERS." + p.getName() + ".MAX_SOUL", getSoulMax(p));
        Resources.savedata();
    }

    public static int getSoulData(Player p) {
        return Resources.getdatafile().getInt("PLAYERS." + p.getName() + ".SOUL");
    }

    public static int getSoul(Player p) {
        return soul.get(p.getName() + "_SOUL");
    }

    public static void setSoul(Player p, Integer amount) {
        soul.put(p.getName() + "_SOUL", Math.max(Resources.getconfigfile().getInt("SETTINGS.DEFAULT_SOUL"), amount));
        SoulChangeEvent soulChangeEvent = new SoulChangeEvent(p, Math.max(Resources.getconfigfile().getInt("SETTINGS.DEFAULT_SOUL"), amount));
        Bukkit.getServer().getPluginManager().callEvent(soulChangeEvent);
    }

    public static void addSoul(Player p, Integer amount) {
        int amounts = getSoul(p) + amount;
        if (amounts <= getSoulMax(p)) {
            soul.replace(p.getName() + "_SOUL", amounts);
            SoulChangeEvent soulChangeEvent = new SoulChangeEvent(p, amounts);
            Bukkit.getServer().getPluginManager().callEvent(soulChangeEvent);
        }
    }

    public static void removeSoul(Player p, Integer amount) {
        int count = getSoul(p) - amount;
        if (count >= 0) {
            soul.replace(p.getName() + "_SOUL", count);
            SoulChangeEvent soulChangeEvent = new SoulChangeEvent(p, count);
            Bukkit.getServer().getPluginManager().callEvent(soulChangeEvent);
        }
    }

    public static int getMaxSoulData(Player p) {
        return Resources.getdatafile().getInt("PLAYERS." + p.getName() + ".MAX_SOUL");
    }

    public static int getSoulMax(Player p) {
        return soul.get(p.getName() + "_SOUL_MAX");
    }

    public static void setSoulMax(Player p, Integer amount) {
        soul.put(p.getName() + "_SOUL_MAX", Math.max(Resources.getconfigfile().getInt("SETTINGS.DEFAULT_SOUL_MAX"), amount));
    }

    public static void addSoulMax(Player p, Integer amount) {
        soul.replace(p.getName() + "_SOUL_MAX", getSoulMax(p) + amount);
    }

    public static void removeSoulMax(Player p, Integer amount) {
        if (getSoulMax(p) - amount >= 0) {
            soul.replace(p.getName() + "_SOUL_MAX", getSoulMax(p) - amount);
        }
    }
}
