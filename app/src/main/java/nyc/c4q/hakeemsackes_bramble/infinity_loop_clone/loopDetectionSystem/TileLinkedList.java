package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

public class TileLinkedList {

    private int listUuid = 0;
    private TileNode head;
    private Map<Integer, TileNode> nodeMap;

    private Set<TileNode> completelyConnectedNodes = new HashSet<>();

    private int totalNodes = 0;

    public TileLinkedList() {
        head = null;
        nodeMap = new HashMap<>();
    }

    /**
     * This will add a new node to the branching list
     * @param uuid
     * @param prongCount
     */
    public void addNode(int uuid, int prongCount, Tile tile) {

        if (head == null) {
            head = tile;
        } else {
            if (head.neighbors.size() < prongCount) {
                head.neighbors.add(tile);
                tile.getNeighbors().add(head);
            } else {
                System.out.println("Can't add more neighbors to the current node.");
            }

        }
        nodeMap.put(uuid, tile);
    }

    public void deleteNode(int uuid) {
        TileNode nodeToDelete = nodeMap.get(uuid);
        if (nodeToDelete == null) {
            return; // TileNode not found
        }

        if (head == nodeToDelete) {
            head = null; // Remove the current node
        } else {
            for (TileNode neighbor : nodeToDelete.neighbors) {
                neighbor.neighbors.remove(nodeToDelete);
            }
        }
        nodeMap.remove(uuid);
    }

    public void splitAtNode(Tile tile) {
        if (head == null) {
            return;
        }

        if (head.uuid == tile.getUuid() && head.neighbors.size() >= 2) {
            TileNode newNode = new TileNode(tile.getUuid(), head.maxConnections); // Create a new Tilenode for splitting
            TileNode neighbor1 = head.neighbors.get(0);
            TileNode neighbor2 = head.neighbors.get(1);
            newNode.neighbors.add(head);
            newNode.neighbors.add(neighbor1);
            head.neighbors.clear();
            head.neighbors.add(newNode);
            head.neighbors.add(neighbor2);
        }
    }

    public TileNode getNode(int data) {
        return nodeMap.get(data);
    }

    public boolean isCompleteList(){
        return completelyConnectedNodes.size() == totalNodes;
    }

}
