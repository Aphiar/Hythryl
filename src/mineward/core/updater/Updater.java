package mineward.core.updater;

import mineward.core.Core;
import mineward.core.common.utils.UtilFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * Created by alexc on 28/07/2016.
 */
public class Updater {

    private String fileName;
    public static boolean restartNeeded = false;

    public Updater(String fileName) {
        this.fileName = fileName;
    }

    private void log(String log) {
        System.out.println(log);
    }

    public void CheckForUpdates() {
        File currentPluginFolder =
            new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath()).getParentFile();
        //log("Current Plugin Folder: " + currentPluginFolder.getPath());
        File currentPluginFile = new File(currentPluginFolder, fileName);
        File newPluginFolder = new File("/root/Hythryl/Update");
        //log("New Plugin Folder: " + newPluginFolder);

        File newPluginFile = new File(newPluginFolder, fileName);

        if (UtilFile.getFileSizeInMB(currentPluginFile) != UtilFile.getFileSizeInMB(newPluginFile)) {
            Bukkit.broadcastMessage("§c§lUpdater: §fAn update was found, updating...");
            try {
                FileUtils.forceDelete(currentPluginFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileUtil.copy(newPluginFile, currentPluginFile);
            restartNeeded = true;

        }
    }

}
