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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;
import dev.xalpol12.wheretoeat.viewmodel.adapter.PlaceAdapter;
import dev.xalpol12.wheretoeat.viewmodel.adapter.RecyclerViewInterface;

public class SavedPlacesFragment extends Fragment implements RecyclerViewInterface {

    RecyclerView recyclerView;
    LiveData<List<PlaceEntity>> savedPlaces;

    public SavedPlacesFragment(LiveData<List<PlaceEntity>> places) {
        super(R.layout.fragment_saved_places);
        this.savedPlaces = places;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_places, container, false);
        initializeUI(view);
        return view;
    }

    private void initializeUI(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        PlaceAdapter adapter = new PlaceAdapter(getContext(), savedPlaces, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
    }
    
    @Override
    public void onItemClick(int position) {  //recyclerView method
        Toast.makeText(getActivity(), String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
}
