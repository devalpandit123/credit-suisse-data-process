package com.test.creditsuisse.service;

import com.test.creditsuisse.dao.DataProcessDao;
import com.test.creditsuisse.model.DataProcessEvent;
import lombok.NoArgsConstructor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.sql.SQLException;
import java.util.UUID;

@Service
@NoArgsConstructor
public class DataProcessService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessService.class);

    @Autowired
    private DataProcessEvent dataProcessEvent;

    @Autowired
    private DataProcessDao dataProcessDao;

    private JSONArray parsejsonArray = new JSONArray();
    private JSONArray newParseJsonArray = new JSONArray();
    private JSONArray processParseJsonArray = new JSONArray();

    public JSONArray processLogMessage(String filePath) throws SQLException {
        boolean alert = false;
        int counter = 0;
        StringBuilder resultStringBuilder = new StringBuilder();
        try {
            LOGGER.debug("Creation of new Table");
            dataProcessDao.createEventsTable();
            try (BufferedReader br = new BufferedReader(new FileReader(new File(filePath)))) {
                String line;
                while ((line = br.readLine()) != null) {
                    LOGGER.debug(line);
                    resultStringBuilder.append(line).append("\n");
                    JSONObject jsonObject = new JSONObject(line);
                    LOGGER.debug("get first element: {}", jsonObject.get("id"));
                    parsejsonArray.put(jsonObject);
                }
            }
            for (int i = 0; i < parsejsonArray.length(); i++) {
                JSONObject jsonFirstObject = (JSONObject) parsejsonArray.get(i);
                LOGGER.info("first object timestamp: {}", jsonFirstObject.getDouble("timestamp"));
                if (newParseJsonArray.length() == 0) {
                    newParseJsonArray.put(parsejsonArray.get(i));
                    LOGGER.info("First element: {}", newParseJsonArray.get(0));
                } else {
                    processParseJsonArray = appendArray(newParseJsonArray,
                            parsejsonArray, jsonFirstObject, dataProcessEvent, dataProcessDao, alert, counter);
                }
            }
            dataProcessDao.displayAllDBEntries();
            if (newParseJsonArray.length() == 1){
                processParseJsonArray = newParseJsonArray;
            }
        }
        catch (SQLException | IOException | JSONException e) {
            LOGGER.error("ERROR: " , e);
        }
        finally {
            dataProcessDao.deleteAllDBEntries();
            dataProcessDao.closeDatabase();
        }
        return processParseJsonArray;
    }
    public JSONArray appendArray(JSONArray newParseJsonArray, JSONArray parsejsonArray, JSONObject jsonFirstObject,
                                 DataProcessEvent dataProcessEvent, DataProcessDao dataProcessDao,
                                 boolean alert, int counter) throws SQLException{
        alert = false;
        for (int j = 0; j < newParseJsonArray.length(); j++) {
            JSONObject jsonSecondObject = (JSONObject) parsejsonArray.get(j);
            if (jsonSecondObject.get("id").equals(jsonFirstObject.get("id"))) {
                LOGGER.debug("first: {}", jsonFirstObject.getDouble("timestamp"));
                LOGGER.debug("second: {}", jsonSecondObject.getDouble("timestamp"));
                long timestampValue = Math.abs(jsonFirstObject.getLong("timestamp") - jsonSecondObject.getLong("timestamp"));
                LOGGER.info("Difference of times for ID: {} is {}", jsonSecondObject.get("id"), timestampValue);
                if (timestampValue > 4) {
                    LOGGER.info("RED ALERT for ID: {} !!!: Time: {}", jsonSecondObject.get("id"), timestampValue);
                    alert = true;
                }
                counter = 1;
                dataProcessEvent.setId(UUID.randomUUID().toString());
                dataProcessEvent.setDuration(timestampValue);
                if(jsonFirstObject.has("type")) {
                    dataProcessEvent.setType((String) jsonFirstObject.get("type"));
                }
                else{
                    dataProcessEvent.setType("N/A");
                }
                if(jsonFirstObject.has("host")){
                    dataProcessEvent.setHost((String) jsonFirstObject.get("host"));
                }
                else{
                    dataProcessEvent.setHost("N/A");
                }
                dataProcessEvent.setAlert(alert);
                dataProcessDao.writeEvent(dataProcessEvent);
                LOGGER.info("Event sent to Table: Events");
            }
        }
        if (counter == 0) {
            newParseJsonArray.put(jsonFirstObject);
        }
        return newParseJsonArray;
    }
}
