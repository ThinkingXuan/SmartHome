package com.nuist.you.util;


import java.util.logging.Logger;

/**
 * author: youxuan
 */

public class LogUtil {
    public static void info(String message){
        Logger logger = Logger.getLogger("");
        logger.info(message);
    }
}
