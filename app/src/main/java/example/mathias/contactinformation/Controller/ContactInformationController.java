package example.mathias.contactinformation.Controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.util.Log;
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
 * Created by Skovgaard on 27-03-2018.
 */

public class ContactInformationController {

    private TextView txtClose;
    private Button btnSave, btnDelete;
    private ImageView changePicture;
    private EditText editContactName, editPhoneNumber, editEmail, editWebsite, editAddress, editBirthday;
    private Dialog mDialog;

    private ContactBE mContact;
    private Context mContext;
    private Dialog mOuterDialog;
    private ContactRecyclerViewAdapter mAdapter;
    private ContactActionController mContactActionController;

    public ContactInformationController(Context context) {
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.information_pop_up);

        findViewsByIds();
        setOnClickListeners();
        setInputTypes();
    }

    private void setSelections() {
        // TODO ALH: Remove unused?
        editContactName.setSelection(editContactName.getText().length());
        editPhoneNumber.setSelection(editPhoneNumber.getText().length());
        editEmail.setSelection(editEmail.getText().length());
        editWebsite.setSelection(editWebsite.getText().length());
        editAddress.setSelection(editAddress.getText().length());
    }

    private void setInputTypes() {
        //Name
        editContactName.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        editContactName.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Phone
        editPhoneNumber.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_CLASS_PHONE);
        editPhoneNumber.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Email
        editEmail.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editEmail.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Website
        editWebsite.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_URI);
        editWebsite.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //Address
        editAddress.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_POSTAL_ADDRESS);
        editAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    private void findViewsByIds() {
        // Button
        btnSave = mDialog.findViewById(R.id.btnSave);
        btnDelete = mDialog.findViewById(R.id.btnDelete);
        // TextView
        txtClose = mDialog.findViewById(R.id.txtClose);
        // ImageView
        changePicture = mDialog.findViewById(R.id.changePicture);
        // EditText
        editContactName = mDialog.findViewById(R.id.editContactName);
        editPhoneNumber = mDialog.findViewById(R.id.editPhoneNumber);
        editEmail = mDialog.findViewById(R.id.editEmail);
        editWebsite = mDialog.findViewById(R.id.editWebsite);
        editAddress = mDialog.findViewById(R.id.editAddress);
        editBirthday = mDialog.findViewById(R.id.editBirthday);
    }

    /**
     * Sets Click Listeners on all the clickable views in the PopUp.
     */
    private void setOnClickListeners() {

        // Close
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeInformationController();
            }
        });

        // Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContactBE contact = new ContactBE(mContact.getId());

                contact.setName(editContactName.getText().toString());
                contact.setPhoneNumber(editPhoneNumber.getText().toString());
                contact.setMailAddress(editEmail.getText().toString());
                contact.setWebsite(editWebsite.getText().toString());
                contact.setAddress(editAddress.getText().toString());
                contact.setBirthDay(editBirthday.getText().toString());
                if (mContact.hasPicture()) {
                    contact.setPicture(mContact.getPicture());
                } else {
                    contact.setPicture("");
                }

                ContactModel.get(view.getContext()).updateContact(contact);
                mContactActionController.updateContactInformation(contact);
                mAdapter.notifyDataSetChanged();

                Toast.makeText(view.getContext(), "Saved!", Toast.LENGTH_LONG).show();
            }
        });

        // Delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert(view.getContext());
            }
        });

        // Change picture
        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO ALH: Implement!
                Toast.makeText(view.getContext(), "Changing picture...", Toast.LENGTH_LONG).show();
                Log.d("Change picture", "det virker!");
            }
        });


    }

    private void alert(Context context) {
        mContext = context;
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_DARK);
        mBuilder.setIcon(android.R.drawable.ic_menu_delete);
        mBuilder.setTitle(R.string.title_delete);
        mBuilder.setMessage(R.string.message_delete);
        mBuilder.setCancelable(false);

        // Cancel
        mBuilder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        // Yes
        mBuilder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteContact(mContext);
                mAdapter.notifyDataSetChanged();
                dialogInterface.dismiss();
                closeInformationController();
                mOuterDialog.dismiss();
            }
        });


        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }

    public void showInfo(ContactBE contact, Dialog outerDialog, ContactRecyclerViewAdapter adapter, ContactActionController contactActionController) {
        mContactActionController = contactActionController;
        mAdapter = adapter;
        if (contact != null) {
            mContact = contact;
            setContactInformation();

        }
        mOuterDialog = outerDialog;
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    private void setContactInformation() {
        editContactName.setText(mContact.getName());
        editAddress.setText(mContact.getAddress());
        editEmail.setText(mContact.getMailAddress());
        editPhoneNumber.setText(mContact.getPhoneNumber());
        editWebsite.setText(mContact.getWebsite());
        editBirthday.setText(mContact.getBirthDay());
        if (mContact.hasPicture()) {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mContact.getPicture(), 150, 150);
            changePicture.setImageBitmap(bitmap);
        }
        setSelections();
    }

    private void deleteContact(Context context) {
        ContactModel.get(context).deleteContact(mContact.getId());
    }

    private void closeInformationController() {
        mDialog.dismiss();
    }
}
