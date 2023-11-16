package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.custom_views.TileView;
import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.GameOneLayout;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class TileAdapter extends RecyclerView.Adapter<TileViewHolder> {
    GameOneLayout gameOneLayout;
    int tileSize;
    public TileAdapter(GameOneLayout gameOneLayout, int tileSize) {
        this.gameOneLayout = gameOneLayout;
        this.tileSize = tileSize;
    }

    @Override
    public TileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       return new TileViewHolder(new TileView(parent.getContext(), gameOneLayout.getTileColor(), tileSize), gameOneLayout);
    }

    @Override
    public void onBindViewHolder(TileViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return gameOneLayout.getGameTiles().size();
    }
}
