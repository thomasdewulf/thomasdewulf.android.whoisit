package be.thomasdewulf.whoisit.adapters.viewholders;

import android.support.v7.widget.RecyclerView;

import be.thomasdewulf.whoisit.databinding.CharacterListItemBinding;

/**
 * WhoIsIt
 * Created by thomasdewulf on 27/11/17.
 */

public class CharacterViewHolder extends RecyclerView.ViewHolder
{
    private final CharacterListItemBinding binding;

    public CharacterViewHolder(CharacterListItemBinding binding)
    {
        super(binding.getRoot());
        this.binding = binding;

    }

    public CharacterListItemBinding getBinding()
    {
        return binding;
    }


}
