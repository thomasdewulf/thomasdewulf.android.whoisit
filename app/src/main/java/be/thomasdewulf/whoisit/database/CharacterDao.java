package be.thomasdewulf.whoisit.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 13/11/17.
 */

@Dao
public interface CharacterDao
{

    @Query("SELECT * FROM characters")
    LiveData<List<Character>> getAll();

    @Query("SELECT * FROM characters WHERE name LIKE :name LIMIT 1")
    Character findByName(String name);

    @Insert
    void insertCharacters(Character... characters);

    @Update
    void updateCharachters(Character... characters);

    @Delete
    void deleteCharachters(Character... characters);

    @Query("DELETE FROM characters")
    void nukeTable();
}
