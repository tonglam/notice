package com.tong.notice.constant;

/**
 * Create by tong on 2022/12/30
 */
public class Constant {

    public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";

    // redis
    public static String HERMES_REDIS_KEY = "hermes_bags";
    public static String EXCHANGE_FETCH_KEY="exchange_fetch";
    public static String EXCHANGE_KEY="exchange_%s_%s";
    public static final String REDIS_KEY = "hermes_bags";

    // telegramBot
    public static final String NOTIFICATION_KEY = "hermes_notification";
    private static final String NOTIFICATION_PREFIX = "https://letletme.top/telegramBot/letletme/";
    public static final String NOTIFICATION_TEXT = "https://letletme.top/telegramBot/letletme/textNotification";
    public static final String NOTIFICATION_IMAGE = "https://letletme.top/telegramBot/letletme/imageNotification";

}
