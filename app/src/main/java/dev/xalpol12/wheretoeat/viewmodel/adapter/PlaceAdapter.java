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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyViewHolder> {

    Context context;
    List<PlaceEntity> savedPlaces;

    public PlaceAdapter(Context context, List<PlaceEntity> savedPlaces) {
        this.context = context;
        this.savedPlaces = savedPlaces;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlaceEntity currentEntry = savedPlaces.get(position);

        //Image rescaling for debug
        float scaleFactor = 0.1f;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.zielona_weranda);
        int scaledWidth = (int) (image.getWidth() * scaleFactor);
        int scaledHeight = (int) (image.getHeight() * scaleFactor);

        Bitmap newBitmap = Bitmap.createScaledBitmap(image, scaledWidth, scaledHeight, true);
        holder.cardImage.setImageBitmap(newBitmap);
//        Bitmap photo = decodeStringToBitmap(currentEntry.getImage().getImageData());
        String title = savedPlaces.get(position).getPlace().getName();
//        holder.cardImage.setImageBitmap(photo);
        holder.cardTitle.setText(title);
    }

    private Bitmap decodeStringToBitmap(String codedImage) {
        byte[] byteArray = Base64.decode(codedImage, Base64.DEFAULT); //TODO: resize photo
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    @Override
    public int getItemCount() {
        return savedPlaces.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImage;
        TextView cardTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardImage = itemView.findViewById(R.id.recycler_item_image_view);
            cardTitle = itemView.findViewById(R.id.recycler_item_title);
        }
    }
}
