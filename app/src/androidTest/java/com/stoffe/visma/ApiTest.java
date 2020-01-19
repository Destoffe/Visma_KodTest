package com.stoffe.visma;

import com.stoffe.visma.api.JSONWeatherParser;
import com.stoffe.visma.api.WeatherHTTPClient;
import com.stoffe.visma.models.Weather;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class ApiTest {
    private Weather testWeater1;
    private final String CORRECT_CITY = "VAXJO";

    @Before
    public void fetchData() throws JSONException {
        String data = ((new WeatherHTTPClient()).getWeatherData(CORRECT_CITY));
        testWeater1 = JSONWeatherParser.getWeather(data);
        testWeater1.iconData = ((new WeatherHTTPClient()).getImage(testWeater1.currentCondition.getIcon()));
        testWeater1.setBitmap();

    }

    @Test
    public void testdata(){
        assertNotNull(testWeater1.bitmap);
        assertEquals(CORRECT_CITY,testWeater1.loc.getCity().toUpperCase());
    }

    @After
    public void tearDown(){
        testWeater1 = null;
    }
}
