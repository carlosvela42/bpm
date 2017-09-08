package co.jp.nej.earth.util;

import java.util.Calendar;

public class UniqueIdUtil {
    private static final long NUMBER_1000 = 1000;
    private static final long NUMBER_999 = 999;
    private static final long NUMBER_100 = 100;
    private static final long NUMBER_100000 = 100000;
    private static final long NUMBER_10000000L = 10000000L;
    private static final long NUMBER_1000000000L = 1000000000L;
    private static final long NUMBER_100000000000L = 100000000000L;
    private static final long NUMBER_10000000000000L = 10000000000000L;
    private static long lastId = getNow();
    public synchronized long createId() {
        while (true) {
            long newId = getNow();

            if (newId/ NUMBER_1000 == lastId/ NUMBER_1000) {
                if (lastId% NUMBER_1000 < (NUMBER_999)) {
                    return ++lastId;
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                }
                continue;
            } else if (newId < lastId) {
                try {
                    Thread.sleep(NUMBER_100);
                } catch (InterruptedException e) {
                }
                continue;
            }
            lastId = newId;
            return lastId;
        }
    }

    private static long getNow() {
        Calendar calendar = Calendar.getInstance();
        long date = calendar.get(Calendar.YEAR)         *   NUMBER_10000000000000L;
        date += (calendar.get(Calendar.MONTH) + 1)      *   NUMBER_100000000000L;
        date += calendar.get(Calendar.DAY_OF_MONTH)     *   NUMBER_1000000000L;
        date += calendar.get(Calendar.HOUR_OF_DAY)      *   NUMBER_10000000L;
        date += calendar.get(Calendar.MINUTE)           *   NUMBER_100000;
        date += calendar.get(Calendar.SECOND)           *   NUMBER_1000;
        date += calendar.get(Calendar.MILLISECOND);
        return date;
    }
}
