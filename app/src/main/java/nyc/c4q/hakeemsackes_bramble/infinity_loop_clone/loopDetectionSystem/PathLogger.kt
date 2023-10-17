package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.loopDetectionSystem

/**
 * Path tracker object will be used to monitor connected tile objects in game
 */
class PathLogger {
    /**
     * When creating a new game, add all of the pathSections to the hashMap of paths and their respetive uuids.
     * @param pathMap
     */
    /**
     * This is the path map. It will be the place where I keep track of all of the
     * connected lines in game.
     *
     * HashMap<uuid></uuid>(currently string for easy testing)
     *
     * TODO: change to uuid
     * Set of strings representing each section of path.
     */
    var pathMap = HashMap<String, MutableSet<String>>()

    /**
     * thishash map will be used to logg every section of the paths with the uuid of the path that is created
     * HashMap<pathId></pathId>, uuid>
     *
     */
    private val pathUuid = HashMap<String, String>()

    /**
     * Method will add a new path segment to existing path sets.
     * Path section will be the tile type and the orientation of the current tile.
     *
     * @param uuid
     * @param newPathSection
     */
    fun NewPathSection(uuid: String, newPathSection: String) {
        val path = pathMap[uuid]!!
        path.add(newPathSection)
        pathMap[uuid] = path
    }

    /**
     * used for when line(s) are diverged. (fork in the road or multiple prongs). this will create new paths and thus create new sections.
     *
     * @param uuid
     * @param pathSection
     */
    fun pathForker(uuid: String, pathSection: String?) {
        val path: Set<String> = pathMap[uuid]!!
        for (i in 0..2) {
        }
    }

    /**
     * Used to add new path section to map of paths
     *
     * @param pathSection
     */
    fun addPath(pathSection: String?) {}

    /**
     * used to remove extra path from the map after two paths are merged
     */
    fun removePath(uuid: String) {
        pathMap.remove(uuid)
    }
    /**
     * first, identify if edge of tile section is connected to other tile.
     * add newly added tile section to list of unique path sets.
     * check if other edge connects.
     * if other edge connects to new section then combine paths and add new uuid.
     *
     */
}