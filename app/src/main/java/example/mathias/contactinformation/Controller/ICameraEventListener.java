package example.mathias.contactinformation.Controller;

/**
 * Created by Adamino.
 */
public interface ICameraEventListener {
    /***
     * Inform about updated contact image
     * @param newImageLocation
     * @return path of new image
     */
    void onContactImageUpdated(String newImageLocation);
}
