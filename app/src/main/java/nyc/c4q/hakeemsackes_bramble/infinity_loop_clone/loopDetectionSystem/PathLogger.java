package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem;


import java.util.HashMap;
import java.util.Set;

import nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects.Tile;

/**
 * Path tracker object will be used to monitor connected tile objects in game
 */
public class PathLogger {

    /**
     * This is the path map. It will be the place where I keep track of all of the
     * connected lines in game.
     * <p>
     * HashMap<uuid(currently string for easy testing)
     * TODO: change to uuid
     * , set of strings representing each section of path
     */
    private HashMap<String, Set<Tile>> pathMap = new HashMap<>();

    PathLogger() {
    }

    public HashMap<String, Set<Tile>> getPathMap() {
        return pathMap;
    }

    public void setPathMap(HashMap<String, Set<Tile>> pathMap) {
        this.pathMap = pathMap;
    }

    /**
     * Method will add a new path segment to existing paths.
     * Path section will be the tile type and the orientation of the current tile.
     *
     * @param uuid
     * @param pathSection
     */
    public void setNewPathSection(String uuid, Tile pathSection) {
        Set<Tile> path = pathMap.get(uuid);
        path.add(pathSection);
        pathMap.put(uuid, path);
    }

    /**
     * used for when line(s) are diverged. (fork in the road or multiple prongs). this will create new paths and thus create new sections.
     *
     * @param uuid
     * @param pathSection
     */
    public void pathForker(String uuid, Tile pathSection ) {
        Set<Tile> path = pathMap.get(uuid);
        for (int i = 0; i <5 ; i++) {

        }

    }

    /**
     * Used to add new path section to map of paths
     *
     * @param pathSection
     */
    public void addPath(String pathSection) {
    }

    /**
     * used to remove path from the map after two paths are merged
     */
    public void removePath(String uuid) {
        pathMap.remove(uuid);
    }
}
