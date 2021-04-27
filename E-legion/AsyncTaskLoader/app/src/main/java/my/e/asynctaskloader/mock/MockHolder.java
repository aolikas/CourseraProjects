package my.e.asynctaskloader.mock;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import my.e.asynctaskloader.ContactsAdapter;
import my.e.asynctaskloader.R;

public class MockHolder extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mValue;
    private String mId;

    public MockHolder(View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.tv_name);
        mValue = itemView.findViewById(R.id.tv_value);
    }

    public void bind(Mock mock) {
        mName.setText(mock.getName());
        mValue.setText(mock.getValue());
        mId = mock.getValue();
    }

    public void setListener(final ContactsAdapter.OnItemClickListener listener) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(mId);
            }
        });
    }

}
