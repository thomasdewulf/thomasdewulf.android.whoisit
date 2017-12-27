package be.thomasdewulf.whoisit.database;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

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
        if (instance == null)
        {
            synchronized (DataRepository.class)
            {
                if (instance == null)
                {
                    instance = new DataRepository(database);
                }
            }
        }
        return instance;
    }

    public LiveData<List<Character>> getCharacters()
    {
        return database.characterDao().getAll();
    }

    public void insertCharacter(Character character)
    {
       InsertTask task = new InsertTask(database,character);
       task.execute();
    }

    public void deleteCharacter(Character character)
    {
        DeleteTask task = new DeleteTask(database,character);
        task.execute();
    }

    public void updateCharacter(Character character)
    {
        UpdateTask task = new UpdateTask(database,character);
        task.execute();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Void>
    {
        private final AppDatabase appDatabase;
        private final Character character;

        public InsertTask(AppDatabase appDatabase, Character character)
        {
            this.appDatabase = appDatabase;
            this.character = character;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            appDatabase.characterDao().insertCharacters(character);
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Void,Void,Void>
    {
        private final AppDatabase database;
        private final Character character;

        public DeleteTask(AppDatabase database, Character character)
        {
            this.database = database;
            this.character = character;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            database.characterDao().deleteCharachters(character);
            return null;
        }
    }

    private static class UpdateTask extends AsyncTask<Void, Void, Void>
    {
        private final AppDatabase database;
        private final Character character;

        public UpdateTask(AppDatabase database, Character character)
        {
            this.database = database;
            this.character = character;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
           database.characterDao().deleteCharachters(character);
           return null;
        }
    }
}
