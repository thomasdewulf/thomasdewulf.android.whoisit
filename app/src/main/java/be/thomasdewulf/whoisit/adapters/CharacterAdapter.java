package be.thomasdewulf.whoisit.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.adapters.viewholders.CharacterViewHolder;
import be.thomasdewulf.whoisit.databinding.CharacterListItemBinding;
import be.thomasdewulf.whoisit.models.Character;
import be.thomasdewulf.whoisit.ui.CharacterClickCallback;

/**
 * WhoIsIt
 * Created by thomasdewulf on 27/11/17.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder>
{
    private final static String TAG = "CharacterAdapter";
    private final CharacterClickCallback characterClickCallback;
    private List<Character> characters;

    public CharacterAdapter(CharacterClickCallback clickCallback)
    {
        this.characterClickCallback = clickCallback;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        CharacterListItemBinding binding = DataBindingUtil
                .inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.character_list_item,
                        parent,
                        false);

        CharacterViewHolder holder = new CharacterViewHolder(binding);
        binding.setCallback(characterClickCallback);
        Log.d(this.getClass().getSimpleName(), "Creating viewholder");
        return holder;
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position)
    {
        Log.d(this.getClass().getSimpleName(), "Binding position " + position);
        holder.getBinding().setCharacter(characters.get(position));
        holder.getBinding().executePendingBindings();
    }

    public void setValues(List<Character> newCharacters)
    {
        if (characters == null)
        {
            this.characters = newCharacters;
            notifyItemRangeInserted(0, newCharacters.size());
        } else
        {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback()
            {
                @Override
                public int getOldListSize()
                {
                    return characters.size();
                }

                @Override
                public int getNewListSize()
                {
                    return newCharacters.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
                {
                    return characters.get(oldItemPosition).getId() == newCharacters.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
                {
                    Character oldCharacter = characters.get(oldItemPosition);
                    Character newCharacter = newCharacters.get(newItemPosition);
                    boolean isIdTheSame = oldCharacter.getId() == newCharacter.getId();
                    boolean isNameTheSame = oldCharacter.getName() == newCharacter.getName();
                    boolean isDescriptionTheSame = oldCharacter.getDescription() == newCharacter.getDescription();

                    return isIdTheSame && isNameTheSame && isDescriptionTheSame;
                }
            });

            characters = newCharacters;
            result.dispatchUpdatesTo(this);
        }


    }

    @Override
    public int getItemCount()
    {
        return characters == null ? 0 : characters.size();
    }
}
