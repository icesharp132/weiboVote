package cn.hui.vote.web.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

/**
 * @author: Lu Zhaohui
 * @description:
 * @date: Created on 2018/6/30
 * @modified By:
 */
public class TpsUtil {

    /**
     * 每分钟限制
     */
    private static Map<String, QpmCount> QPM_COUNT = new ConcurrentHashMap<>();

    private static final int TIME_EXPIRE = 60 * 1000;

    public static boolean isPass(String key, int qpmLimit) {
        QpmCount qpm = QPM_COUNT.get(key);
        if (qpm == null) {
            QPM_COUNT.put(key, new QpmCount(System.currentTimeMillis()));
            return true;
        }

        long time = qpm.getTime();
        long timeDif = System.currentTimeMillis() - time;
        if (timeDif > TIME_EXPIRE) {
            QPM_COUNT.remove(key);
            QPM_COUNT.put(key, new QpmCount(System.currentTimeMillis()));
        } else {
            int count = qpm.getCount().incrementAndGet();
            if (count >= qpmLimit) {
                return false;
            }
        }
        return true;
    }

    @Data
    private static class QpmCount {
        long          time;
        AtomicInteger count;

        public QpmCount(long time) {
            this.time = time;
            this.count = new AtomicInteger();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));

        Thread.sleep(1200);

        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
        System.out.println(isPass("a",3));
    }

}
