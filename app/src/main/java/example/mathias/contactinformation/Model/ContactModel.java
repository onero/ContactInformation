package example.mathias.contactinformation.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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

    // Singleton Model variable
    private static ContactModel sContactModel;

    // Context from outside the class
    private Context mContext;

    // Database from the baseHelper
    private SQLiteDatabase mDatabase;

    /**
     * Singleton Get method for getting the same instance every time,
     * while also providing the context.
     * @param context
     * @return
     */
    public static ContactModel get(Context context) {
        if (sContactModel == null) {
            sContactModel = new ContactModel(context);
        }
        return sContactModel;
    }

    /**
     * Constructor with Context from the outside, and instantiation of Database.
     * @param context
     */
    private ContactModel(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new ContactBaseHelper(mContext)
                .getWritableDatabase();
    }

    /**
     * The public GetAll method that handles db contact.
     * @return all ContactBEs from db.
     */
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

    /**
     * Helper method for putting Content Values, for better cohesion.
     * @param contact
     * @return the ContentValues matching the db.
     */
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

    /**
     * Method for Creation of a contact in the db.
     * @param contact
     */
    public void addContact(ContactBE contact) {
        ContentValues values = getContentValues(contact);
        mDatabase.insert(ContactTable.NAME, null, values);
    }

    /**
     * Method for Updating a contact matching the UUID
     * @param contact
     */
    public void updateContact(ContactBE contact) {
        String uuidString = contact.getId().toString();
        ContentValues values = getContentValues(contact);

        // TODO ALH: Refactor to Database :P
        mDatabase.update(ContactTable.NAME, values,
                ContactTable.Cols.UUID + " = ?",
                new String [] {uuidString});
    }

    /**
     * Helper method for getting a CursorWrapper.
     * @param whereClause
     * @param whereArgs
     * @return
     */
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

    /**
     * Method for Deletion of a specific ContactBE
     * @param id
     */
    public void deleteContact(UUID id) {
        mDatabase.delete(ContactTable.NAME,
                ContactTable.Cols.UUID + " = ?",
                new String[] {id.toString()});
    }

}
