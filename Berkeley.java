/**
 * Created by yeonwoo_kim on 11/3/16.
 */

import com.sleepycat.je.*;

import java.io.File;

public class Berkeley {
    Environment myDbEnvironment;
    Database myDatabase;

    public Berkeley() {
        myDbEnvironment = null;
        try {
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            myDbEnvironment = new Environment(new File("/db"),
                    envConfig);

            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            dbConfig.setSortedDuplicates(true);
            //TODO: configure database

            myDatabase = myDbEnvironment.openDatabase(null, "berkeleyDB", dbConfig);


        } catch (DatabaseException dbe) {
            // Exception handling goes here
        }
    }

    public void close() {
        try {
            if (myDatabase != null) {
                myDatabase.close();
            }

            if (myDbEnvironment != null) {
                myDbEnvironment.close();
            }
        } catch (DatabaseException dbe) {
            // Exception handling goes here
        }
    }
}
