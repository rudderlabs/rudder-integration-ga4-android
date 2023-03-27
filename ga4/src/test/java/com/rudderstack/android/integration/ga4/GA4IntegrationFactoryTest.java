package com.rudderstack.android.integration.ga4;

import static com.rudderstack.android.integration.ga4.TestConstants.getProductMap;
import static com.rudderstack.android.integration.ga4.TestConstants.getProductSingleJSONArray;
import static com.rudderstack.android.integration.ga4.TestConstants.getProductSingleLinkedHashMap;
import static com.rudderstack.android.integration.ga4.TestConstants.getProductsArrayList;
import static com.rudderstack.android.integration.ga4.TestConstants.getProductsJSONArray;
import static com.rudderstack.android.integration.ga4.TestConstants.getProductsLinkedHashMap;
import static com.rudderstack.android.integration.ga4.TestConstants.getProductsList;
import static com.rudderstack.android.integration.ga4.TestUtils.jsonArrayToString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.json.JSONArray;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GA4IntegrationFactoryTest {

    @Test
    public void whenObjectParameterIsNull() {
        GA4IntegrationFactory firebaseIntegrationFactory = mock(GA4IntegrationFactory.class);

        when(firebaseIntegrationFactory.getProductsJSONArray(any())).thenCallRealMethod();

        Assert.assertNull(firebaseIntegrationFactory.getProductsJSONArray(null));
    }

    @Test
    public void whenObjectParameterIsJSONArray() {
        GA4IntegrationFactory firebaseIntegrationFactory = mock(GA4IntegrationFactory.class);
        JSONArray products = getProductsJSONArray();

        when(firebaseIntegrationFactory.getProductsJSONArray(any())).thenCallRealMethod();

        verifyJSONString(products, firebaseIntegrationFactory.getProductsJSONArray(products));
    }

    @Test
    public void whenObjectParameterIsList() {
        GA4IntegrationFactory firebaseIntegrationFactory = mock(GA4IntegrationFactory.class);
        JSONArray products = getProductsJSONArray();
        List<Map<String, Object>> productList = getProductsList();

        when(firebaseIntegrationFactory.getProductsJSONArray(any())).thenCallRealMethod();

        verifyJSONString(products, firebaseIntegrationFactory.getProductsJSONArray(productList));
    }

    @Test
    public void whenObjectParameterIsMap() {
        GA4IntegrationFactory firebaseIntegrationFactory = mock(GA4IntegrationFactory.class);
        JSONArray product = getProductSingleJSONArray();
        Map<String, Object> productList = getProductMap();

        when(firebaseIntegrationFactory.getProductsJSONArray(any())).thenCallRealMethod();

        verifyJSONString(product, firebaseIntegrationFactory.getProductsJSONArray(productList));
    }

    @Test
    public void whenObjectParameterIsLinkedHashMap() {
        GA4IntegrationFactory firebaseIntegrationFactory = mock(GA4IntegrationFactory.class);
        JSONArray products = getProductsJSONArray();
        List<LinkedHashMap<String, Object>> productList = getProductsLinkedHashMap();

        when(firebaseIntegrationFactory.getProductsJSONArray(any())).thenCallRealMethod();

        verifyJSONString(products, firebaseIntegrationFactory.getProductsJSONArray(productList));
    }

    @Test
    public void whenObjectParameterIsSingleLinkedHashMap() {
        GA4IntegrationFactory firebaseIntegrationFactory = mock(GA4IntegrationFactory.class);
        JSONArray product = getProductSingleJSONArray();
        LinkedHashMap<String, Object> productList = getProductSingleLinkedHashMap();

        when(firebaseIntegrationFactory.getProductsJSONArray(any())).thenCallRealMethod();

        verifyJSONString(product, firebaseIntegrationFactory.getProductsJSONArray(productList));
    }

    @Test
    public void whenObjectParameterIsArrayList() {
        GA4IntegrationFactory firebaseIntegrationFactory = mock(GA4IntegrationFactory.class);
        JSONArray product = getProductsJSONArray();
        ArrayList<Map<String, Object>> productList = getProductsArrayList();

        when(firebaseIntegrationFactory.getProductsJSONArray(any())).thenCallRealMethod();

        verifyJSONString(product, firebaseIntegrationFactory.getProductsJSONArray(productList));
    }

    private void verifyJSONString(JSONArray products1, JSONArray products2) {
        Assert.assertEquals(jsonArrayToString(products1), jsonArrayToString(products2));
    }
}
