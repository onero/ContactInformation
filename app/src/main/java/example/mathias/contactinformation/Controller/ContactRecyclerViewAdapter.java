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

    private TextView txtClose, txtCall, txtSms, txtMail, txtWeb, txtDirection;

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
            ShowPopUp(v);
        }
    }


    // Calls the details popup menu.
    public void ShowPopUp(View view) {
        myDialog.setContentView(R.layout.details_pop_up);
        txtClose = myDialog.findViewById(R.id.txtClose);
        txtCall = myDialog.findViewById(R.id.txtCall);
        txtSms = myDialog.findViewById(R.id.txtSms);
        txtMail = myDialog.findViewById(R.id.txtMail);
        txtWeb = myDialog.findViewById(R.id.txtWeb);
        txtDirection = myDialog.findViewById(R.id.txtDirection);

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
