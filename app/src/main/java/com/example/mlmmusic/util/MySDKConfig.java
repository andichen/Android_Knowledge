package com.example.mlmmusic.util;

public class MySDKConfig {

    //默认配置
    private static boolean sDebug = false;
    private static long sTimeout = 8000L;

    private static final MySDKConfig.Config CONFIG = new MySDKConfig.Config();

    public static class Config {
        private Config() {
        }

        /**
         * 设置调试模式
         *
         * @param isDebug 模式
         * @return Config
         */
        public MySDKConfig.Config setDebug(final boolean isDebug) {
            sDebug = isDebug;
            return this;
        }

        /**
         * 设置超时时间
         *
         * @param timeout 超时时间
         * @return Config
         */
        public MySDKConfig.Config setTimeout(final long timeout) {
            //此处演示了边界值的处理方式
            long minTimeout = 3000L;
            if (timeout < minTimeout) {
                sTimeout = minTimeout;
            } else {
                sTimeout = timeout;
            }
            return this;
        }

        public void set() {

        }
    }



    public static boolean isDebug() {
        return sDebug;
    }

    public static long getTimeout() {
        return sTimeout;
    }

    public static MySDKConfig.Config getConfig() {
        return CONFIG;
    }

}
