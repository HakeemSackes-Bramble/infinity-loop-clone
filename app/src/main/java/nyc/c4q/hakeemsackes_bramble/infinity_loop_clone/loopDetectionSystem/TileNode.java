package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem;

import java.util.LinkedList;

public class TileNode {
    int uuid;
    LinkedList<TileNode> neighbors;
    int maxConnections;

    public TileNode(int uuid, int maxConnections) {
        this.uuid = uuid;
        this.neighbors = new LinkedList<>();
        this.maxConnections = maxConnections;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public LinkedList<TileNode> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(LinkedList<TileNode> neighbors) {
        this.neighbors = neighbors;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

}