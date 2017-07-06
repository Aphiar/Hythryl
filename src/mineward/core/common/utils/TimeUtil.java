package mineward.core.common.utils;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public static String formateDateDiff(long date) {
        Calendar c = new GregorianCalendar();
        c.setTimeInMillis(date);
        Calendar now = new GregorianCalendar();
        return TimeUtil.formateDateDiff(now, c);
    }

    static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future) {
        int diff = 0;
        long savedDate = fromDate.getTimeInMillis();
        while ((future && !fromDate.after(toDate)) || (!future && !fromDate.before(toDate))) {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future ? 1 : -1);
            diff++;
        }
        diff--;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }

    public static String formateDateDiff(Calendar fromDate, Calendar toDate) {
        boolean future = false;
        if (toDate.equals(fromDate)) {
            return "now";
        }
        if (toDate.after(fromDate)) {
            future = true;
        }
        StringBuilder sb = new StringBuilder();
        int[] types = new int[]{
                Calendar.YEAR, Calendar.MONDAY, Calendar.DAY_OF_MONTH, Calendar.HOUR_OF_DAY, Calendar.MINUTE, Calendar.SECOND
        };
        String[] names = new String[]{
                "year", "years", "month", "months", "day", "days", "hour", "hours", "minute", "minutes", "second", "seconds"
        };
        int accuracy = 0;
        for (int i = 0; i < types.length; i++) {
            if (accuracy > 2) {
                break;
            }
            int diff = dateDiff(types[i], fromDate, toDate, future);
            if (diff > 0) {
                accuracy++;
                sb.append(" ").append(diff).append(" ").append(names[i * 2 + (diff > 1 ? 1 : 0)]);
            }
        }
        if (sb.length() == 0) {
            return "now";
        }
        return sb.toString().trim();
    }
}