package dev.xalpol12.wheretoeat.viewmodel.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {

    private final Context context;
    private final RecyclerViewInterface recyclerViewInterface;
    private final LiveData<List<PlaceEntity>> savedPlaces;

    public PlaceAdapter(Context context,
                        LiveData<List<PlaceEntity>> savedPlaces,
                        RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.savedPlaces = savedPlaces;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlaceEntity currentEntry = savedPlaces.getValue().get(position);

        float scaleFactor = 0.4f;

        Bitmap photo = resizeBitmap(
                decodeStringToBitmap(currentEntry.getImage().getImageData()),
                scaleFactor);
        String title = savedPlaces.getValue().get(position).getPlace().getName();
        holder.cardImage.setImageBitmap(photo);
        holder.cardTitle.setText(title);
    }

    private Bitmap decodeStringToBitmap(String codedImage) {
        byte[] byteArray = Base64.decode(codedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    private Bitmap resizeBitmap(Bitmap image, float scaleFactor) {
        int scaledWidth = (int) (image.getWidth() * scaleFactor);
        int scaledHeight = (int) (image.getHeight() * scaleFactor);
        return Bitmap.createScaledBitmap(image, scaledWidth, scaledHeight, true);
    }

    @Override
    public int getItemCount() {
        return savedPlaces.getValue().size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImage;
        TextView cardTitle;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.recycler_item_image_view);
            cardTitle = itemView.findViewById(R.id.recycler_item_title);

            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
