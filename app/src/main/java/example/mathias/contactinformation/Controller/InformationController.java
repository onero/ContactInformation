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

import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.R;

/**
 * Created by Skovgaard on 27-03-2018.
 */

public class InformationController {

    private TextView txtClose, txtName;
    private Button btnSave, btnDelete;
    private ImageView changePicture;
    private EditText editContactName, editPhoneNumber, editEmail, editWebsite, editAddress;
    private Dialog mDialog;

    public InformationController(Context context) {
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.information_pop_up);

        findViewsByIds();
        setOnClickListeners();
    }

    private void findViewsByIds() {
        // Button
        btnSave = mDialog.findViewById(R.id.btnSave);
        btnDelete = mDialog.findViewById(R.id.btnDelete);
        // TextView
        txtClose = mDialog.findViewById(R.id.txtClose);
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

    /**
     * Sets Click Listeners on all the clickable views in the PopUp.
     */
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

        // Delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Deleting...", Toast.LENGTH_LONG).show();
                Log.d("DELETE", "det virker!");

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

    public void showInfo() {
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

}
