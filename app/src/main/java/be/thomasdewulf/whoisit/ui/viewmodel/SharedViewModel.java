package be.thomasdewulf.whoisit.ui.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 19/12/17.
 */

public class SharedViewModel extends ViewModel
{
    
    private MutableLiveData<Character> selectedCharacter;
    public SharedViewModel()
    {
        selectedCharacter = new MutableLiveData<>();
        selectedCharacter.setValue(null);
    }

    public void select(Character character)
    {
        selectedCharacter.setValue(character);
    }

    public MutableLiveData<Character> getSelectedCharacter()
    {
        return selectedCharacter;
    }

    
}
