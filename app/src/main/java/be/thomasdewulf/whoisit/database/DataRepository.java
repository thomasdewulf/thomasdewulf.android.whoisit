package be.thomasdewulf.whoisit.database;

import android.arch.lifecycle.LiveData;

import java.util.List;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 18/12/17.
 * Repository class. Single point of truth voor de app
 * Ook een singleton.
 */

public class DataRepository
{
    private static DataRepository instance;
    private final AppDatabase database;



private DataRepository(final AppDatabase database)
{
    this.database = database;

}

public static DataRepository getInstance(final AppDatabase database)
{
    if(instance == null)
    {
        synchronized (DataRepository.class)
        {
            if(instance == null)
            {
                instance = new DataRepository(database);
            }
        }
    }
    return  instance;
}

public LiveData<List<Character>> getCharacters(){
    return database.characterDao().getAll();
}
}
