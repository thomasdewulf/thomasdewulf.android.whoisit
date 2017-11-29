package be.thomasdewulf.whoisit.ui.viewmodel;

import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 27/11/17.
 */

public class CharacterListViewModel extends ViewModel
{

    private List<Character> characters;


    public List<Character> getCharacters()
    {
        if(characters == null)
        {
            characters = new ArrayList<>();
            loadCharacters();
        }
        return characters;
    }

    private void loadCharacters()
    {
        //TODO: ROOM implementatie
    }
}
