package example.mathias.contactinformation.BE;

import android.location.Location;
import android.media.Image;

import java.util.Date;

/**
 * Created by Mathias on 19/03/2018.
 */

public class ContactBE {

    public String mName;
    public String mAddress;
    public Location mLocation;
    public String mPhoneNumber;
    public String mMailAddress;
    public String mWebsite;
    public Date mBirthDay;
    public Image mPicture;

    public ContactBE(String name) {
        mName = name;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
