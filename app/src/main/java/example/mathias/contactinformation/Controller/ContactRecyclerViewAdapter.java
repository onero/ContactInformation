package example.mathias.contactinformation.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.Model.ContactModel;
import example.mathias.contactinformation.R;

/**
 * Created by Mathias.
 */

public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.RecycleHolder> {
    private ContactModel mContactModel = ContactModel.getInstance();

    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new RecycleHolder(layoutInflater, parent);
    }

    @Override
    public void onBindViewHolder(RecycleHolder holder, int position) {
        ContactBE contact = mContactModel.getContacts().get(position);
        holder.bind(contact);
    }

    @Override
    public int getItemCount() {
        return mContactModel.getContacts().size();
    }

    public class RecycleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mName;
        private ImageView mImage;
        private TextView mPhoneNumber;

        private ContactBE mContact;

        public RecycleHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.row_item, parent, false));
            itemView.setOnClickListener(this);

            mName = itemView.findViewById(R.id.txtName);
            mImage = itemView.findViewById(R.id.imgView);
            mPhoneNumber = itemView.findViewById(R.id.txtPhone);
        }

        public void bind(ContactBE contact) {
            mContact = contact;
            mName.setText(mContact.getName());
            InputStream imageStream = itemView.getResources().openRawResource(R.raw.monkey);
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            mImage.setImageBitmap(bitmap);
            mPhoneNumber.setText(mContact.getPhoneNumber());
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), mContact.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
