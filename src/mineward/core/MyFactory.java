package mineward.core;

import mineward.core.command.ConsoleCommand;
import mineward.core.command.MyCommand;
import mineward.core.common.utils.UtilConsoleLog;
import mineward.core.listener.MyListener;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MyFactory {

    public static void RegisterCommand(MyCommand cmd, JavaPlugin plugin) {
        cmd.setMain(plugin).registerMe();
        UtilConsoleLog.Log("Command",
                "Command " + ChatColor.GREEN + cmd.getLabel() + ChatColor.WHITE
                        + " has been registered.");
    }

    public static void RegisterCommand(ConsoleCommand cmd, JavaPlugin plugin) {
        cmd.setMain(plugin).registerMe();
        UtilConsoleLog.Log("Command", "Console Command " + ChatColor.GREEN
                + cmd.getLabel() + ChatColor.WHITE + " has been registered.");
    }

    public static void RegisterListener(MyListener listener, JavaPlugin plugin) {
        listener.setMain(plugin).registerMe();
        UtilConsoleLog.Log("Listener",
                "Listener " + ChatColor.GREEN + listener.getName()
                        + ChatColor.WHITE + " has been registered.");
    }

}
