import interfaces.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.MSSQLJDBCService;
import services.MSSQLQueryService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        String fileName;
        String jdbcUrl = null;
        String dbName = null;
        String dbLogin = null;
        String dbPassword = null;

//        if (args.length != 1) {
//            logger.error("Wrong amount of parameters");
//            System.exit(0);
//        }

        Properties prop = new Properties();
//        fileName = args[0];
        fileName = "db.properties";
        try (FileInputStream fis = new FileInputStream(fileName)) {
            prop.load(fis);
            jdbcUrl = prop.getProperty("jdbc.url");
            dbName = prop.getProperty("db.name");
            dbLogin = prop.getProperty("db.login");
            dbPassword = prop.getProperty("db.password");
        } catch (FileNotFoundException ex) {
            logger.error("Provided file is not found. Default properties will be used.");
        } catch (IOException ex) {
            logger.error("Something went wrong while reading the file. Default properties will be used.");
        }

        DBService mssqlQueryService = (jdbcUrl != null) ? new MSSQLJDBCService(jdbcUrl, dbName, dbLogin, dbPassword) : new MSSQLJDBCService();

        mssqlQueryService.showTables();
        mssqlQueryService.showColumns();
        mssqlQueryService.showConstraints();
        mssqlQueryService.showIndexInfo();
    }
}
