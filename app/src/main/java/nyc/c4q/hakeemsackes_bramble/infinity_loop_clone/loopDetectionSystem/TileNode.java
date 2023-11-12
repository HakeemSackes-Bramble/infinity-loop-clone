package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem;

import java.util.LinkedList;

public class TileNode {
    int data;
    LinkedList<TileNode> neighbors;
    int maxConnections;

    public TileNode(int data, int maxConnections) {
        this.data = data;
        this.neighbors = new LinkedList<>();
        this.maxConnections = maxConnections;
    }

    public TileNode() {

    }
}