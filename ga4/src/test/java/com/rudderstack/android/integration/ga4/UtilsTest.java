package com.rudderstack.android.integration.ga4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UtilsTest {
    private static final Map TEST_MAP = new HashMap<String, Object>();

    @Before
    public void init() {
        Map data = new HashMap<String, Object>();
        data.put("nameValuePairs", "value_string");
        TEST_MAP.put("values", Collections.singletonList(data));

    }

    @Test
    public void getString() {
        assertThat(Utils.getString(TEST_MAP), is("\"[value_string]\""));
    }

    @Test
    public void getType() {
        System.out.println("Type is: " + TEST_MAP.getClass().getSimpleName());
        assertThat(Utils.getType(TEST_MAP), is("HashMap"));
    }
}