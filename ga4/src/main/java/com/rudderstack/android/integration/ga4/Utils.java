package com.rudderstack.android.integration.ga4;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.rudderstack.android.sdk.core.RudderLogger;
import com.rudderstack.android.sdk.core.ecomm.ECommerceEvents;
import com.rudderstack.android.sdk.core.ecomm.ECommerceParamNames;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Utils {

    static final List<String> IDENTIFY_RESERVED_KEYWORDS = Arrays.asList(
            "age", "gender", "interest"
    );

    static final List<String> TRACK_RESERVED_KEYWORDS = Arrays.asList (
            "product_id", "name", "category", "quantity", "price", "currency", "value", "revenue", "total",
            "tax", "shipping", "coupon", "cart_id", "payment_method", "query", "list_id", "promotion_id", "creative",
            "affiliation", "share_via", "order_id", ECommerceParamNames.PRODUCTS, FirebaseAnalytics.Param.SCREEN_NAME);

    static final Map<String, String> ECOMMERCE_EVENTS_MAPPING = new HashMap<String, String>() {
        {
            put(ECommerceEvents.PAYMENT_INFO_ENTERED, FirebaseAnalytics.Event.ADD_PAYMENT_INFO);
            put(ECommerceEvents.PRODUCT_ADDED, FirebaseAnalytics.Event.ADD_TO_CART);
            put(ECommerceEvents.PRODUCT_ADDED_TO_WISH_LIST, FirebaseAnalytics.Event.ADD_TO_WISHLIST);
            put(ECommerceEvents.CHECKOUT_STARTED, FirebaseAnalytics.Event.BEGIN_CHECKOUT);
            put(ECommerceEvents.ORDER_COMPLETED, FirebaseAnalytics.Event.PURCHASE);
            put(ECommerceEvents.ORDER_REFUNDED, FirebaseAnalytics.Event.REFUND);
            put(ECommerceEvents.PRODUCTS_SEARCHED, FirebaseAnalytics.Event.SEARCH);
            put(ECommerceEvents.CART_SHARED, FirebaseAnalytics.Event.SHARE);
            put(ECommerceEvents.PRODUCT_SHARED, FirebaseAnalytics.Event.SHARE);
            put(ECommerceEvents.PRODUCT_VIEWED, FirebaseAnalytics.Event.VIEW_ITEM);
            put(ECommerceEvents.PRODUCT_LIST_VIEWED, FirebaseAnalytics.Event.VIEW_ITEM_LIST);
            put(ECommerceEvents.PRODUCT_REMOVED, FirebaseAnalytics.Event.REMOVE_FROM_CART);
            put(ECommerceEvents.PRODUCT_CLICKED, FirebaseAnalytics.Event.SELECT_CONTENT);
            put(ECommerceEvents.PROMOTION_VIEWED, FirebaseAnalytics.Event.VIEW_PROMOTION);
            put(ECommerceEvents.PROMOTION_CLICKED, FirebaseAnalytics.Event.SELECT_PROMOTION);
            put(ECommerceEvents.CART_VIEWED, FirebaseAnalytics.Event.VIEW_CART);
        }
    };

    static final List<String> EVENT_WITH_PRODUCTS_ARRAY = Arrays.asList(
            FirebaseAnalytics.Event.BEGIN_CHECKOUT,
            FirebaseAnalytics.Event.PURCHASE,
            FirebaseAnalytics.Event.REFUND,
            FirebaseAnalytics.Event.VIEW_ITEM_LIST,
            FirebaseAnalytics.Event.VIEW_CART
    );

    static final List<String> EVENT_WITH_PRODUCTS_AT_ROOT = Arrays.asList(
            FirebaseAnalytics.Event.ADD_TO_CART,
            FirebaseAnalytics.Event.ADD_TO_WISHLIST,
            FirebaseAnalytics.Event.VIEW_ITEM,
            FirebaseAnalytics.Event.REMOVE_FROM_CART
    );

    static final Map<String, String> PRODUCT_PROPERTIES_MAPPING = new HashMap<String, String>() {
        {
            put("product_id", FirebaseAnalytics.Param.ITEM_ID);
            put("name", FirebaseAnalytics.Param.ITEM_NAME);
            put("category", FirebaseAnalytics.Param.ITEM_CATEGORY);
            put("quantity", FirebaseAnalytics.Param.QUANTITY);
            put("price", FirebaseAnalytics.Param.PRICE);
        }
    };
    
    static final Map<String, String> ECOMMERCE_PROPERTY_MAPPING = new HashMap<String, String>() {
        {
            put("payment_method", FirebaseAnalytics.Param.PAYMENT_TYPE);
            put("coupon", FirebaseAnalytics.Param.COUPON);
            put("query", FirebaseAnalytics.Param.SEARCH_TERM);
            put("list_id", FirebaseAnalytics.Param.ITEM_LIST_ID);
            put("promotion_id", FirebaseAnalytics.Param.PROMOTION_ID);
            put("creative", FirebaseAnalytics.Param.CREATIVE_NAME);
            put("affiliation", FirebaseAnalytics.Param.AFFILIATION);
            put("share_via", FirebaseAnalytics.Param.METHOD);
        }
    };

    static Map<String, String> transformUserTraits(Map<String, Object> userTraits) {
        Map<String, String> transformedUserTraits = new HashMap<>();
        for (Map.Entry<String, Object> userTrait : userTraits.entrySet()) {
            String value = getString(userTrait.getValue());
            if (value != null) {
                transformedUserTraits.put(userTrait.getKey(), value);
            }
        }
        return transformedUserTraits;
    }

    static String getTrimKey(String key) {
        String firebaseEvent = key.toLowerCase().trim().replace(" ", "_");
        if (firebaseEvent.length() > 40) {
            firebaseEvent = firebaseEvent.substring(0, 40);
        }
        return firebaseEvent;
    }

    static String getString(Object object) {
        if (object == null) {
            return null;
        }
        switch (getType(object)) {
            case "Byte":
            case "Short":
            case "Integer":
            case "Long":
            case "Float":
            case "Double":
            case "Boolean":
            case "Character":
                return object.toString();
            case "String":
                return (String) object;
            case "Map":
                return new Gson().toJson(getStringFromMap((Map<String, Object>) object));
            case "Collection":
            case "Array":
                return new Gson().toJson(object);
            default:
                return null;
        }
    }

    static String getType(Object object) {
        if (object.getClass().isArray()) {
            return "Array";
        }
        if (object instanceof Collection)
            return "Collection";
        if(object instanceof Map)
            return "Map";
        return object.getClass().getSimpleName();
    }

    static boolean isDouble(Object value) {
        if (value instanceof Number) {
            return true;
        }
        if (value instanceof String) {
            try {
                Double.parseDouble((String) value);
                return true;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
        return false;
    }

    static boolean isLong(Object value) {
        if (value instanceof Number) {
            return true;
        }
        if (value instanceof String) {
            try {
                Long.parseLong((String) value);
                return true;
            } catch (NumberFormatException ignored) {
                return false;
            }
        }
        return false;
    }

    static double getDouble(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException ignored) {
                RudderLogger.logDebug("Unable to convert the value: " + value +
                        " to Double, using the defaultValue: " + (double) 0);
            }
        }
        return 0;
    }

    static long getLong(Object value) {
        if (value == null) {
            return 0;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        if (value instanceof String) {
            try {
                return Long.parseLong((String) value);
            } catch (NumberFormatException ignored) {
                RudderLogger.logDebug("Unable to convert the value: " + value +
                        " to Long, using the defaultValue: " + (long) 0);
            }
        }
        return 0;
    }
    private static final String NAME_VALUE_PAIRS_IDENTIFIER = "nameValuePairs";
    static String getStringFromMap(Map<String, Object> map) {
        if (map.containsKey("values")) {
            List<Object> arrayList = new ArrayList<>();
            List<Map> tempList = (List<Map>) map.get("values");
            for (Map ltMap : tempList) {
                arrayList.add(ltMap.get(NAME_VALUE_PAIRS_IDENTIFIER));
            }
            return arrayList.toString();
        }
        if (map.containsKey(NAME_VALUE_PAIRS_IDENTIFIER)) {
            return map.get(NAME_VALUE_PAIRS_IDENTIFIER).toString();
        }
        return map.toString();
    }

    public static boolean isEmpty(Object value) {
        if(value == null){
            return true;
        }
        if (value instanceof String) {
            return (((String) value).trim().isEmpty());
        }
        if (value instanceof JSONArray) {
            return (((JSONArray) value).length() == 0);
        }
        if (value instanceof JSONObject) {
            return (((JSONObject) value).length() == 0);
        }
        if (value instanceof Map) {
            return ((Map<?, ?>) value).size() == 0;
        }
        return false;
    }
}
