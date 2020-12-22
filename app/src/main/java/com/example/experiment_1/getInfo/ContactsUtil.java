package com.example.experiment_1.getInfo;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * @author ylqq
 */
public class ContactsUtil {
    public static ArrayList<MyContacts> getContactInfo(Context context){
        ContentResolver resolver=context.getContentResolver();
        ArrayList<MyContacts> myContactsArrayList=new ArrayList<>();
        Cursor cursor=resolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()){
            String contactId=cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            String name=cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phone=cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
            myContactsArrayList.add(
                    new MyContacts(contactId,name,phone)
            );
        }
        cursor.close();
        return myContactsArrayList;
    }
}
