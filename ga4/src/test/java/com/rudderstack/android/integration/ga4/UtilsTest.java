package com.rudderstack.android.integration.ga4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class UtilsTest {
    private static final Map<String, Object> TEST_MAP = new HashMap<>();

    @Before
    public void init() {
        Map<String, Object> data = new HashMap<>();
        data.put("nameValuePairs", "value_string");
        TEST_MAP.put("values", Collections.singletonList(data));

    }

    @Test
    public void getString() {
        assertThat(Utils.getString(TEST_MAP), is("{values=[{nameValuePairs=value_string}]}"));
    }

    @Test
    public void getType() {
        System.out.println("Type is: " + TEST_MAP.getClass().getSimpleName());
        assertThat(Utils.getType(TEST_MAP), is("HashMap"));

        Object treeMap = new TreeMap<String, Object>();
        assertThat(Utils.getType(treeMap), is("TreeMap"));

        Object set = new HashSet<>();
        assertThat(Utils.getType(set), is("HashSet"));

        Object arrayList = new ArrayList<>();
        assertThat(Utils.getType(arrayList), is("ArrayList"));

        Object[] array = new Object[1];
        assertThat(Utils.getType(array), is("Array"));

    }
}