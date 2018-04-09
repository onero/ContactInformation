package example.mathias.contactinformation.Controller;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.BLL.PictureUtils;
import example.mathias.contactinformation.Model.ContactModel;
import example.mathias.contactinformation.R;

/**
 * Created by Skovgaard on 28-03-2018.
 */

public class AddContactController implements ICameraEventListener {

    private TextView txtCloseAddContact;
    private Button btnSaveNewContact;
    private ImageView addPicture;
    private EditText addContactName, addPhoneNumber, addEmail, addWebsite, addAddress, addBirthday;

    private Dialog mDialog;
    private ContactRecyclerViewAdapter mAdapter;
    private Context mContext;
    private String mImageLocation;

    public AddContactController(Context context) {

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.add_contact_pop_up);
        mContext = context;

        findViewsByIds();
        setOnClickListeners();
        setInputTypes();
    }

    /**
     * Helper method to set input type of fields
     * and to ensure correct behavior of "enter" button
     */
    private void setInputTypes() {
        //Name
        addContactName.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        addContactName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Phone
        addPhoneNumber.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_CLASS_PHONE);
        addPhoneNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Email
        addEmail.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        addEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Website
        addWebsite.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_URI);
        addWebsite.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Address
        addAddress.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
        addAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    private void setOnClickListeners() {
        // Close
        txtCloseAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        // Save
        btnSaveNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContactBE contact = new ContactBE();

                contact.setName(addContactName.getText().toString());
                contact.setPhoneNumber(addPhoneNumber.getText().toString());
                contact.setMailAddress(addEmail.getText().toString());
                contact.setWebsite(addWebsite.getText().toString());
                contact.setAddress(addAddress.getText().toString());
                contact.setBirthDay(addBirthday.getText().toString());
                if (mImageLocation != null) {
                    contact.setPicture(mImageLocation);
                }

                ContactModel.get(view.getContext()).addContact(contact);
                mAdapter.notifyDataSetChanged();
                mDialog.dismiss();

                Toast.makeText(view.getContext(), "Saved...", Toast.LENGTH_LONG).show();

            }
        });

        // Change picture
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Due to current implementation, camera must be started from ContactListActivity
                ContactListActivity mainContext = (ContactListActivity) mContext;
                mainContext.startCameraActivity();
            }
        });
    }

    private void findViewsByIds() {
        // Button
        btnSaveNewContact = mDialog.findViewById(R.id.btnSaveNewContact);
        // TextView
        txtCloseAddContact = mDialog.findViewById(R.id.txtCloseAddContact);
        // ImageView
        addPicture = mDialog.findViewById(R.id.addPicture);
        // EditText
        addContactName = mDialog.findViewById(R.id.addContactName);
        addPhoneNumber = mDialog.findViewById(R.id.addPhoneNumber);
        addEmail = mDialog.findViewById(R.id.addEmail);
        addWebsite = mDialog.findViewById(R.id.addWebsite);
        addAddress = mDialog.findViewById(R.id.addAddress);
        addBirthday = mDialog.findViewById(R.id.addBirthday);

    }

    /***
     * Displays the pop-up window with information for adding a new contact
     * @param adapter
     */
    public void showAddContactPopUp(ContactRecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    @Override
    public void onContactImageUpdated(String newImageLocation) {
        mImageLocation = newImageLocation;
        Bitmap bitmap = PictureUtils.getScaledBitmap(newImageLocation, 150, 150);
        addPicture.setImageBitmap(bitmap);
    }
}

