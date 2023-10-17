package nyc.c4q.hakeemsackes_bramble.infinity_loop_clone.gameLevelObjects

/**
 * Created by hakeemsackes-bramble on 1/15/18.
 *
 *
 * enums numerical order matters!!!!!
 * TOP_EDGE,
 * RIGHT_EDGE,
 * BOTTOM_EDGE,
 * LEFT_EDGE,
 * CENTER
 * WARNING:::::: DO NOT CHANGE ORDER!!!
 */
enum class SquareTilePositions(private val value: Int) {
    TOP_EDGE(0), RIGHT_EDGE(1), BOTTOM_EDGE(2), LEFT_EDGE(3), CENTER(4), NULL_POS(-1);

    companion object {
        private val tilePositionsFromValue = HashMap<Int, SquareTilePositions>()

        init {
            for (pos in values()) {
                tilePositionsFromValue[pos.value] = pos
            }
        }

        fun getTilePositionsFromValue(i: Int): SquareTilePositions? {
            return tilePositionsFromValue[i]
        }
    }
}