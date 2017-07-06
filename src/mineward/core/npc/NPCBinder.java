package mineward.core.npc;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import mineward.core.Core;
import mineward.core.listener.MyListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class NPCBinder extends MyListener {

    public static HashMap<UUID, String> ToBind = new HashMap<>();

    public File getWorldFile(String world) {
        return new File(new File(Core.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath()).getParentFile()
                .getParentFile().getPath()
                + "/" + world);
    }

    public File getNPCData(String world, int entityId, boolean createIfNotExists) {
        File f = new File(getWorldFile(world).getPath() + "/" + "NPCData" + "/"
                + entityId + ".yml");
        // Bukkit.broadcastMessage(f.getPath());
        if (createIfNotExists) {
            if (!f.exists()) {
                try {
                    if (!f.getParentFile().exists()) {
                        f.getParentFile().mkdir();
                    }
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return f;
    }

    public NPCBinder() {
        super("NPC Binder");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (ToBind.containsKey(p.getUniqueId()))
            ToBind.remove(p.getUniqueId());
    }

    @EventHandler
    public void onEntityInteractEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        Entity ent = e.getRightClicked();
        if (ToBind.containsKey(p.getUniqueId())) {
            String cmd = ToBind.get(p.getUniqueId());
            File f = getNPCData(p.getWorld().getName(), ent.getEntityId(), true);
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            cfg.set("Command", cmd.replace("(OPEN_PLAYER)", ""));
            cfg.set("RunAsPlayer", cmd.contains("(OPEN_PLAYER)"));
            try {
                cfg.save(f);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            p.sendMessage(ChatColor.GREEN + "Successfully bound command "
                    + ChatColor.RED + "/" + cmd + ChatColor.GREEN
                    + " to entity.");
            ToBind.remove(p.getUniqueId());
            e.setCancelled(true);
            return;
        }
        File f = getNPCData(p.getWorld().getName(), ent.getEntityId(), false);
        if (!f.exists())
            return;
        CommandSender sender = Bukkit.getConsoleSender();
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        String cmd = cfg.getString("Command");
        if (cfg.getBoolean("RunAsPlayer"))
            sender = p;
        String command = cmd.replace("{p}", p.getName());
        Bukkit.getServer().dispatchCommand(sender, command);
        e.setCancelled(true);
    }

}
