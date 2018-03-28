package example.mathias.contactinformation.Controller;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import example.mathias.contactinformation.R;

/**
 * Created by Skovgaard on 28-03-2018.
 */

public class AddContactController {

    private TextView txtCloseAddContact;
    private Button btnSaveNewContact;
    private ImageView addPicture;
    private EditText addContactName, addPhoneNumber, addEmail, addWebsite, addAddress;

    private Dialog mDialog;

    public AddContactController(Context context) {

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.add_contact_pop_up);

        findViewsByIds();
        setOnClickListeners();
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
                Toast.makeText(view.getContext(), "Saving...", Toast.LENGTH_LONG).show();
                Log.d("SAVE", "det virker!");

            }
        });

        // Change picture
        addPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Changing picture...", Toast.LENGTH_LONG).show();
                Log.d("Change picture", "det virker!");
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

    }
    public void showAddContactPopUp() {
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

}

