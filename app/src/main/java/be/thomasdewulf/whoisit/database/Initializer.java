package be.thomasdewulf.whoisit.database;

import android.os.AsyncTask;
import android.util.Log;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 18/12/17.
 */

public class Initializer
{
    private static final String TAG = "Initializer";
    public static void populateDbAsync(AppDatabase db)
    {
        PopulateTask task = new PopulateTask(db);
        task.execute();
    }

    private static void populateDb(AppDatabase db)
    {
        Character character1 = new Character("Test 1","Test Description");
        Character character2 = new Character("Vic","Dude with headphones");
        db.characterDao().insertCharacters(character1,character2);
        Log.i(TAG, "Inserted some categories");
    }


    private static class PopulateTask extends AsyncTask<Void, Void, Void>
    {
private final AppDatabase db;

        public PopulateTask(AppDatabase db)
        {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {

            populateDb(db);
            return null;
        }
    }
}
