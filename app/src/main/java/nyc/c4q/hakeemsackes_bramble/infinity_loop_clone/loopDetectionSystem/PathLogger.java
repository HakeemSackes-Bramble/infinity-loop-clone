package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem;


import java.util.HashMap;
import java.util.Set;

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
    private HashMap<String, Set<String>> pathMap = new HashMap<>();

    PathLogger() {
    }

    public HashMap<String, Set<String>> getPathMap() {
        return pathMap;
    }

    public void setPathMap(HashMap<String, Set<String>> pathMap) {
        this.pathMap = pathMap;
    }

    /**
     * Method will add a new path segment to existing paths.
     * Path section will be the tile type and the orientation of the current tile.
     *
     * @param uuid
     * @param pathSection
     */
    public void setNewPathSection(Set<String> uuid, String pathSection) {
        Set<String> path = pathMap.get(uuid);
        path.add(pathSection);
    }

    /**
     * used for when line(s) are diverged. (fork in the road or a ). this will create new paths and thus
     * create new sections.
     *
     * @param uuid
     * @param pathSection
     */
    public void pathForker(Set<String> uuid, String pathSection) {
    }

    /**
     * Used to add new path section to map of paths
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
