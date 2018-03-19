package example.mathias.contactinformation.Controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        for (int i = 1; i <= 10; i++) {
            contacts.add(new ContactBE("Person: " + i));
        }

        mContactModel.setContacts(contacts);

        /**
         * Incredible recyclerview adapter
         */
        ContactRecyclerViewAdapter adapter = new ContactRecyclerViewAdapter();

        recyclerView.setAdapter(adapter);
    }
}
