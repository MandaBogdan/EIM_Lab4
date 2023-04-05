package ro.pub.cs.systems.eim.lab03.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private Button showAdditional;
    private Button save;
    private Button cancel;
    LinearLayout additionaLayout;
    private EditText nameT;
    private EditText telT;
    private EditText emailT;
    private EditText addressT;
    private EditText companyT;
    private EditText jobT;
    private EditText messT;
    private EditText websiteT;

    private MasterButtonClickListener buttonClickListener = new MasterButtonClickListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        showAdditional = (Button)findViewById(R.id.show_fields);
        save = (Button)findViewById(R.id.save);
        cancel = (Button)findViewById(R.id.cancel);
        nameT = (EditText)findViewById(R.id.editName);
        emailT = (EditText)findViewById(R.id.editEmail);
        telT = (EditText)findViewById(R.id.editNumber);
        addressT = (EditText)findViewById(R.id.editAddress);
        companyT = (EditText)findViewById(R.id.editCompany);
        jobT = (EditText)findViewById(R.id.editJob);
        messT = (EditText)findViewById(R.id.editMes);
        websiteT = (EditText)findViewById(R.id.editWeb);
        additionaLayout = (LinearLayout) findViewById(R.id.AditionalLayout);
        additionaLayout.setVisibility(View.INVISIBLE);

        showAdditional.setOnClickListener(buttonClickListener);
        save.setOnClickListener(buttonClickListener);
        cancel.setOnClickListener(buttonClickListener);
    }

    private class MasterButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int ID = view.getId();
            if (ID == R.id.show_fields) {
                if (additionaLayout.getVisibility() == View.INVISIBLE) {
                    showAdditional.setText(R.string.hide_additional_fields);
                    additionaLayout.setVisibility(View.VISIBLE);
                } else {
                    showAdditional.setText(R.string.show_additional_fields);
                    additionaLayout.setVisibility(View.INVISIBLE);
                }
            } else if (ID == R.id.save) {
                String name = nameT.getText().toString();
                String phone = telT.getText().toString();
                String email = emailT.getText().toString();
                String address = addressT.getText().toString();
                String jobTitle = jobT.getText().toString();
                String company = companyT.getText().toString();
                String website = websiteT.getText().toString();
                String im = messT.getText().toString();
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            } else {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    }
}