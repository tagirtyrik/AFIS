package db;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.derby.jdbc.ClientDataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;

/**
 * Фабрика для настройки источника данных
 * Created by GeneraL on 27.03.2015.
 */
public class DataSourceFactory {
    public static DataSource dataSource=null;
    public static  void rebind(){
        try {
            dataSource=getMySQLDataSource();
            Context ctx = new InitialContext();
            ctx.rebind("jdbc/afisDB", dataSource);
        }
        catch ( NamingException e) {
            e.printStackTrace();
        }
    }
    public static DataSource getMySQLDataSource() {
        Properties props = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDS = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
            mysqlDS = new MysqlDataSource();
            mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
            mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
            mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));

        } catch (IOException  e) {
            e.printStackTrace();
        }

        return mysqlDS;
    }

    public static DataSource getDerbyDataSource()//не отлаживал
    {
        Properties props = new Properties();
        FileInputStream fis = null;
        ClientDataSource derbyDS = null;
        try {
            fis = new FileInputStream("db.properties");
            props.load(fis);
            derbyDS = new ClientDataSource();
            derbyDS.setDatabaseName(props.getProperty("DERBY_DB_NAME"));
            derbyDS.setServerName(props.getProperty("DERBY_DB_SERVERNAME"));
            derbyDS.setPortNumber(Integer.parseInt(props.getProperty("DERBY_DB_PORT")));

            derbyDS.setUser(props.getProperty("DERBY_DB_USERNAME"));
            derbyDS.setPassword(props.getProperty("DERBY_DB_PASSWORD"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return derbyDS;
    }
    public static void unbindDataSourse(){
        if (dataSource!=null){
            try {
            Context ctx = new InitialContext();
            ctx.unbind("jdbc/afisDB");
            }
                catch ( NamingException e) {
                    e.printStackTrace();
            }
        }
    }

}
