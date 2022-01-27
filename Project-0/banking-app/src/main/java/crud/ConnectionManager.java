package crud;/* Need to add mariadb dependency to the pom.xml file and refresh or else connection won't be established */

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

// Singleton class is implemented in order to prevent having to re-connect in every view.
// By making it a singleton class, we can access the same instance from the user class and account class.



public class ConnectionManager {
    //this is for keeping a connection object alive and referenced, and it will be used by this class
    private static Connection connection;
    //private because no one else should access this field directly - abstraction
    //static because we will never instantiate an object of this class, we just use the static functionality
    //Connection is an object that stores and keeps alive a connection to a database

    //no args constructor, not really used here
    private ConnectionManager() {
    }

    //this is a method to grab the connection above. Note that it works like a singleton, if there is
    //a connection we return that, otherwise create it and then return it.
    public static Connection getConnection() {
        if(connection == null) {
            connection = connect();
        }
        return connection;
    }
    //connection logic here

    private static Connection connect() {
        /*
        - jdbc:mariadb://<hostname>:<port>/<databaseName>?user=<username>&password=<password>
        - This is the string we need to use to connect to our database. We will build this string with each of the
        - variables (hostname, port, databaseName, username, password.
        - We will fill in the variables using the properties file which holds the credentials needed to connect to db.
        - The .properties file extension is added to the .gitignore file to prevent the credentials from being uploaded
        - github.
         */

        //try catch block because the things in here are likely to throw exceptions. We could throw these up further, but
        //we're going to handle them here.
        try {

            //Properties is an object that holds key/value pairs read from a file
            // the file reader gets the data out of the file, and when we call props.load it loads that data
            //into the properties object.

            Properties props = new Properties();
            FileReader fr = new FileReader("src/main/resources/jdbc.properties");
            props.load(fr);


            //next we concatenate the credentials needed to complete the connection.
            String connectionString = "jdbc:mariadb://" +
                    props.getProperty("hostname") + ":" +
                    props.getProperty("port") + "/" +
                    props.getProperty("dbname") + "?user=" +
                    props.getProperty("username") + "&password=" +
                    props.getProperty("password");


            connection = DriverManager.getConnection(connectionString);

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }


        return connection;
    }
}
