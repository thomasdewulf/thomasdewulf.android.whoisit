package be.thomasdewulf.whoisit;

import android.app.Application;

import be.thomasdewulf.whoisit.database.AppDatabase;
import be.thomasdewulf.whoisit.database.DataRepository;

/**
 * WhoIsIt
 * Created by thomasdewulf on 18/12/17.
 */

public class WhoIsItApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    public DataRepository getRepository()
    {
        return DataRepository.getInstance(AppDatabase.getInstance(getApplicationContext()));
    }


}
