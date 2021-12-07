package com.test.creditsuisse.helper;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JsonHelper {
    public JSONObject jsonConstruction() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("host", "12345");
        object.put("id", "scsmbstgra");
        object.put("state", "STARTED");
        object.put("type", "APPLICATION_LOG");
        object.put("timestamp", 1491377495212d);
        return object;
    }

    public JSONObject jsonSecondConstruction() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("host", "12345");
        object.put("id", "scsmbstgra");
        object.put("state", "STARTED");
        object.put("type", "APPLICATION_LOG");
        object.put("timestamp", 1491377495215d);
        return object;
    }

    public JSONObject jsonNoTypeNoHostSet() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", "scsmbstgrz");
        object.put("state", "STARTED");
        object.put("timestamp", 1491377495215d);
        return object;
    }

    public JSONObject jsonNoTypeNoHostSetSecondObject() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("id", "scsmbstgrz");
        object.put("state", "STARTED");
        object.put("timestamp", 1491377495220d);
        return object;
    }
}
