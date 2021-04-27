package my.e.asynctaskloader;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import my.e.asynctaskloader.mock.Mock;
import my.e.asynctaskloader.mock.MockHolder;

public class ContactsAdapter extends RecyclerView.Adapter<MockHolder>{

    private Cursor mCursor;
    private OnItemClickListener mListener;

    @Override
    public MockHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.li_mock, parent, false);
        return new MockHolder(view);
    }

    @Override
    public void onBindViewHolder(MockHolder holder, int position) {
        if(mCursor.moveToPosition(position)){
            String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int id = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
            holder.bind(new Mock(name, id));
            holder.setListener(mListener);
        }
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    public void swapCursor(Cursor cursor) {
        if (cursor != null && cursor != mCursor) {
            if (mCursor != null) mCursor.close();
            mCursor = cursor;
            notifyDataSetChanged();
        }

    }

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(String id);
    }

}
