package example.mathias.contactinformation.Controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import example.mathias.contactinformation.Model.ContactModel;
import example.mathias.contactinformation.R;

public class ContactListActivity extends AppCompatActivity {

    // Toolbar title
    private static final String TOOLBAR_TITLE = "Contacts";

    // Stored Context.
    private Context mContext;

    // Stored Contact RecyclerViewAdapter.
    private ContactRecyclerViewAdapter adapter;

    // Stored RecyclerView.
    private RecyclerView recyclerView;

    private AddContactController mAddContactController;

    ActionBar mActionBar;

    /**
     * Instanciation of Context, Dialog, RecyclerView and RecyclerViewAdapter.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;

        super.onCreate(savedInstanceState);

        mActionBar = getSupportActionBar();

        // Sets the toolbar.
        mActionBar.setTitle(TOOLBAR_TITLE);
        // TODO ALH: Skovgaard 'R.color......'
        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1e2028")));

        // Recyclerview
        setContentView(R.layout.activity_main_recycler);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // Incredible recyclerview adapter
        adapter = new ContactRecyclerViewAdapter(ContactModel.get(mContext));
        recyclerView.setAdapter(adapter);
    }

    /**
     * When starting up the application create the menu.
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
            case R.id.btn_sort_list:
                // TODO ALH: Consider implementation?
                return true;
            case R.id.btn_map:
                map();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Add a Contact.
     */
    private void addContact() {

        mAddContactController = new AddContactController(this);
        mAddContactController.showAddContactPopUp(adapter);
    }

    /**
     * Starts up a new Activity for maps.
     */
    private void map() {
        Intent intent = new Intent(ContactListActivity.this, MapsActivity.class);
        startActivity(intent);
    }
}
