package mineward.core.command;

import java.util.Arrays;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConsoleCommand extends BukkitCommand {

    private String[] aliases;
    private String label;
    private String aliasUsed;
    private JavaPlugin plugin;

    public ConsoleCommand(String label, String[] aliases, String description) {
        super(label);
        this.aliases = aliases;
        this.label = label;
        this.description = description;
        this.setAliases(Arrays.asList(aliases));
    }

    public String getLabel() {
        return this.label;
    }

    public String[] getCommandAliases() {
        return this.aliases;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public String getAliasUsed() {
        return this.aliasUsed;
    }

    public void setAliasUsed(String alias) {
        this.aliasUsed = alias;
    }

    public ConsoleCommand setMain(JavaPlugin main) {
        this.plugin = main;
        return this;
    }

    public abstract void execute(ConsoleCommandSender s, String[] args);

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            this.execute((ConsoleCommandSender) sender, args);
        }
        return false;
    }

    public void registerMe() {
        ((CraftServer) plugin.getServer()).getCommandMap().register(
                "HythrylCommand", this);
        CommandCenter.ConsoleCommandMap.add(this);
    }

}
