package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects;

import java.util.HashSet;

public class Path {

    private HashSet<Tile> connectedTiles = new HashSet<>();

    private HashSet<Tile> fullyAlignedTiles = new HashSet<>();
    private boolean pathIsComplete;

    public Path() {
    }

    public boolean isPathComplete() {
        return fullyAlignedTiles.size() == connectedTiles.size();
    }

    void addTileToPath(Tile pathTile) {
        connectedTiles.add(pathTile);
        if (pathTile.isProperlyAligned()) {
            fullyAlignedTiles.add(pathTile);
        }
    }

    void removeTileFromPath(Tile tile) {
        connectedTiles.remove(tile);
        fullyAlignedTiles.remove(tile);
    }

    void tileNotAligned(Tile tile) {
        fullyAlignedTiles.remove(tile);
    }

    public HashSet<Tile> getConnectedTiles() {
        return connectedTiles;
    }

    public void setConnectedTiles(HashSet<Tile> connectedTiles) {
        this.connectedTiles = connectedTiles;
    }

    public HashSet<Tile> getFullyAlignedTiles() {
        return fullyAlignedTiles;
    }

    public void setFullyAlignedTiles(HashSet<Tile> fullyAlignedTiles) {
        this.fullyAlignedTiles = fullyAlignedTiles;
    }

    public void mergePaths(Path newPath) {
        connectedTiles.addAll(newPath.getConnectedTiles());
    }
}
