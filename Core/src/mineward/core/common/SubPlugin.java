package mineward.core.common;

import java.sql.Connection;

import mineward.core.Core;
import mineward.core.MyFactory;
import mineward.core.command.MyCommand;
import mineward.core.common.utils.UtilConsoleLog;
import mineward.core.listener.MyListener;

import org.bukkit.plugin.java.JavaPlugin;

public abstract class SubPlugin {

	private String _name;
	private Version _ver;
	private JavaPlugin _main;

	public Connection getConnection() {
		return Database.getConnection();
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		this._name = name;
	}

	public Version getVersion() {
		return _ver;
	}

	public void setVersion(Version ver) {
		this._ver = ver;
	}

	/**
	 * Use this method only when your main plugin is defined.
	 * 
	 * @param cmd
	 *            An instance of the Command object your class has inherited.
	 *            Ex. PokemonCMD (if PokemonCMD explicitly inherits MyCommand)
	 * 
	 */
	public void addCommand(MyCommand cmd) {
		MyFactory.RegisterCommand(cmd, getMainPlugin());
	}

	public void addListener(MyListener listener) {
		MyFactory.RegisterListener(listener, getMainPlugin());
	}

	public JavaPlugin getMainPlugin() {
		return _main;
	}

	public void enableMe(JavaPlugin main, String name) {
		Core.registerSubPlugin(this);
		this._main = main;
		this._name = name;
		UtilConsoleLog.Log("Module", "Module " + name + " has been enabled!");
	}

}
