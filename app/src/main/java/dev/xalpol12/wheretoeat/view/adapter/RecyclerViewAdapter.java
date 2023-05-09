//package dev.xalpol12.wheretoeat.view.adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import dev.xalpol12.wheretoeat.R;
//import dev.xalpol12.wheretoeat.data.Place;
//
//public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
//
//    private List<Place> placeList;
//    int currentIndex = 0;
//
//    public RecyclerViewAdapter(List<Place> placeList) {
//        this.placeList = placeList;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.place_details_card, parent, true);
//        return new ViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
//       Place place = placeList.get(position);
//       holder.image.setImageResource(R.drawable.img_default_place_photo);
//       holder.placeName.setText(place.getName());
//       holder.rating.setText(String.valueOf(place.getRating()));
//       holder.ratingCount.setText(String.valueOf(place.getUserRatingsTotal()));
//       holder.address.setText(place.getVicinity());
//       holder.distance.setText("1 km away"); //TODO: fetch distance from place
//        if (place.isOpenNow()) {
//            holder.openNow.setText(R.string.open_now);
//            holder.openNow.setTextColor(holder.onOpenColor); //TODO: color dependant on theme
//        } else {
//            holder.openNow.setText(R.string.closed_now);
//            holder.openNow.setTextColor(holder.onClosedColor);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return placeList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private final ImageView image;
//        private final TextView placeName;
//        private final TextView rating;
//        private final TextView ratingCount;
//        private final TextView address;
//        private final TextView distance;
//        private final TextView openNow;
//        private final int onOpenColor;
//        private final int onClosedColor;
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            image = itemView.findViewById(R.id.place_image);
//            placeName = itemView.findViewById(R.id.place_name);
//            rating = itemView.findViewById(R.id.rating);
//            ratingCount = itemView.findViewById(R.id.rating_count);
//            address = itemView.findViewById(R.id.address);
//            distance = itemView.findViewById(R.id.distance);
//            openNow = itemView.findViewById(R.id.open_now);
//
//            onOpenColor = itemView.getResources().getColor(R.color.accent);
//            onClosedColor = itemView.getResources().getColor(R.color.secondary_30_tint);
//        }
//    }
//}
