package com.netcracker;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final int BALANCE_ATTRIBUTE_ID = 1;

    public static final int DRAFT_ATTRIBUTE_ID = 2;

    public static final int PASSWORD_ATTRIBUTE_ID = 3;

    public static final int OPERATION_ATTRIBUTE_ID = 4;

    public static final int SUM_ATTRIBUTE_ID = 5;

    public static final int CURRENCY_ATTRIBUTE_ID = 6;

    public static final int SENDER_ATTRIBUTE_ID = 7;

    public static final int RECIPIENT_ATTRIBUTE_ID = 8;

    public static final Map<String, Integer> constMap = new HashMap<>() {{
        put("currency", CURRENCY_ATTRIBUTE_ID);
        put("balance", BALANCE_ATTRIBUTE_ID);
        put("sender", SENDER_ATTRIBUTE_ID);
        put("recipient", RECIPIENT_ATTRIBUTE_ID);
        put("password", PASSWORD_ATTRIBUTE_ID);
        put("draft", DRAFT_ATTRIBUTE_ID);
        put("sum", SUM_ATTRIBUTE_ID);
        put("operation", OPERATION_ATTRIBUTE_ID);
    }};
}
