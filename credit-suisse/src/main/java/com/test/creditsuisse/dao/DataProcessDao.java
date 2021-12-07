package com.test.creditsuisse.dao;

import com.test.creditsuisse.model.DataProcessEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DataProcessDao {
    private static final Log logger = LogFactory.getLog(DataProcessDao.class);

    private static final String tableName = "Events";

    private static Connection connection;

    public DataProcessDao() {
        String connectionString = "jdbc:hsqldb:file:hsqldb/logs";

        logger.info("Opening database connection at < hsqldb/logs >");
        try {
            connection = DriverManager.getConnection(connectionString, "SA", "");
        }
        catch (SQLException ex){
            logger.error("SQLException: "+ex.getMessage());

        }
    }

    public void createEventsTable() throws SQLException {
        String createEvents = "CREATE TABLE IF NOT EXISTS Events (id VARCHAR(50) NOT NULL, duration FLOAT NOT NULL, " +
                "type VARCHAR(50), host VARCHAR(50), alert BOOLEAN NOT NULL)";

        logger.info("Creating Events table");
        connection.createStatement().executeUpdate(createEvents);
    }

    public void writeEvent(DataProcessEvent dataProcessEvent) throws SQLException {
        String addEvent = "INSERT INTO " + tableName + " (ID, duration, type, host, alert)  VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(addEvent);
        preparedStatement.setString(1, dataProcessEvent.getId());
        preparedStatement.setFloat(2, dataProcessEvent.getDuration());
        preparedStatement.setString(3, dataProcessEvent.getType());
        preparedStatement.setString(4, dataProcessEvent.getHost());
        preparedStatement.setBoolean(5, dataProcessEvent.isAlert());

        preparedStatement.executeUpdate();
    }

    public void closeDatabase() throws SQLException {
        logger.info("Closing DB connection.");
        connection.close();
    }

    public void displayAllDBEntries() throws SQLException {
        String getAll = "SELECT * FROM " + tableName;

        logger.info("Retrieving all DB entries in < " + tableName + " > table.");
        ResultSet resultSet = connection.createStatement().executeQuery(getAll);
        logger.info("All entries: {}"+resultSet);
        while (resultSet.next()) {
            logger.info("Alert for EventID <"+ resultSet.getString(1) + ">"+" Event duration: " +
                    resultSet.getString(2) + " milliseconds" + " Type: "
                    + resultSet.getString(3) + " Host: "
                    + resultSet.getString(4) + " and Alert: "
                    + resultSet.getString(5) + ". "
            );
        }
    }

    public void deleteAllDBEntries() throws SQLException {
        String deleteAll = "DELETE FROM " + tableName;

        logger.info("Deleting all entries in DB table < " + tableName + " >.");
        connection.createStatement().executeUpdate(deleteAll);
    }
}
