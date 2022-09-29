package sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;

public class SqlHandler {

    Logger logger = LoggerFactory.getLogger(SqlHandler.class);

    private Connection connection;

    public SqlHandler() {
        try {
            String url = "jdbc:postgresql://database-1.cw781siizbri.us-east-1.rds.amazonaws.com:5432/postgres";
            Properties props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", loadPassword());
            connection = DriverManager.getConnection(url, props);
        } catch (Exception e) {
            logger.error("Exception --> Class SqlHandler --> error in constructor: " + e.getMessage());
        }
    }

    private String loadPassword() {
        String password = "";
        try {
            InputStream in = getClass().getResourceAsStream("/sql_credentials.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            password = reader.readLine();
            System.out.println(password);
        } catch (Exception e) {
            logger.error("Exception --> Class SqlHandler --> error in method loadPassword(): " + e.getMessage());
        }
        return password;
    }

    public String pullFromTable() {

        StringBuilder sb = new StringBuilder();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM email_details");

            while (rs.next()) {
                sb.append(rs.getString(2) + " " + rs.getString(3) + " " +
                        rs.getString(4) + " " + rs.getString(5) + "\n");
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            logger.error("Exception --> Class SqlHandler --> error in method pullFromTable() --> " + e.getMessage());
        }
        return sb.toString();
    }
}
