package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem;

import java.util.HashMap;
import java.util.Map;

public class TileLinkedList {
    private TileNode head;
    private Map<Integer, TileNode> nodeMap;

    public TileLinkedList() {
        head = null;
        nodeMap = new HashMap<>();
    }

    public void addNode(int data, int maxConnections) {
        TileNode newNode = new TileNode(data, maxConnections);
        if (head == null) {
            head = newNode;
        } else {
            if (head.neighbors.size() < maxConnections) {
                head.neighbors.add(newNode);
                newNode.neighbors.add(head);
            } else {
                System.out.println("Can't add more neighbors to the current node.");
            }
        }
        nodeMap.put(data, newNode);
    }

    public void deleteNode(int data) {
        TileNode nodeToDelete = nodeMap.get(data);
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

        nodeMap.remove(data);
    }

    public void splitNode(int data) {
        if (head == null) {
            return;
        }

        if (head.data == data && head.neighbors.size() >= 2) {
            TileNode newNode = new TileNode(999, head.maxConnections); // Create a new Tilenode for splitting
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

}
