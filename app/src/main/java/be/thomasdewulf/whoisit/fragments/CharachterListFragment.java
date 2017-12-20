package be.thomasdewulf.whoisit.fragments;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.activities.MainActivity;
import be.thomasdewulf.whoisit.adapters.CharacterAdapter;
import be.thomasdewulf.whoisit.databinding.FragmentCharachterListBinding;
import be.thomasdewulf.whoisit.models.Character;
import be.thomasdewulf.whoisit.ui.CharacterClickCallback;
import be.thomasdewulf.whoisit.ui.viewmodel.CharacterListViewModel;
import be.thomasdewulf.whoisit.ui.viewmodel.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharachterListFragment extends Fragment
{
    public static final String TAG = "CharacterListFragment";
    private final CharacterClickCallback characterClickCallback = new CharacterClickCallback()
    {
        @Override
        public void onClick(Character character)
        {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED))
            {
                SharedViewModel viewmodel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
                viewmodel.select(character);
                ((MainActivity) getActivity()).show(character);
            }
        }
    };
    private CharacterListViewModel viewModel;
    private CharacterAdapter adapter;
    private FragmentCharachterListBinding binding;


    public CharachterListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charachter_list, container, false);
        //Recyclerview
        adapter = new CharacterAdapter(characterClickCallback);
        binding.characterList.setAdapter(adapter);

        return binding.getRoot();
    }



    private void observeUI()
    {
        //Observeren van karakters in viewmodel. Recyclerview adapter waarden doorgeven wanneer ze geupdatet worden.
        viewModel.getCharacters().observe(this, characters ->
        {
            if (characters != null)
            {
                adapter.setValues(characters);

            }
            binding.executePendingBindings();
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //Viewmodel ophalen
        CharacterListViewModel.Factory viewModelFactory = new CharacterListViewModel.Factory(getActivity().getApplication());
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CharacterListViewModel.class);
        observeUI();

    }
}
