package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.tileRecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */

public class TileAdapter extends RecyclerView.Adapter<TileViewHolder> {
    ArrayList<Tile> gameTiles;

    public TileAdapter(ArrayList<Tile> gameTiles) {
        this.gameTiles = gameTiles;
    }

    @Override
    public TileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       return new TileViewHolder(new TileView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(TileViewHolder holder, int position) {
        holder.bind(gameTiles.get(position));
    }

    @Override
    public int getItemCount() {
        return gameTiles.size();
    }
}
