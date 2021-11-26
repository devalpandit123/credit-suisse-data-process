package com.test.creditsuisse.controller;

import com.test.creditsuisse.service.DataProcessService;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;


@RestController
public class DataProcessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataProcessController.class);

    @Autowired
    private DataProcessService dataProcessService;

    @Autowired
    private ResourceLoader resourceLoader;

    @GetMapping(value = "/processLogFile")
    public StringBuilder messageProcessor(@RequestParam String filePath) throws IOException, SQLException {
        Resource resource = resourceLoader.getResource("classpath:static/logfile.txt");
        LOGGER.info("Resource: {}", resource.contentLength());
        LOGGER.debug("Resource: {}", resource.getInputStream());
        return dataProcessService.processLogMessage(filePath);
    }
}
