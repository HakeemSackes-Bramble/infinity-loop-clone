package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects

/**
 * Created by hakeemsackes-bramble on 10/15/17.
 */
class Tile internal constructor(
    var orientation: Int,
    var tileType: Int,
    var correctOrientation: Int,
    var prongOrientations: Array<String>
) {
    private val alignment = BooleanArray(4)
    var stringOrientation: String
    var tilePositions: HashSet<SquareTilePositions> = HashSet<SquareTilePositions>()

    init {
        stringOrientation = prongOrientations[orientation + 1]
    }

    fun newProngOrientations(): Array<String> {
        return prongOrientations
    }

    fun newProngOrientations(prongOrientations: Array<String>) {
        this.prongOrientations = prongOrientations
        stringOrientation = prongOrientations[orientation + 1]
    }

    fun newOrientation(orientation: Int) {
        this.orientation = orientation
        stringOrientation = prongOrientations[orientation + 1]
    }

    fun returnOrientation(): Int {
        return orientation
    }

    fun isProngConnected(pos: Int, checkConnection: Boolean) {
        alignment[pos] = checkConnection
    }

    fun newStringOrientation(): String {
        return stringOrientation
    }

    val isProperlyAligned: Boolean
        get() = alignment[0] && alignment[1] && alignment[2] && alignment[3]
}