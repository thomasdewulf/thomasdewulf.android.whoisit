package be.thomasdewulf.whoisit.util;

import android.view.View;

/**
 * WhoIsIt
 * Created by thomasdewulf on 19/12/17.
 */

public interface RecyclerViewOnClickListener
{
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
