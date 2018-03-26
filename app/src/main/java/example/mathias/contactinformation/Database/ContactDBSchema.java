package example.mathias.contactinformation.Database;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mathias on 26/03/2018.
 */

public class ContactDBSchema {

    public static final class ContactTable {

        public static final String NAME = "contacts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String ADDRESS = "address";
            public static final String LOCATION = "location";
            public static final String PHONE= "phone";
            public static final String MAIL = "mail";
            public static final String WEBSITE = "website";
            public static final String BIRTHDAY = "birthday";
        }
    }
}
