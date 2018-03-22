package example.mathias.contactinformation.Model;

import java.util.ArrayList;
import java.util.List;

import example.mathias.contactinformation.BE.ContactBE;

/**
 * Created by Mathias on 19/03/2018.
 */

public class ContactModel {

    private static final ContactModel instance = new ContactModel();

    private List<ContactBE> mContacts;

    public static ContactModel getInstance() {
        return instance;
    }

    private ContactModel() {
        mContacts = new ArrayList<>();
    }

    public List<ContactBE> getContacts() {
        return mContacts;
    }

    public void setContacts(List<ContactBE> contact) {
        mContacts = contact;
    }
    public void addContact(ContactBE contact) {
        mContacts.add(contact);
    }
}
