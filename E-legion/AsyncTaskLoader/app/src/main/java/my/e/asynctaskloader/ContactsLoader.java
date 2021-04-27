package my.e.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;


import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class ContactsLoader extends AsyncTaskLoader<String> {

    public static final String ID = "id";
    public static final String TAG = "ContactsLoader";
    private final String mId;


    public ContactsLoader(Context context, String id) {
        super(context);
        this.mId = id;
        Log.d(TAG, "Constructor id " + mId);
    }

    @Override
    public String loadInBackground() {
        try {
            TimeUnit.SECONDS.sleep(2);
            Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                            + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                    new String[]{mId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                    null);

            if (cursor != null && cursor.moveToFirst()) {
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                cursor.close();

                return number;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
        Toast.makeText(getContext(),
                "Cancelled", Toast.LENGTH_SHORT).show();
    }
}
