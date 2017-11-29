package be.thomasdewulf.whoisit.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 13/11/17.
 */

@Database(entities = {Character.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
public abstract CharacterDao characterDao();
}
