package mineward.core.common.utils;

import java.text.DecimalFormat;

public class TimeUtil {

	public enum TimeUnit {
		Seconds(1), Minutes(60), Hours(3600), Days(24 * 3600);

		private int s;

		private TimeUnit(int s) {
			this.s = s;
		}

		public int getSeconds() {
			return s;
		}
	}

	public static String toString(long milliseconds) {
		TimeUnit unit = TimeUnit.Seconds;
		if (milliseconds >= (TimeUnit.Minutes.getSeconds() * 1000)) {
			unit = TimeUnit.Minutes;
		}
		if (milliseconds >= (TimeUnit.Hours.getSeconds() * 1000)) {
			unit = TimeUnit.Hours;
		}
		if (milliseconds >= (TimeUnit.Days.getSeconds() * 1000)) {
			unit = TimeUnit.Days;
		}
		double timeInUnit = Long.valueOf(milliseconds).doubleValue() / 1000
				/ unit.getSeconds();
		DecimalFormat format = new DecimalFormat("#.#");
		double d = Double.valueOf(format.format(timeInUnit)).doubleValue();
		if (d == 1.0) {
			return d + " " + unit.name().substring(0, unit.name().length() - 1);
		}
		return d + " " + unit.name();
	}

}
