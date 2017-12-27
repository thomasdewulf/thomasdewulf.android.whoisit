package be.thomasdewulf.whoisit.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import be.thomasdewulf.whoisit.WhoIsItApplication;
import be.thomasdewulf.whoisit.database.DataRepository;
import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 19/12/17.
 */

public class SharedViewModel extends AndroidViewModel
{

    private MediatorLiveData<Character> selectedCharacter;
    private DataRepository dataRepository;

    public SharedViewModel(Application application)
    {
        super(application);
        dataRepository = ((WhoIsItApplication) application).getRepository();
        selectedCharacter = new MediatorLiveData<>();
        selectedCharacter.setValue(null);
    }

    public void select(Character character)
    {


        LiveData<Character> charactersLive = dataRepository.getCharacter(character.getId());
      selectedCharacter.addSource(charactersLive, selectedCharacter::setValue);
    }

    public MediatorLiveData<Character> getSelectedCharacter()
    {
        return selectedCharacter;
    }


}
