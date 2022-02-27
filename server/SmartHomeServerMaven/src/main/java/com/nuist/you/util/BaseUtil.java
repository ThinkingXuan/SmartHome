package com.nuist.you.util;

import java.util.UUID;

public class BaseUtil {
    //UUID
    public static String getUUID32() {

        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
