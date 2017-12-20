package be.thomasdewulf.whoisit.fragments;


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
import be.thomasdewulf.whoisit.databinding.FragmentDetailBinding;
import be.thomasdewulf.whoisit.ui.viewmodel.SharedViewModel;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment
{

    private static final String KEY_CHARACTER_ID = "character_id";
    private FragmentDetailBinding binding;
    private SharedViewModel viewModel;

    private Character character;

    //UI bindings


    private Unbinder unbinder;

    public DetailFragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);

        setupUI();
       // unbinder = ButterKnife.bind(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        //viewmodel ophalen
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        observeUI();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        //unbinder.unbind();
    }

    private void setupUI()
    {
        MainActivity activity = (MainActivity) getActivity();
        activity.setSupportActionBar(binding.toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v ->
        {
          activity.onBackPressed();
        });
        }

    public static DetailFragment forCharachter(int characterId)
    {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CHARACTER_ID, characterId);
        fragment.setArguments(args);
        return fragment;
    }
    private void observeUI()
    {
        viewModel.getSelectedCharacter().observe(this, character ->
                binding.setCharacter(character));
    }

}
