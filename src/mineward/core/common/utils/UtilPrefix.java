package mineward.core.common.utils;

import mineward.core.common.Prefix;
import mineward.core.common.Prefix.PrefixColor;

public class UtilPrefix {

	public static Prefix buildPrefix(PrefixColor c) {
		Prefix pr = new Prefix();
		pr.color = c;
		pr.simple = true;
		return pr;
	}

	public static Prefix buildPrefix(String str, PrefixColor c) {
		Prefix pr = new Prefix();
		pr.color = c;
		pr.main = str;
		pr.simple = false;
		return pr;
	}

}
