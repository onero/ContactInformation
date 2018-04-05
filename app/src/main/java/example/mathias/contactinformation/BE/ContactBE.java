package example.mathias.contactinformation.BE;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by Mathias on 19/03/2018.
 */

public class ContactBE {

    private UUID mId;
    private String mName;
    private String mAddress;
    private Location mLocation;
    private String mPhoneNumber;
    private String mMailAddress;
    private String mWebsite;
    private Date mBirthDay;
    private SimpleDateFormat mDateFormat;
    private String mPicture;

    public ContactBE() {
        this(UUID.randomUUID());
        mName = "";
        mAddress = "";
        mLocation = new Location("");
        mPhoneNumber = "";
        mMailAddress = "";
        mWebsite = "";
        mBirthDay = new Date();
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        mPicture = "";
    }

    public ContactBE(UUID id) {
        mId = id;
        mName = "";
        mAddress = "";
        mLocation = new Location("");
        mPhoneNumber = "";
        mMailAddress = "";
        mWebsite = "";
        mBirthDay = new Date();
        mDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        mPicture = "";
}

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getLocation() {
        return mLocation.toString();
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public String getMailAddress() {
        return mMailAddress;
    }

    public void setMailAddress(String mailAddress) {
        mMailAddress = mailAddress;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getBirthDay() {
        return mBirthDay.toString();
    }

    public void setBirthDay(String birthDay) {}

    public SimpleDateFormat getDateFormat() {
        return mDateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        mDateFormat = dateFormat;
    }

    public String getPicture() {
        return "IMG " + getId().toString() + ".jpg";
    }

    public UUID getId() {

        return mId;
    }

    public void setId(UUID id) {
        mId = id;
    }
}
