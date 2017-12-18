package be.thomasdewulf.whoisit.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import be.thomasdewulf.whoisit.R;
import be.thomasdewulf.whoisit.models.Character;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * WhoIsIt
 * Created by thomasdewulf on 27/11/17.
 */

public class CharacterViewHolder extends RecyclerView.ViewHolder
{
    @BindView(R.id.characterName)
    TextView characterName;
    @BindView(R.id.characterDescription)
    TextView characterDescription;
    @BindView(R.id.characterImage)
    ImageView characterImage;

    public CharacterViewHolder(View itemView)
    {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setData(Character character)
    {
        characterName.setText(character.getName());
        characterDescription.setText(character.getDescription());
    }
}
