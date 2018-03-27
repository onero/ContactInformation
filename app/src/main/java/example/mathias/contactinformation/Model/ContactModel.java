package example.mathias.contactinformation.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.Database.ContactBaseHelper;
import example.mathias.contactinformation.Database.ContactCurserWrapper;

import example.mathias.contactinformation.Database.ContactDBSchema.ContactTable;

/**
 * Created by Mathias on 19/03/2018.
 */

public class ContactModel {


    private static ContactModel sContactModel;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ContactModel get(Context context) {
        if (sContactModel == null) {
            sContactModel = new ContactModel(context);
        }
        return sContactModel;
    }

    /**
     *
     * @param context
     */
    private ContactModel(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ContactBaseHelper(mContext)
                .getWritableDatabase();
    }

    public List<ContactBE> getContacts() {
        List<ContactBE> contacts = new ArrayList<>();

        try (ContactCurserWrapper cursor = queryContacts(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contacts.add(cursor.getContact());
                cursor.moveToNext();
            }
        }
        return contacts;
    }

    public ContactBE getContact(UUID id) {

        try (ContactCurserWrapper cursor = queryContacts(
                ContactTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        )) {
            if (cursor.getCount() == 0) return null;

            cursor.moveToFirst();
            return cursor.getContact();
        }
    }

    private static ContentValues getContentValues(ContactBE contact) {
        ContentValues values = new ContentValues();
        values.put(ContactTable.Cols.UUID, contact.getId().toString());
        values.put(ContactTable.Cols.NAME, contact.getName());
        values.put(ContactTable.Cols.ADDRESS, contact.getAddress());
        values.put(ContactTable.Cols.LOCATION, contact.getLocation());
        values.put(ContactTable.Cols.PHONE, contact.getPhoneNumber());
        values.put(ContactTable.Cols.MAIL, contact.getMailAddress());
        values.put(ContactTable.Cols.WEBSITE, contact.getWebsite());
        values.put(ContactTable.Cols.BIRTHDAY, contact.getBirthDay());

        return values;
    }

    public void addContact(ContactBE contact) {
        ContentValues values = getContentValues(contact);
        mDatabase.insert(ContactTable.NAME, null, values);
    }

    public void updateContact(ContactBE contact) {
        String uuidString = contact.getId().toString();
        ContentValues values = getContentValues(contact);

        mDatabase.update(ContactTable.NAME, values,
                ContactTable.Cols.UUID + " = ?",
                new String [] {uuidString});
    }

    public File getPhotoFile(ContactBE contact) {
        File fileDir = mContext.getFilesDir();
        return new File(fileDir, contact.getPicture());
    }

    private ContactCurserWrapper queryContacts(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ContactTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy,
                null, // having
                null // orderBy
        );
        return new ContactCurserWrapper(cursor);
    }

    public void deleteContact(UUID id) {
        mDatabase.delete(ContactTable.NAME,
                ContactTable.Cols.UUID + " = ?",
                new String[] {id.toString()});
    }

}
