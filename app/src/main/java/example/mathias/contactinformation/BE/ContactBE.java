package example.mathias.contactinformation.BE;

import android.location.Location;

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
    private String mBirthDay;
    private String mPicture;

    public ContactBE() {
        this(UUID.randomUUID());
        mName = "";
        mAddress = "";
        mLocation = new Location("");
        mPhoneNumber = "";
        mMailAddress = "";
        mWebsite = "";
        mBirthDay = "";
        mPicture = "";
    }

    /***
     * Overloaded constructor for retrieving contact from DB
     * @param id
     */
    public ContactBE(UUID id) {
        mId = id;
        mName = "";
        mAddress = "";
        mLocation = new Location("");
        mPhoneNumber = "";
        mMailAddress = "";
        mWebsite = "";
        mBirthDay = "";
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
        return mBirthDay;
    }

    public void setBirthDay(String birthDayAsString) {
        mBirthDay = birthDayAsString;
    }

    public UUID getId() {

        return mId;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public void setId(UUID id) {
        mId = id;
    }

    /***
     * Check if the Picture location for the contact has been set
     * @return true if there is a saved location string for the picture, else false
     */
    public boolean hasPicture() {
        return mPicture != null && mPicture.length() > 1;
    }
}
