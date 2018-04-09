package example.mathias.contactinformation.BLL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Adamino.
 */
public class PictureUtils {

    /***
     * Return Bitmap, scaled to the specific requirements of provided width and height
     * @param path of image to scale
     * @param destWidth of new Bitmap
     * @param destHeight of new Bitmap
     * @return scaled Bitmap
     */
    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        // Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        // Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight > destHeight || srcWidth > destWidth)

        {
            float heightScale = srcHeight / destHeight;
            float widthScale = srcWidth / destWidth;
            inSampleSize = Math.round(heightScale > widthScale ? heightScale : widthScale);
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }

}
