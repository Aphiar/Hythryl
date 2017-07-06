package mineward.core.chat;

import mineward.core.command.MyCommand;
import mineward.core.common.Rank;
import mineward.core.common.utils.C;
import mineward.core.common.utils.F;
import mineward.core.listener.defaultlisteners.ChatListener;
import mineward.core.player.HPlayer;

import org.bukkit.entity.Player;

public class StaffChatCMD extends MyCommand {

    public StaffChatCMD() {
        super("sc", new String[]{}, "Toggle staff chat", Rank.Jrmod);
    }

    @Override
    public void execute(Player p, String[] args) {
        if (ChatListener.staffChat.contains(p.getUniqueId())) {
            ChatListener.staffChat.remove(p.getUniqueId());
            String second = HPlayer.o(p).getRank()
                    .isPermissible(Rank.Jrmod) ? ",[" + C.STR_ELEMENT
                    + "STAFF" + C.STR_MAIN + "]" : "";
            F.message(p, "Chat Channels", "You have been moved to channel ["
                    + C.STR_ELEMENT + "GLOBAL" + C.STR_MAIN
                    + "] with visibility [" + C.STR_ELEMENT + "GLOBAL"
                    + C.STR_MAIN + "]" + second + ".");
            return;
        }
        ChatListener.staffChat.add(p.getUniqueId());
        F.message(p, "Chat Channels", "You have been moved to channel ["
                + C.STR_ELEMENT + "STAFF" + C.STR_MAIN + "] with visibility ["
                + C.STR_ELEMENT + "GLOBAL" + C.STR_MAIN + "],[" + C.STR_ELEMENT
                + "STAFF" + C.STR_MAIN + "].");
        return;
    }

}
