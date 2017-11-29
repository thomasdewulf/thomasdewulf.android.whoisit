package be.thomasdewulf.whoisit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.fragments.CharachterListFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{

    private final String TAG = "MainActivity";
   /* @BindView(R.id.addCharacterButton)
    FloatingActionButton fab;*/


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /**Lijst fragment weergeven wanneer app 1e keer opstart*/
        if(savedInstanceState == null)
        {
            CharachterListFragment listFragment = new CharachterListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, listFragment, CharachterListFragment.TAG)
                    .commit();
        }
    }

    @OnClick(R.id.addCharacterButton)
    public void showAddDialog()
    {
        Log.i(TAG, "FAB was clicked");
    }

    /**Detail scherm van een karakter weergeven*/
    public void show(Character character)
    {
        //TODO: implement
    }
}
