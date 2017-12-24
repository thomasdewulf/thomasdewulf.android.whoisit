package be.thomasdewulf.whoisit.ui.viewmodel;

import android.arch.lifecycle.ViewModel;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 19/12/17.
 */

public class SharedViewModel extends ViewModel
{

    private Character selectedCharacter;



    public void select(Character character)
    {
        selectedCharacter = character;
    }

    public Character getSelectedCharacter()
    {
        return selectedCharacter;
    }


}
