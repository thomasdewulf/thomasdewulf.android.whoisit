package be.thomasdewulf.whoisit.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.database.AppDatabase;
import be.thomasdewulf.whoisit.database.Initializer;
import be.thomasdewulf.whoisit.fragments.CharachterListFragment;
import be.thomasdewulf.whoisit.fragments.DetailFragment;
import be.thomasdewulf.whoisit.models.Character;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showListFragmentOnStart(savedInstanceState);
        populateDatabase();
    }

    private void showListFragmentOnStart(Bundle savedInstanceState)
    {
        /**Lijst fragment weergeven wanneer opstart*/
        if (savedInstanceState == null)
        {
            CharachterListFragment listFragment = new CharachterListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, listFragment, CharachterListFragment.TAG)
                    .commit();
        }
    }

    /**
     * Populeert de database wanneer de app voor het eerst wordt opgestart.
     */
    private void populateDatabase()
    {
        SharedPreferences sharedPreferences = this.getSharedPreferences(TAG, Context.MODE_PRIVATE);
        boolean defaultValueFirstRun = true;
        boolean isFirstRun = sharedPreferences.getBoolean(getString(R.string.is_first_run), defaultValueFirstRun);

        if (isFirstRun)
        {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            Initializer.populateDbAsync(db);

            isFirstRun = false;

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(getString(R.string.is_first_run), isFirstRun);
            editor.commit();
        }

    }



    /**
     * Detail scherm van een karakter weergeven
     */
    public void show(Character character)
    {
        DetailFragment detailFragment = DetailFragment.forCharachter(character.getId());
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("character")
                .replace(R.id.fragment_container, detailFragment, null)
                .commit();
    }
}
