package be.thomasdewulf.whoisit.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.thomasdewulf.whoisit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment
{

private static final String KEY_CHARACTER_ID = "character_id";
    public DetailFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public static DetailFragment forCharachter(int characterId)
    {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CHARACTER_ID,characterId);
        fragment.setArguments(args);
        return fragment;
    }

}
