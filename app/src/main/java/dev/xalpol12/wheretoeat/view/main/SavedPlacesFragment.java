package dev.xalpol12.wheretoeat.view.main;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;
import dev.xalpol12.wheretoeat.view.place.PlaceFragment;
import dev.xalpol12.wheretoeat.view.utility.ImageDecoder;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;
import dev.xalpol12.wheretoeat.viewmodel.adapter.PlaceAdapter;
import dev.xalpol12.wheretoeat.viewmodel.adapter.RecyclerViewInterface;

public class SavedPlacesFragment extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    PlaceAdapter adapter;
    PlaceActivityViewModel placeActivityViewModel;
    PlaceFragment placeFragment;
    List<PlaceEntity> savedPlaces;

    public SavedPlacesFragment(PlaceActivityViewModel placeActivityViewModel) {
        super(R.layout.fragment_saved_places);
        this.placeActivityViewModel = placeActivityViewModel;
        this.savedPlaces = this.placeActivityViewModel.getAllPlaces().getValue();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_places, container, false);
        initializeUI(view);
        addTouchHelperCallback();
        return view;
    }

    private void initializeUI(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new PlaceAdapter(getContext(), savedPlaces, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void addTouchHelperCallback() {
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                PlaceEntity selectedItem = savedPlaces.get(viewHolder.getAdapterPosition());
                savedPlaces.remove(selectedItem);
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                placeActivityViewModel.deletePlaceFromDb(selectedItem.getPlace().getPlaceId());
            }
        };
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
    }
    
    @Override
    public void onItemClick(int position) {
        setUpNewFragment(position);
    }

    private void setUpNewFragment(int itemPosition) {
        int currentLayoutId = ((ViewGroup) getView().getParent()).getId();
        placeFragment = createNewFragment(itemPosition);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(currentLayoutId, placeFragment, "Selected place fragment")
                .addToBackStack(null)
                .commit();
        setButtonCallback();
    }

    private PlaceFragment createNewFragment(int itemPosition) {
        PlaceEntity placeEntity = savedPlaces.get(itemPosition);
        Place place = placeEntity.getPlace();
        Bitmap bitmap = ImageDecoder.decode(placeEntity.getImage().getImageData());
        Location location = placeActivityViewModel.getCurrentLocation();
        return new PlaceFragment(place, bitmap, location);
    }

    private void setButtonCallback() {
        View.OnClickListener goThereButtonClick = this::goThereButtonClick;
        placeFragment.setGoThereButtonCallback(goThereButtonClick);
    }

    private void goThereButtonClick(View v) {
        Toast.makeText(v.getContext(), "Button working properly", Toast.LENGTH_SHORT).show();
//        Location location = viewModel.getCurrentPlaceLocation();
//        String name = viewModel.getCurrentPlaceName();
//        String strUri = "http://maps.google.com/maps?q=loc:" + location.getLat() + "," + location.getLng() + " (" + name + ")";
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
//
//        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//
//        startActivity(intent);
    }
}
