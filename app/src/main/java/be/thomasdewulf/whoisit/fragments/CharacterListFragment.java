package be.thomasdewulf.whoisit.fragments;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.WhoIsItApplication;
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
public class CharacterListFragment extends Fragment
{
    public static final String TAG = "CharacterListFragment";
    private final CharacterClickCallback characterClickCallback = (view, character) ->
    {
        if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED))
        {
            SharedViewModel viewmodel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
            viewmodel.select(character);
            ImageView image = view.findViewById(R.id.characterImage);
            ((MainActivity) getActivity()).show(image);
        }
    };
    private CharacterListViewModel viewModel;
    private CharacterAdapter adapter;
    private FragmentCharachterListBinding binding;

    public CharacterListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_charachter_list, container, false);
        setupUI();

        return binding.getRoot();
    }

    private void setupUI()
    {
        RecyclerView recyclerView = binding.characterList;
        adapter = new CharacterAdapter(characterClickCallback,getContext());
        recyclerView.setAdapter(adapter);

        MainActivity activity = (MainActivity) getActivity();

        activity.setSupportActionBar(binding.toolbar);
        setHasOptionsMenu(true);

        setupAnimations(recyclerView);
        setupDivider(recyclerView);
        setupSwipeToDelete(recyclerView);
        setupFabListener();

    }

    private void setupAnimations(RecyclerView recyclerView)
    {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(100);
        itemAnimator.setRemoveDuration(100);
        recyclerView.setItemAnimator(itemAnimator);
    }

    private void setupDivider(RecyclerView recyclerView)
    {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void setupSwipeToDelete(RecyclerView recyclerView)
    {
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
            {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
            {
                adapter.remove(viewHolder.getAdapterPosition());
                makeSnackbar(binding.addCharacterButton, "Karakter is verwijderd");
            }
        };

        ItemTouchHelper helper = new ItemTouchHelper(itemTouchCallback);
        helper.attachToRecyclerView(recyclerView);
    }

    private void setupFabListener()
    {
        binding.addCharacterButton.setOnClickListener(v ->
        {
            openDialog();
        });
    }

    private void openDialog()
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        AddCharacterFragment newFragment = new AddCharacterFragment();

        newFragment.setTargetFragment(CharacterListFragment.this, 100);
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
            Toast.makeText(getContext(), "Karakter werd toegevoegd", Toast.LENGTH_LONG).show();
            String name = data.getExtras().getString(AddCharacterFragment.INTENT_EXTRA_NAME);
            String description = data.getExtras().getString(AddCharacterFragment.INTENT_EXTRA_DESCRIPTION);
            String path = data.getExtras().getString(AddCharacterFragment.INTENT_EXTRA_PHOTO);
            Character character = new Character(name, description, path);

            WhoIsItApplication app = (WhoIsItApplication) getActivity().getApplication();
            app.getRepository().insertCharacter(character);
        } else if (requestCode == 101)
        {
            //Cancelled
            Toast.makeText(getContext(), "Karakter werd niet toegevoegd", Toast.LENGTH_LONG).show();
        }
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

    private void makeSnackbar(View view, String message)
    {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_character_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_restart)
        {
            viewModel.loadCharacters();
            makeSnackbar(binding.addCharacterButton, getString(R.string.restarted));
        }
        return super.onOptionsItemSelected(item);
    }
}
