package my.e.lists;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private List<Model> mModelList;

    public RecyclerAdapter(List<Model> modelList) {
        this.mModelList = modelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case Model.TYPE_ONE:
                View layoutOne = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_type_1, parent, false);
                return new LayoutFirstViewHolder(layoutOne);
            case Model.TYPE_TWO:
                View layoutTwo = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_type_2, parent, false);
                return new LayoutSecondViewHolder(layoutTwo);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (mModelList.get(position).getViewType()) {
            case Model.TYPE_ONE:
                int icon = mModelList.get(position).getIconFirstLayout();
                String textOne = mModelList.get(position).getTextFirstLayout();
                ((LayoutFirstViewHolder) holder).setFirstLayoutViews(icon, textOne);
                ((LayoutFirstViewHolder) holder).linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "This is a first layout",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            case Model.TYPE_TWO:
                String textTwo = mModelList.get(position).getTextSecondLayout();
                ((LayoutSecondViewHolder) holder).setSecondLayoutViews(textTwo);
                ((LayoutSecondViewHolder) holder).linearLayout.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(view.getContext(), "This is a second layout",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;

            default:
                return;
        }
    }
public void addTypeOne(List<Model> models) {
        models.addAll(models);
}

    @Override
    public int getItemCount() {
        return mModelList.size();
    }

    // This method uses a switch statement
    // to assign the layout to each item
    // depending on the viewType passed
    @Override
    public int getItemViewType(int position) {
        switch (mModelList.get(position).getViewType()) {
            case 0:
                return Model.TYPE_ONE;
            case 1:
                return Model.TYPE_TWO;
            default:
                return -1;
        }
    }

    // ViewHolder for a first layout
    class LayoutFirstViewHolder extends RecyclerView.ViewHolder {

        private ImageView mIcon;
        private TextView mText;
        private LinearLayout linearLayout;

        public LayoutFirstViewHolder(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.item_one_image);
            mText = itemView.findViewById(R.id.item_one_text);
            linearLayout = itemView.findViewById(R.id.linear_layout_one);
        }

        private void setFirstLayoutViews(int image, String text) {
            mIcon.setImageResource(image);
            mText.setText(text);
        }
    }

    // ViewHolder for a second layout
    class LayoutSecondViewHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private LinearLayout linearLayout;

        public LayoutSecondViewHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.item_two_text);
            linearLayout = itemView.findViewById(R.id.linear_layout_two);
        }

        private void setSecondLayoutViews(String text) {
            mText.setText(text);
        }
    }
}
