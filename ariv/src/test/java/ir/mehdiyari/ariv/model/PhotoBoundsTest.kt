package ir.mehdiyari.ariv.model

import org.junit.Test

class PhotoBoundsTest {

    @Test
    fun isNotSet() {
        assert(!PhotoBounds(300, 1367, 300, 720).isNotSet())
        assert( PhotoBounds().isNotSet())
    }
}