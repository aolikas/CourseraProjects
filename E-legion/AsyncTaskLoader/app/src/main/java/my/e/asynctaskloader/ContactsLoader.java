package my.e.asynctaskloader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;



import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class ContactsLoader extends AsyncTaskLoader<String> {

    public static final String ID = "id";
    public static final String TAG = "ContactsLoader";
    private String mId;
    private boolean isCancelled;
    private WeakReference<Context> mContext;

    public ContactsLoader(Context context, String id) {
        super(context);
        mContext = new WeakReference<>(context);
        mId = id;
        Log.d(TAG, "Constructor id " + mId);
    }

    @Override
    public String loadInBackground() {

        String number = "";

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Context context = mContext.get();
        if (context == null) return null;

        Cursor cursor = getContext().getContentResolver()
                .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND "
                                + ContactsContract.CommonDataKinds.Phone.TYPE + " = ?",
                        new String[]{mId, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                        null);

        if (cursor != null && cursor.moveToFirst()) {
            number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            cursor.close();
        }

        return number;
    }

    /**
     public void setId(String id) {
     mId = id;
     }
     */

    public boolean isCancelled() {
        return isCancelled;
    }


    @Override
    protected void onStartLoading() {
        isCancelled = false;
        if(mId == null) return;
        forceLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        isCancelled = false;
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    public void onCanceled(String data) {
        super.onCanceled(data);
        isCancelled = true;
        deliverResult(null);
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
    }

}
