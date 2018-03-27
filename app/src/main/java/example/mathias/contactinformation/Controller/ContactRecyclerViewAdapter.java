package example.mathias.contactinformation.Controller;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.Model.ContactModel;
import example.mathias.contactinformation.R;

import static example.mathias.contactinformation.Controller.ContactListActivity.myDialog;

/**
 * Created by Mathias.
 */

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.RecycleHolder> {

    // Variables for assigning data.
    private TextView txtClose, txtName;
    private LinearLayout txtCall, txtSms, txtMail, txtWeb, txtDirection;

    // Stored Model from the outside.
    private ContactModel mContactModel;

    /**
     * Constructor for getting the model from the outside.
     * @param contactModel
     */
    public ContactRecyclerViewAdapter(ContactModel contactModel) {
        mContactModel = contactModel;
    }

    /**
     * Creates the Holder for the RecycleView.
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new RecycleHolder(layoutInflater, parent);
    }

    /**
     * Binds the holder to a ContactBE.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecycleHolder holder, int position) {
        ContactBE contact = mContactModel.getContacts().get(position);
        holder.bind(contact);
    }

    /**
     * Gets the Amount of Contacts from the model.
     * @return
     */
    @Override
    public int getItemCount() {
        return mContactModel.getContacts().size();
    }

    /**
     * Inner class for each row in RecyclerView.
     */
    public class RecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Xml Components.
        private TextView mName, mPhoneNumber;
        private ImageView mImage;

        // The contact to bind.
        private ContactBE mContact;

        /**
         * Constructor for instantiation and inflation of the views.
         * @param inflater
         * @param parent
         */
        public RecycleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.row_item, parent, false));
            itemView.setOnClickListener(this);

            mName = itemView.findViewById(R.id.txtName);
            mImage = itemView.findViewById(R.id.imgView);
            mPhoneNumber = itemView.findViewById(R.id.txtPhone);
        }

        /**
         * Binds the data from the contact to the row.
         * @param contact
         */
        public void bind(ContactBE contact) {
            mContact = contact;
            mName.setText(mContact.getName());
            InputStream imageStream = itemView.getResources().openRawResource(R.raw.monkey);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            mImage.setImageBitmap(bitmap);
            mPhoneNumber.setText(mContact.getPhoneNumber());
        }

        /**
         * Shows the PopUp when row is clicked.
         * @param v
         */
        @Override
        public void onClick(View v) {
            ShowPopUp(v, mContact);
        }
    }

    /**
     * Calls the details popup menu.
     * @param view
     * @param contact
     */
    public void ShowPopUp(View view, ContactBE contact) {

        myDialog.setContentView(R.layout.details_pop_up);
        txtClose = myDialog.findViewById(R.id.txtClose);
        txtCall = myDialog.findViewById(R.id.txtCall);
        txtSms = myDialog.findViewById(R.id.txtSms);
        txtMail = myDialog.findViewById(R.id.txtMail);
        txtWeb = myDialog.findViewById(R.id.txtWeb);
        txtDirection = myDialog.findViewById(R.id.txtDirection);
        txtName = myDialog.findViewById(R.id.txtName);

        txtName.setText(contact.getName());
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });

        // CALL
        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Calling...", Toast.LENGTH_LONG).show();
                Log.d("CALL", "det virker!");
            }
        });

        // SMS
        txtSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "SMSing...", Toast.LENGTH_LONG).show();
                Log.d("CALL", "det virker!");
            }
        });

        // MAILING
        txtMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Mailing...", Toast.LENGTH_LONG).show();
                Log.d("CALL", "det virker!");
            }
        });

        // WEBSITE
        txtWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Visiting website...", Toast.LENGTH_LONG).show();
                Log.d("CALL", "det virker!");
            }
        });

        // GET DIRECTION
        txtDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Getting direction...", Toast.LENGTH_LONG).show();
                Log.d("CALL", "det virker!");
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
