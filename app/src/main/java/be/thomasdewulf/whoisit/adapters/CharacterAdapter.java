package be.thomasdewulf.whoisit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.adapters.viewholders.CharacterViewHolder;
import be.thomasdewulf.whoisit.models.Character;

/**
 * WhoIsIt
 * Created by thomasdewulf on 27/11/17.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder>
{
    private List<Character> characters;

    public CharacterAdapter(List<Character> characters)
    {
        this.characters = characters;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.character_list_item, parent, false);
        CharacterViewHolder holder = new CharacterViewHolder(v);
        Log.d(this.getClass().getSimpleName(), "Creating viewholder");
        return holder;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position)
    {
        Log.d(this.getClass().getSimpleName(), "Binding position " + position);
        holder.setData(characters.get(position));
    }

    @Override
    public int getItemCount()
    {
        return characters.size();
    }
}
