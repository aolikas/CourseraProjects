package my.e.lists.RecyclerAdapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.e.lists.R;

public class ImageViewHolder extends RecyclerView.ViewHolder {
    private ImageView image;

    public ImageViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.type_two_image);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }
}
