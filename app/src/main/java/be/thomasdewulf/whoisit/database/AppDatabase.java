package be.thomasdewulf.whoisit.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 13/11/17.
 */

@Database(entities = {Character.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{

    public abstract CharacterDao characterDao();

    private static AppDatabase instance;

    public static final String DATABASE_NAME = "whoIsIt-database";

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context)
    {
        if(instance == null)
        {
            synchronized (AppDatabase.class)
            {
                if(instance == null)
                {
                    instance = buildDatabase(context.getApplicationContext());
                    instance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }

        return instance;
    }

    private static AppDatabase buildDatabase(final Context appContext)
    {
        return Room.databaseBuilder(appContext, AppDatabase.class,DATABASE_NAME).addCallback(new Callback()
        {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db)
            {
                super.onCreate(db);
                AppDatabase database = AppDatabase.getInstance(appContext);
                database.setDatabaseCreated();
            }
        }).fallbackToDestructiveMigration().build();

    }

    private void updateDatabaseCreated(final Context context)
    {
        if(context.getDatabasePath(DATABASE_NAME).exists())
        {
            setDatabaseCreated();
        }


    }

    private void setDatabaseCreated()
    {
        isDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {return isDatabaseCreated;}
}
