package be.thomasdewulf.whoisit.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.adapters.CharacterAdapter;
import be.thomasdewulf.whoisit.ui.viewmodel.CharacterListViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharachterListFragment extends Fragment
{
    public static final String TAG = "CharacterListFragment";
    private CharacterListViewModel viewModel;

    private Unbinder unbinder;

    @BindView(R.id.characterList)
    RecyclerView characterView;

    private CharacterAdapter adapter;

    public CharachterListFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_charachter_list, container, false);
        unbinder = ButterKnife.bind(this,v);
        characterView.setLayoutManager(new LinearLayoutManager(getContext()));
        CharacterListViewModel.Factory viewModelFactory = new CharacterListViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(CharacterListViewModel.class);
        adapter = new CharacterAdapter();
        characterView.setAdapter(adapter);
        observeUI();
        return v;
    }

    private void observeUI()
    {
        viewModel.getCharacters().observe(this, characters -> {
            if(characters != null)
            {
                adapter.setValues(characters);

            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
