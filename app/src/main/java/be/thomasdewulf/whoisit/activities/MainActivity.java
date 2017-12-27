package be.thomasdewulf.whoisit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.fragments.CharacterListFragment;
import be.thomasdewulf.whoisit.fragments.DetailFragment;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
{

    private final String TAG = "MainActivity";
    private int masterId;
    private int detailId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupIdsForReplacement();
        showListFragmentOnStart(savedInstanceState);

    }

    private void setupIdsForReplacement()
    {
        masterId = R.id.fragment_container;
        detailId = R.id.fragment_container;

        if(findViewById(R.id.master_dual) !=null)
        {
            masterId = R.id.master_dual;
            detailId = R.id.detail_dual;
        }

    }

    private void showListFragmentOnStart(Bundle savedInstanceState)
    {
        CharacterListFragment listFragment = new CharacterListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(masterId, listFragment, CharacterListFragment.TAG)
                    .commit();

    }





    /**
     * Detail scherm van een karakter weergeven
     */
    public void show(ImageView imageView)
    {

        DetailFragment detailFragment = new DetailFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("character")
                .replace(detailId, detailFragment, null)
                .commit();
    }
}
