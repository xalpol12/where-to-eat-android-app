package dev.xalpol12.wheretoeat.view.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import dev.xalpol12.wheretoeat.R;

public class AboutMeFragment extends Fragment {
    AppCompatButton aboutMeButton;
    String githubProfile = "https://github.com/xalpol12";
    String secretCode = "4Hg1zIAh3rc";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_about_me, container, false);
        initializeUI(view);
        return view;
    }

    private void initializeUI(View view) {
        aboutMeButton = view.findViewById(R.id.button_about_me);
        aboutMeButton.setOnClickListener( v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(githubProfile));
            if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
        aboutMeButton.setOnLongClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + secretCode));
            intent.putExtra("id", secretCode);

            if(intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=" + secretCode));
                startActivity(intent);
            }
            return true;
        });
    }


}
