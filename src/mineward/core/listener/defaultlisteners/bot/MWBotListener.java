package mineward.core.listener.defaultlisteners.bot;

import mineward.core.listener.MyListener;
import mineward.core.listener.custom.MinewardPlayerChatEvent;

import org.bukkit.event.EventHandler;

public class MWBotListener extends MyListener {

    public MWBotListener() {
        super("MinewardBot");
    }

    @EventHandler
    public void onChat(MinewardPlayerChatEvent e) {

    }

}
