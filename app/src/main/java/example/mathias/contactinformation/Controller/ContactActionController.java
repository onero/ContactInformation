package example.mathias.contactinformation.Controller;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.BLL.PictureUtils;
import example.mathias.contactinformation.R;

/**
 * Created by Mathias on 27/03/2018.
 */

public class ContactActionController {

    private TextView txtClose, txtName, txtInfo;
    private LinearLayout txtCall, txtSms, txtMail, txtWeb, txtDirection;
    private ImageView imgContact;
    private Dialog mDialog;
    private ContactInformationController info;
    private ContactBE mContact;
    private ContactRecyclerViewAdapter mAdapter;
    private ContactActionController mContactActionController;
    private Context mContext;
    private Intent mIntent;

    public ContactActionController(Context context) {

        mContext = context;
        mContactActionController = this;
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.details_pop_up);

        findViewsByIds();
        setOnClickListeners();
    }

    private void findViewsByIds() {
        txtClose = mDialog.findViewById(R.id.txtClose);
        txtCall = mDialog.findViewById(R.id.txtCall);
        txtSms = mDialog.findViewById(R.id.txtSms);
        txtMail = mDialog.findViewById(R.id.txtMail);
        txtWeb = mDialog.findViewById(R.id.txtWeb);
        txtDirection = mDialog.findViewById(R.id.txtDirection);
        txtName = mDialog.findViewById(R.id.txtName);
        txtInfo = mDialog.findViewById(R.id.txtInfo);
        imgContact = mDialog.findViewById(R.id.imgContact);
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
            }
        });

        // INFORMATION
        txtInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info = new ContactInformationController(view.getContext());
                info.showInfo(mContact, mDialog, mAdapter, mContactActionController);
            }
        });

        // CALL
        txtCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callContact();
            }
        });

        // SMS
        txtSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                smsContact();
            }
        });

        // MAILING
        txtMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mailingContact();
            }
        });

        // WEBSITE
        txtWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                websiteOfContact();
            }
        });

        // GET DIRECTION
        txtDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                directionToContact();

            }
        });
    }

    /**
     * Show a marker where the selected contact lives and get the direction.
     */
    private void directionToContact() {
        Intent mapIntent = MapsActivity.newIntent(mContext, mContact.getName(), mContact.getAddress());
        mContext.startActivity(mapIntent);
    }

    /**
     * Visiting the website of the contact.
     */
    private void websiteOfContact() {
        // Uri addresses must be preceded by 'http://'...
        Uri website = Uri.parse("http://" + mContact.getWebsite().toString());
        mIntent = new Intent(Intent.ACTION_VIEW, website);
        mContext.startActivity(mIntent);
    }

    /**
     * Mailing the contact.
     */
    private void mailingContact() {
        mIntent = new Intent(Intent.ACTION_SEND);
        mIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mContact.getMailAddress().toString()});

        // This sets the MIME type of your intent.
        // Since you aren't supposed to force any kind of behavior.
        mIntent.setType("message/rfc822");

        mContext.startActivity(Intent.createChooser(mIntent, "Choose email client..."));
    }

    /**
     * SMS the contact
     */
    private void smsContact() {

        mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mContact.getPhoneNumber()));

        mContext.startActivity(mIntent);
    }

    /**
     * Dialing up the contact.
     */
    private void callContact() {

        mIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mContact.getPhoneNumber()));

        mContext.startActivity(mIntent);

    }

    public void showContactActionPopUp(ContactBE contact, ContactRecyclerViewAdapter adapter) {
        mAdapter = adapter;
        mContact = contact;
        txtName.setText(contact.getName());
        if (mContact.hasPicture()) {
            Bitmap bitmap = PictureUtils.getScaledBitmap(mContact.getPicture(), 150, 150);
            imgContact.setImageBitmap(bitmap);
        }
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();
    }

    public void updateContactInformation(ContactBE contact) {
        mContact = contact;
        txtName.setText(mContact.getName());
    }
}
