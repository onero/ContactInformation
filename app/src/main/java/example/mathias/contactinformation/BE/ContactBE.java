package example.mathias.contactinformation.BE;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.media.Image;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import example.mathias.contactinformation.R;

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
    private SimpleDateFormat mDateFormat, mTimeFormat;
    private String mPicture;

    public ContactBE() {
        this(UUID.randomUUID());
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
        mTimeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
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

    public void setLocation(String location) {
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

    public void setBirthDay(String birthDay) {
    }

    public SimpleDateFormat getDateFormat() {
        return mDateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        mDateFormat = dateFormat;
    }

    public SimpleDateFormat getTimeFormat() {
        return mTimeFormat;
    }

    public void setTimeFormat(SimpleDateFormat timeFormat) {
        mTimeFormat = timeFormat;
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
