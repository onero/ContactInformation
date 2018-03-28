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

    private TextView txtClose, txtName;
    private Button btnSave;
    private ImageView changePicture;
    private EditText editContactName, editPhoneNumber, editEmail, editWebsite, editAddress;

    private Dialog mDialog;

    public AddContactController(Context context) {

        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.add_contact_pop_up);

        findViewsByIds();
//        setOnClickListeners();
    }

    private void setOnClickListeners() {

        // Close
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                Log.d("CLOSE", "det virker!");
            }
        });

        // Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Saving...", Toast.LENGTH_LONG).show();
                Log.d("SAVE", "det virker!");

            }
        });

        // Change picture
        changePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Changing picture...", Toast.LENGTH_LONG).show();
                Log.d("Change picture", "det virker!");
            }
        });
    }

    private void findViewsByIds() {

        // Button
        btnSave = mDialog.findViewById(R.id.btnSave);
        // TextView
        txtName = mDialog.findViewById(R.id.txtName);
        // ImageView
        changePicture = mDialog.findViewById(R.id.changePicture);
        // EditText
        editContactName = mDialog.findViewById(R.id.editContactName);
        editPhoneNumber = mDialog.findViewById(R.id.editPhoneNumber);
        editEmail = mDialog.findViewById(R.id.editEmail);
        editWebsite = mDialog.findViewById(R.id.editWebsite);
        editAddress = mDialog.findViewById(R.id.editAddress);

    }
    public void showAddContactPopUp() {
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

}

