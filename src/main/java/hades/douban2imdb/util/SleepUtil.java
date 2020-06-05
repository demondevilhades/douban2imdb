package hades.douban2imdb.util;

import org.apache.commons.lang3.RandomUtils;

public class SleepUtil {

    private static boolean SLEEP_FLAG = false;

    public static void init(boolean sleepFlag) {
        SLEEP_FLAG = sleepFlag;
    }

    public static void sleep60() {
        if (SLEEP_FLAG) {
            try {
                Thread.sleep(RandomUtils.nextInt(30, 90) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void sleep20() {
        if (SLEEP_FLAG) {
            try {
                Thread.sleep(RandomUtils.nextInt(10, 30) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void sleep10() {
        if (SLEEP_FLAG) {
            try {
                Thread.sleep(RandomUtils.nextInt(5, 15) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public static void sleep5() {
        if (SLEEP_FLAG) {
            try {
                Thread.sleep(RandomUtils.nextInt(0, 10) * 1000);
            } catch (InterruptedException e) {
            }
        }
    }
}
