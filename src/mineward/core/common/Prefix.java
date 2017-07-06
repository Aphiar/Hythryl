package mineward.core.common;

import org.bukkit.ChatColor;

import mineward.core.common.utils.C;

public class Prefix {

    public boolean simple = true;
    public String main = "";
    public PrefixColor color = PrefixColor.Easy;

    public String toString() {
        if (simple) {
            return color.getColor() + "" + ChatColor.BOLD + ">> ";
        }
        return ChatColor.GRAY + "[" + color.getColor() + main + ChatColor.GRAY + "] ";
    }

    public enum PrefixColor {
        Important(C.PREFIX_IMPORTANT), Normal(C.PREFIX_NORMAL), Easy(
                C.PREFIX_EASY);
        private String color;

        private PrefixColor(String str) {
            this.color = str;
        }

        public String getColor() {
            return this.color;
        }
    }

}
