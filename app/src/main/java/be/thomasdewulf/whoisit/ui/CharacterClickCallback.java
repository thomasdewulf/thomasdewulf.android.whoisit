package be.thomasdewulf.whoisit.ui;

import android.view.View;

import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 19/12/17.
 */

public interface CharacterClickCallback
{
    void onClick(View view, Character character);
}
