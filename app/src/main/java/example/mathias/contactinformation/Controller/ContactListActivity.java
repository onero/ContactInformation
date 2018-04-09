package example.mathias.contactinformation.Controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import example.mathias.contactinformation.Model.ContactModel;
import example.mathias.contactinformation.R;

public class ContactListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_CAMERA_ADD = 1;
    private final String DIRECTORY = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES) + "/ContactInformation";


    // Toolbar title
    private static final String TOOLBAR_TITLE = "Contacts";

    // Stored Contact RecyclerViewAdapter.
    private ContactRecyclerViewAdapter adapter;

    // Reference to Contact image created
    private File mPhotoFile;

    // Stored RecyclerView.
    private RecyclerView recyclerView;


    // Listeners for camera specific events
    private List<ICameraEventListener> mCameraEventListeners;

    /**
     * Instanciation of Context, Dialog, RecyclerView and RecyclerViewAdapter.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();

        // Sets the toolbar.
        actionBar.setTitle(TOOLBAR_TITLE);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.blueDarker)));

        // Recyclerview
        setContentView(R.layout.activity_main_recycler);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Incredible recyclerview adapter
        adapter = new ContactRecyclerViewAdapter(this, ContactModel.get(this));
        recyclerView.setAdapter(adapter);
    }

    /**
     * When starting up the application create the menu.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_list, menu);
        return true;
    }

    /**
     * Switch for our menu so it is easy to add more later on.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btn_new_contact:
                addContact();
                return true;
//            case R.id.btn_sort_list:
//                // TODO ALH: Consider implementation?
//                return true;
            case R.id.btn_map:
                startMap();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * When result from activity gets back
     * @param requestCode
     * @param resultCode
     * @param data from the intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_CODE_CAMERA_ADD:
                if (mPhotoFile.exists()) {
                    if (mCameraEventListeners != null) {
                        // For each listener we have for camera events
                        for (ICameraEventListener listener : mCameraEventListeners) {
                            // Inform them of the updated picture!
                            listener.onContactImageUpdated(mPhotoFile.getPath());
                        }
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    /***
     * Start the camera activity
     * Remarks: Due to current implementation, the camera must be started from this controller
     */
    public void startCameraActivity() {
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPhotoFile = new File(DIRECTORY, "IMG " + UUID.randomUUID().toString() + ".jpg");
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        startActivityForResult(captureImage, REQUEST_CODE_CAMERA_ADD);
    }

    /***
     * Start the camera activity with pre-defined image location
     * Remarks: Due to current implementation, the camera must be started from this controller
     */
    public void startCameraActivity(String imageLocation) {
        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mPhotoFile = new File(imageLocation);
        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhotoFile));
        startActivityForResult(captureImage, REQUEST_CODE_CAMERA_ADD);
    }

    /**
     * Add listener
     *
     * @param listener
     */
    public void setCameraEventListener(ICameraEventListener listener) {
        // Ensure the list is created
        if (mCameraEventListeners == null) {
            mCameraEventListeners = new ArrayList<>();
        }
        // Check if listener is already in list
        if (!mCameraEventListeners.contains(listener)) {
            mCameraEventListeners.add(listener);
        }
    }

    /**
     * Add a Contact.
     */
    private void addContact() {
        AddContactController addContactController = new AddContactController(this);
        addContactController.showAddContactPopUp(adapter);
        setCameraEventListener(addContactController);
    }

    /**
     * Starts up a new Activity for maps.
     */
    private void startMap() {
        Intent intent = new Intent(ContactListActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}
