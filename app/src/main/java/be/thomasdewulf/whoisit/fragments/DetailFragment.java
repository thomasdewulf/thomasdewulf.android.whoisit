package be.thomasdewulf.whoisit.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.WhoIsItApplication;
import be.thomasdewulf.whoisit.activities.MainActivity;
import be.thomasdewulf.whoisit.databinding.FragmentDetailBinding;
import be.thomasdewulf.whoisit.models.Character;
import be.thomasdewulf.whoisit.ui.viewmodel.SharedViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment
{


    private FragmentDetailBinding binding;
    private SharedViewModel viewModel;


    public DetailFragment()
    {
        // Required empty public constructor
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //viewmodel ophalen
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        observeUI();


        return binding.getRoot();
    }
private void observeUI()
{
    viewModel.getSelectedCharacter().observe(this,character -> {
        if(character != null)
        {
            binding.setCharacter(character);

            Picasso.with(getContext())
                    .load("file:////" + binding.getCharacter().getImageUrl())
                    .fit()
                    .into(binding.backdrop);

            setupUI(character);
        }
        //binding.executePendingBindings();
    });
}

    private void setupUI(Character character)
    {
        MainActivity activity = (MainActivity) getActivity();
        activity.setSupportActionBar(binding.toolbar);

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle(character.getName());
        binding.toolbar.setNavigationOnClickListener(v ->
        {
            activity.onBackPressed();
        });
        binding.collapsingToolbar.setTitle(character.getName());

        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_character_detail,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id == R.id.action_edit)
        {
          openDialog();


            return true;
        } else if(id == R.id.action_delete)
        {

            WhoIsItApplication app = (WhoIsItApplication) getActivity().getApplication();
            app.getRepository().deleteCharacter(binding.getCharacter());
            Toast.makeText(getContext(), "Karakter werd definitief verwijderd", Toast.LENGTH_LONG).show();
            getActivity().onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openDialog()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AddCharacterFragment newFragment = new AddCharacterFragment();
        newFragment.setCharacter(binding.getCharacter());

        newFragment.setTargetFragment(DetailFragment.this, 100);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(R.id.fragment_container, newFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100)
        {
            //OK
            Toast.makeText(getContext(), "Karakter werd gewijzigd", Toast.LENGTH_LONG).show();
            String name = data.getExtras().getString(AddCharacterFragment.INTENT_EXTRA_NAME);
            String description = data.getExtras().getString(AddCharacterFragment.INTENT_EXTRA_DESCRIPTION);
            String path = data.getExtras().getString(AddCharacterFragment.INTENT_EXTRA_PHOTO);
            int id = data.getExtras().getInt(AddCharacterFragment.INTENT_EXTRA_ID);
            Character character = new Character(id,name, description, path);


            WhoIsItApplication app = (WhoIsItApplication) getActivity().getApplication();
            app.getRepository().updateCharacter(character);


        } else if (requestCode == 101)
        {
            //Cancelled
            Toast.makeText(getContext(), "Karakter werd niet gewijzigd", Toast.LENGTH_LONG).show();
        }
    }
}
