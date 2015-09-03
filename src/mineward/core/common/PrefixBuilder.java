package mineward.core.common;

import mineward.core.common.Prefix.PrefixColor;

public class PrefixBuilder {

	public static PrefixBuilder pbinstance;

	private boolean _simple;
	private String _main;
	private PrefixColor _color;

	public static PrefixBuilder getPrefixBuilder(boolean simple, String main,
			PrefixColor color) {
		pbinstance = new PrefixBuilder();
		pbinstance._simple = simple;
		pbinstance._main = main;
		pbinstance._color = color;
		return pbinstance;
	}

	public Prefix build() {
		Prefix prefix = new Prefix();
		prefix.simple = pbinstance._simple;
		prefix.main = pbinstance._main;
		prefix.color = pbinstance._color;
		return prefix;
	}

}