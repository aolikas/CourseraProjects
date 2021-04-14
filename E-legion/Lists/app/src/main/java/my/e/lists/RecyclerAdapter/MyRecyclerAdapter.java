package my.e.lists.RecyclerAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.e.lists.Models.Item;
import my.e.lists.R;
import my.e.lists.RecyclerAdapter.ImageViewHolder;
import my.e.lists.RecyclerAdapter.ItemViewHolder;

public class MyRecyclerAdapter extends RecyclerView.Adapter {

    private List<Object> mDataList;

    public static final int TYPE_ITEM = 0;
    public static final int TYPE_IMAGE = 1;

    public MyRecyclerAdapter(List<Object> dataList) {
        this.mDataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_ITEM:
                View layoutOne = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_type_one, parent, false);
                return new ItemViewHolder(layoutOne);

            case TYPE_IMAGE:
                View layoutTwo = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_type_two, parent, false);
                return new ImageViewHolder(layoutTwo);

            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_ITEM:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                configureItemViewHolder(itemViewHolder, position);
                break;
            case TYPE_IMAGE:
                ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
                configureImageViewHolder(imageViewHolder, position);
                break;
            default:
                return;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData(position);
            }
        });
    }

    private void deleteData(int position) {
        mDataList.remove(position);
        notifyDataSetChanged();
    }

    public void addData(Context context, int type) {
        switch (type) {
            case TYPE_ITEM:
                mDataList.add(new Item("Title", "Description"));
                notifyItemInserted(mDataList.size());
                break;
            case TYPE_IMAGE:
                mDataList.add("image");
                notifyItemInserted(mDataList.size());
                break;
            default:
                break;
        }
    }


    private void configureItemViewHolder(ItemViewHolder holder, int position) {
        Item item = (Item) mDataList.get(position);
        if (item != null) {
            holder.getTitle().setText("This is a title");
            holder.getDescription().setText("This is a description");
        }
    }

    private void configureImageViewHolder(ImageViewHolder holder, int position) {
        holder.getImage().setImageResource(R.drawable.image);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataList.get(position) instanceof Item) {
            return TYPE_ITEM;
        } else if (mDataList.get(position) instanceof String) {
            return TYPE_IMAGE;
        }
        return -1;
    }
}
