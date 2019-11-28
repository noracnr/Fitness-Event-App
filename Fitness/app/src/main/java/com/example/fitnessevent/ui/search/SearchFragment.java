package com.example.fitnessevent.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fitnessevent.R;
import com.example.fitnessevent.ui.home.SearchActivity;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;





    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText mSearchfield = root.findViewById(R.id.search_field);
        final ImageButton btn_search = root.findViewById(R.id.btn_search);


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getActivity(), SearchActivity.class);
                searchIntent.putExtra("searchKeywords",mSearchfield.getText().toString());
                startActivity(searchIntent);
            }
        });
        /*
        searchViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */
        return root;
    }




}

