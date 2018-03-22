package example.mathias.contactinformation.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import example.mathias.contactinformation.BE.ContactBE;
import example.mathias.contactinformation.Model.ContactModel;
import example.mathias.contactinformation.R;

public class ContactListActivity extends AppCompatActivity {

    private ContactModel mContactModel = ContactModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Recyclerview
         */
        setContentView(R.layout.activity_main_recycler);
        final RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final List<ContactBE> contacts = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            contacts.add(new ContactBE("Person: " + i, "00 00 00 0" + i));
        }

        mContactModel.setContacts(contacts);

        /**
         * Incredible recyclerview adapter
         */
        ContactRecyclerViewAdapter adapter = new ContactRecyclerViewAdapter();

        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.contacts_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btn_new_contact:
                newGame();
                return true;
            case R.id.btn_sort_list:
                showHelp();
                return true;
            case R.id.btn_map:
                showHelp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
