package mineward.core.common.utils;

import mineward.core.common.Rank;

public class UtilRank {

    public static Rank getRank(String label, boolean ignoreCase) {
        for (Rank rank : Rank.values()) {
            if (ignoreCase) {
                if (rank.getName().equalsIgnoreCase(label)) {
                    return rank;
                }
            } else {
                if (rank.getName().equals(label)) {
                    return rank;
                }
            }
        }
        return null;
    }

}
