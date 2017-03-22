package me.blog.korn123.utils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import org.apache.commons.lang3.*;

import java.util.ArrayList;
import java.util.List;

import me.blog.korn123.awesomemanager.R;
import me.blog.korn123.awesomemanager.team.MemberAddStep2Activity;

/**
 * Created by CHO HANJOONG on 2016-10-18.
 */

public class ContactsViewActivity extends Activity {

    private ListView mListViewContact;
    private ArrayAdapter<ContactDto> mArrayAdapter;
    private List<ContactDto> mListContactDto;
    private SearchView mContactSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_view_content);
        mListViewContact = (ListView) findViewById(R.id.listContact);
        bindSearchViewEvent();
        refreshList(null);
    }

    private void bindSearchViewEvent() {
        mContactSearchView = (SearchView) findViewById(R.id.contactSearchView);
        mContactSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                refreshList(keyword);
                return false;
            }
        });
    }

    public List<ContactDto> selectContactBy(String name) {
        List<ContactDto> listContactDto = new ArrayList<>();
        String[] mSelectionArgs = {"%" + name + "%"};
        Cursor phones = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE ?",
                mSelectionArgs,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC");
        while (phones.moveToNext()) {
            String displayName = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            ContactDto contactDto = new ContactDto(
                    displayName,
                    phoneNumber
            );
            listContactDto.add(contactDto);
        }
        phones.close();
        return listContactDto;
    }

    public void refreshList(String keyword) {
        if (mListContactDto != null) {
            mListContactDto.clear();
            mListContactDto.addAll(selectContactBy(keyword));
            mArrayAdapter.notifyDataSetChanged();
        } else {
            mListContactDto = new ArrayList<>();
            mListContactDto = selectContactBy("");
            mArrayAdapter = new ArrayAdapter<ContactDto>(this, android.R.layout.simple_list_item_1, mListContactDto);
            mListViewContact.setAdapter(mArrayAdapter);
            mListViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ContactDto contactDto = (ContactDto) parent.getAdapter().getItem(position);
                    CommonUtils.makeSnackBar(ContactsViewActivity.this, contactDto.toString());
                    getIntent().putExtra("phoneNumber", org.apache.commons.lang3.StringUtils.replace(contactDto.getPhoneNumber(), "-", ""));
                    setResult(RESULT_OK, getIntent());
                    finish();
                }
            });
        }
    }

    public class ContactDto {
        String displayName;
        String phoneNumber;

        public ContactDto(String displayName, String phoneNumber) {
            this.displayName = displayName;
            this.phoneNumber = phoneNumber;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public String toString() {
            return displayName + "\n" + phoneNumber;
        }
    }

}
