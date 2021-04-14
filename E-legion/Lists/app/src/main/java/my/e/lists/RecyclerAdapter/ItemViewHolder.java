package my.e.lists.RecyclerAdapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.e.lists.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView title, description;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.type_one_title);
        description = itemView.findViewById(R.id.type_one_description);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(TextView description) {
        this.description = description;
    }
}
