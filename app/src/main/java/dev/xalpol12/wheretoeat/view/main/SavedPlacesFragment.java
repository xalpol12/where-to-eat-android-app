package dev.xalpol12.wheretoeat.view.main;

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
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;
import dev.xalpol12.wheretoeat.viewmodel.PlaceActivityViewModel;
import dev.xalpol12.wheretoeat.viewmodel.adapter.PlaceAdapter;
import dev.xalpol12.wheretoeat.viewmodel.adapter.RecyclerViewInterface;

public class SavedPlacesFragment extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    PlaceAdapter adapter;
    PlaceActivityViewModel placeActivityViewModel;
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
    public void onItemClick(int position) {  //recyclerView method
        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

}
