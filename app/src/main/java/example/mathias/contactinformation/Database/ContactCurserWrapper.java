package example.mathias.contactinformation.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.Database.ContactDBSchema.ContactTable;

/**
 * Created by Mathias on 26/03/2018.
 */

public class ContactCurserWrapper extends CursorWrapper {

    public ContactCurserWrapper(Cursor cursor) {
        super(cursor);
    }

    public ContactBE getContact() {
        String uuidString = getString(getColumnIndex(ContactTable.Cols.UUID));
        String name = getString(getColumnIndex(ContactTable.Cols.NAME));
        String address = getString(getColumnIndex(ContactTable.Cols.ADDRESS));
        String birthday = getString(getColumnIndex(ContactTable.Cols.BIRTHDAY));
        String location = getString(getColumnIndex(ContactTable.Cols.LOCATION));
        String mail = getString(getColumnIndex(ContactTable.Cols.MAIL));
        String phone = getString(getColumnIndex(ContactTable.Cols.PHONE));
        String website = getString(getColumnIndex(ContactTable.Cols.WEBSITE));

        ContactBE contact = new ContactBE(UUID.fromString(uuidString));
        contact.setName(name);
        contact.setAddress(address);
        contact.setBirthDay(birthday);
        contact.setLocation(location);
        contact.setMailAddress(mail);
        contact.setPhoneNumber(phone);
        contact.setWebsite(website);

        return contact;
    }
}
