package com.test.creditsuisse.service;

import com.test.creditsuisse.dao.DataProcessDao;
import com.test.creditsuisse.helper.JsonHelper;
import com.test.creditsuisse.model.DataProcessEvent;
import com.test.creditsuisse.service.DataProcessService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataProcessServiceTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private JsonHelper jsonHelper;

    @Mock
    private DataProcessDao mockDataProcessDao;

    @Mock
    private DataProcessEvent mockDataProcessEvent;

    @InjectMocks
    private DataProcessService dataProcessService;

    @BeforeEach
    public void setUp() throws Exception {
        mockDataProcessDaoAuto(mockDataProcessDao);
    }

    @After
    public void tearDown() throws Exception {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessService.class);

    private void mockDataProcessDaoAuto(DataProcessDao mockDataProcessDao) throws SQLException {
        LOGGER.info("Initializing mock on every test");
        doNothing().when(mockDataProcessDao).createEventsTable();
        doNothing().when(mockDataProcessDao).displayAllDBEntries();
        doNothing().when(mockDataProcessDao).deleteAllDBEntries();
        doNothing().when(mockDataProcessDao).writeEvent(mockDataProcessEvent);
    }

    @Test
    public void test_processLogMessage_return_contents_of_mock_file_is_blank_true() throws SQLException {
        String FILE_NAME = "src//test//resources//static//testLogBlankFile.txt";
//        mockDataProcessDaoAuto(mockDataProcessDao);
        JSONArray actualStringOutput = dataProcessService.processLogMessage(FILE_NAME);
        assertEquals(0, actualStringOutput.length());
    }

    @Test
    public void test_processLogMessage_return_contents_of_mock_file_is_blank_() throws SQLException, JSONException {
        String FILE_NAME = "src//test//resources//static//testLogFileContainsJsonMultipleEntries.txt";
//        mockDataProcessDaoAuto(mockDataProcessDao);
        JSONArray actualStringOutput = dataProcessService.processLogMessage(FILE_NAME);
        JSONObject jsonObject = new JSONObject(actualStringOutput.get(0).toString());
        String expectedId = "scsmbstgra";
        Object actualId = jsonObject.get("id");
        assertEquals(expectedId, actualId);
    }

    @Test
    public void test_appendArray_id_matched_and_timestamp_less_than_4_success() throws SQLException, JSONException {
//        mockDataProcessDaoAuto(mockDataProcessDao);
        JSONObject jsonObject = jsonHelper.jsonConstruction();
        JSONObject jsonSecondObject = jsonHelper.jsonSecondConstruction();
        JSONArray newParseJsonArray = new JSONArray();
        newParseJsonArray.put(jsonObject.toString());
        JSONArray parseJsonArray = new JSONArray();
        parseJsonArray.put(jsonObject);
        parseJsonArray.put(jsonSecondObject);
        JSONArray resultantArray = dataProcessService.appendArray(newParseJsonArray, parseJsonArray, jsonObject, mockDataProcessEvent, mockDataProcessDao, false, 0);
        JSONObject object = new JSONObject((String) resultantArray.get(0));
        assertEquals("scsmbstgra", object.get("id"));
    }

    @Test
    public void test_appendArray_id_matched_and_timestamp_greater_than_4_No_Host_No_Type_set_success() throws SQLException, JSONException {
//        mockDataProcessDaoAuto(mockDataProcessDao);
        JSONObject jsonObject = jsonHelper.jsonNoTypeNoHostSet();
        JSONObject jsonSecondObject = jsonHelper.jsonNoTypeNoHostSetSecondObject();
        JSONArray newParseJsonArray = new JSONArray();
        newParseJsonArray.put(jsonObject.toString());
        newParseJsonArray.put(jsonSecondObject.toString());
        JSONArray parseJsonArray = new JSONArray();
        parseJsonArray.put(jsonObject);
        parseJsonArray.put(jsonSecondObject);
        JSONArray resultantArray = dataProcessService.appendArray(newParseJsonArray, parseJsonArray, jsonObject, mockDataProcessEvent, mockDataProcessDao, false, 0);
        JSONObject object = new JSONObject((String) resultantArray.get(0));
        assertFalse(object.has("type") && object.has("host"));
    }


























}