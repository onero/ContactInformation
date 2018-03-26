package example.mathias.contactinformation.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.icu.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import example.mathias.contactinformation.BE.ContactBE;

/**
 * Created by Mathias on 26/03/2018.
 */

public class ContactCurserWrapper extends CursorWrapper {

    public ContactCurserWrapper(Cursor cursor) {
        super(cursor);
    }

    public ContactBE getContact() {
        String uuidString = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.UUID));
        String name = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.NAME));
        String address = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.ADDRESS));
        String birthday = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.BIRTHDAY));
        String location = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.LOCATION));
        String mail = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.MAIL));
        String phone = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.PHONE));
        String website = getString(getColumnIndex(ContactDBSchema.ContactTable.Cols.WEBSITE));

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
