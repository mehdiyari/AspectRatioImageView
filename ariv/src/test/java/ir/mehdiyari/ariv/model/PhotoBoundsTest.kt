package ir.mehdiyari.ariv.model

import org.junit.Test

class PhotoBoundsTest {

    @Test
    fun isNotSet() {
        assert(!PhotoBounds(300, 1367, 300, 720).isNotSet())
        assert( PhotoBounds().isNotSet())
    }

    @Test
    fun calculateWidthAndHeightBasedOnPhotoBounds() {
        val (width, height) = PhotoBounds(minWidth = 350, maxWidth = 1280, minHeight = 350, maxHeight = 960)
            .calculateScaledWidthAndHeightBasedOnBounds(500, 250)

        assert(width == 700)
        assert(height == 350)
    }
}