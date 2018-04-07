package example.mathias.contactinformation.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.BLL.PictureUtils;
import example.mathias.contactinformation.Model.ContactModel;
import example.mathias.contactinformation.R;


/**
 * Created by Mathias.
 */

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.RecycleHolder> {

    // Stored Model from the outside.
    private ContactModel mContactModel;
    private ContactRecyclerViewAdapter mAdapter;

    /**
     * Constructor for getting the model from the outside.
     *
     * @param contactModel
     */
    public ContactRecyclerViewAdapter(ContactModel contactModel) {
        mContactModel = contactModel;
        mAdapter = this;
    }

    /**
     * Creates the Holder for the RecycleView.
     *
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
     *
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
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mContactModel.getContacts().size();
    }

    /**
     * Inner class for each row in RecyclerView.
     */
    public class RecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Xml Components.
        private TextView mName, mPhoneNumber;
        private ImageView mImage;

        // The contact to bind.
        private ContactBE mContact;

        /**
         * Constructor for instantiation and inflation of the views.
         *
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
         *
         * @param contact
         */
        public void bind(ContactBE contact) {
            mContact = contact;
            mName.setText(mContact.getName());
            Bitmap bitmap;
            // Check for contact image
            if (contact.hasPicture()) {
                bitmap = PictureUtils.getScaledBitmap(contact.getPicture(), 150, 150);
            } else {
                // Insert nice monkey!
                InputStream imageStream = itemView.getResources().openRawResource(R.raw.monkey);
                bitmap = BitmapFactory.decodeStream(imageStream);
            }
            mImage.setImageBitmap(bitmap);
            mPhoneNumber.setText(mContact.getPhoneNumber());
        }

        /**
         * Shows the PopUp when row is clicked.
         *
         * @param
         */
        @Override
        public void onClick(View view) {
            ContactActionController contactActionController = new ContactActionController(view.getContext());
            contactActionController.showContactActionPopUp(mContact, mAdapter);
        }
    }
}
