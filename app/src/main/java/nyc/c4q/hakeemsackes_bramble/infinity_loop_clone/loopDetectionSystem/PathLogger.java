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
     *
     * HashMap<uuid(currently string for easy testing)
     *
     * TODO: change to uuid
     * Set of strings representing each section of path.
     */
    private HashMap<String, Set<String>> pathMap = new HashMap<>();

    /**
     * thishash map will be used to logg every section of the paths with the uuid of the path that is created
     * HashMap<pathId, uuid>
     *
     */
    private HashMap<String, String> pathUuid = new HashMap<>();

    public PathLogger() {
    }

    public HashMap<String, Set<String>> getPathMap() {
        return pathMap;
    }

    /**
     * When creating a new game, add all of the pathSections to the hashMap of paths and their respetive uuids.
     * @param pathMap
     */
    public void setPathMap(HashMap<String, Set<String>> pathMap) {
        this.pathMap = pathMap;
    }

    /**
     * Method will add a new path segment to existing path sets.
     * Path section will be the tile type and the orientation of the current tile.
     *
     * @param uuid
     * @param newPathSection
     */
    public void NewPathSection(String uuid, String newPathSection) {
        Set<String> path = pathMap.get(uuid);
        path.add(newPathSection);
        pathMap.put(uuid, path);
    }

    /**
     * used for when line(s) are diverged. (fork in the road or multiple prongs). this will create new paths and thus create new sections.
     *
     * @param uuid
     * @param pathSection
     */
    public void pathForker(String uuid, String pathSection) {
        Set<String> path = pathMap.get(uuid);
        for (int i = 0; i <  3; i++) {
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
     * used to remove extra path from the map after two paths are merged
     */
    public void removePath(String uuid) {
        pathMap.remove(uuid);
    }

    /**
     * first, identify if edge of tile section is connected to other tile.
     * add newly added tile section to list of unique path sets.
     * check if other edge connects.
     * if other edge connects to new section then combine paths and add new uuid.
     *
     */

}
