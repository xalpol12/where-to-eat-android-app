package dev.xalpol12.wheretoeat.view.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import dev.xalpol12.wheretoeat.R;
import dev.xalpol12.wheretoeat.data.ImageResult;
import dev.xalpol12.wheretoeat.data.Place;
import dev.xalpol12.wheretoeat.database.entity.PlaceEntity;
import dev.xalpol12.wheretoeat.viewmodel.adapter.PlaceAdapter;

public class SavedPlacesFragment extends Fragment {

    RecyclerView recyclerView;
    List<String> placeNames = List.of("McDonalds Łużycka", "Zielona Weranda",
            "DaVinci", "Bardzo długa nazwa restauracji oceniana prawdopodnie bardzo nisko w google maps", "kolejne",
            "smaki babuni", "Costca coffee", "smaki świata");
    public SavedPlacesFragment() {
        super(R.layout.fragment_saved_places);
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
        List<PlaceEntity> places = createFakeData();
        PlaceAdapter adapter = new PlaceAdapter(getContext(), places);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private List<PlaceEntity> createFakeData() {
        List<PlaceEntity> places = new ArrayList<>();
        for (String name : placeNames) {
            Place place = new Place();
            place.setName(name);
            PlaceEntity placeEntity = new PlaceEntity(place, new ImageResult("imageData", "photoReference"));
            places.add(placeEntity);
        }
        return places;
    }
}
