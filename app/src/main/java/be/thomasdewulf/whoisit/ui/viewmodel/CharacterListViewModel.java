package be.thomasdewulf.whoisit.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import be.thomasdewulf.whoisit.WhoIsItApplication;
import be.thomasdewulf.whoisit.database.DataRepository;
import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 27/11/17.
 */

public class CharacterListViewModel extends AndroidViewModel
{
    private DataRepository dataRepository;


    private MediatorLiveData<List<Character>> characters;


    public CharacterListViewModel(Application application)
    {
        super(application);
        dataRepository = ((WhoIsItApplication) application).getRepository();
        characters = new MediatorLiveData<>();
        characters.setValue(null);
        loadCharacters();

    }

    public MediatorLiveData<List<Character>> getCharacters()
    {

        return characters;
    }

    public void loadCharacters()
    {
        LiveData<List<Character>> charactersLive = dataRepository.getCharacters();
        characters.addSource(charactersLive, characters::setValue);
    }




    /**
     * Custom factory is nodig. Android slaagt er anders niet in om een instantie van het viewmodel aan te maken
     **/
    public static class Factory extends ViewModelProvider.NewInstanceFactory
    {

        @NonNull
        private final Application mApplication;


        public Factory(@NonNull Application application)
        {
            mApplication = application;

        }

        @Override
        public <T extends ViewModel> T create(java.lang.Class<T> modelClass)
        {
            //noinspection unchecked
            return (T) new CharacterListViewModel(mApplication);
        }
    }
}
