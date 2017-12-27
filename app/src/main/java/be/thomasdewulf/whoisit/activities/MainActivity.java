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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        showListFragmentOnStart(savedInstanceState);

    }

    private void showListFragmentOnStart(Bundle savedInstanceState)
    {
        /**Lijst fragment weergeven wanneer opstart*/
        if (savedInstanceState == null)
        {
            CharacterListFragment listFragment = new CharacterListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, listFragment, CharacterListFragment.TAG)
                    .commit();
        }
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
                .replace(R.id.fragment_container, detailFragment, null)
                .commit();
    }
}
