package be.thomasdewulf.whoisit.ui.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 19/12/17.
 */

public class SharedViewModel extends ViewModel
{
    private MutableLiveData<Character> selected;

    public void select(int characterId)
    {

        //TODO: get character from db
        //selected.setValue(character);
    }

    public LiveData<Character> getSelected()
    {
        return selected;
    }
}
