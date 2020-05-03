package ir.mehdiyari.ariv.model

import org.junit.Test

class PhotoDimensionTest {

    private val photoDimension = PhotoDimension(4096, 2160) // 4k resolution
    private val imageViewBounds = PhotoBounds(300, 1367, 300, 720)

    /**
     * calculate aspectRatio for 4096x2160
     *
     * 4096 / 300 = 13.653333333333334 -> minWidthRatio
     * 4096 / 1367 = 2.9963423555230433 -> maxWidthRatio
     * 2160 / 300 = 7.2 -> minHeightRatio
     * 2160 / 720 = 3.0 -> maxHeightRatio
     *
     *  13.653333333333334 > 1 || 2.9963423555230433 > 1 -> true
     *      2.9963423555230433 >= 3.0 -> false
     *      else -> true
     *          4096 / 3.0 = 1365.3333333333333
     *          finalWidth = max(1365.3333333333333, 300)
     *          2160 / 3.0 = 720.0
     *          finalHeight = max(720.0, 300)
     *
     *    1367 is width
     *    720 is height
     */
    @Test
    fun setPhotoDimensionWhenDimensionIsNotInBounds_4096_2160() {
        photoDimension.setPhotoDimensionWhenDimensionIsNotInBounds(imageViewBounds)
        println(photoDimension.toString())
        assert(photoDimension.width == 1365)
        assert(photoDimension.height == 720)
    }

    /**
     * calculate aspectRatio for 1080x1920
     *
     * 1080 / 350 = 3.085714285714286 -> minWidthRatio
     * 1080 / 1280 = 0.84375 -> maxWidthRatio
     * 1920 / 350 = 5.485714285714286 -> minHeightRatio
     * 1920 / 960 = 2.0 -> maxHeightRatio
     *
     *  0.84375 > 1 || 2.0 > 1 -> true
     *     0.84375 >= 2.0 -> false
     *     else -> true
     *          1080 / 2.0 = 540.0
     *          finalWidth = max(540.0, 350)
     *          1920 / 2.0 = 960.0
     *          finalHeight = max(960.0, 350)
     *
     *    540 is width
     *    960 is height
     */
    @Test
    fun setPhotoDimensionWhenDimensionIsNotInBounds_1080_1920() {
        // screenShot portrait
        photoDimension.width = 1080
        photoDimension.height = 1920
        photoDimension.setPhotoDimensionWhenDimensionIsNotInBounds(imageViewBounds.copy(minWidth = 350, maxWidth = 1280, minHeight = 350, maxHeight = 960))
        println(photoDimension.toString())
        assert(photoDimension.width == 540)
        assert(photoDimension.height == 960)
    }

    /**
     * calculate aspectRatio for 3120x2150
     *
     * 3120 / 350 = 8.914285714285715 -> minWidthRatio
     * 3120 / 1280 = 2.4375-> maxWidthRatio
     * 2150 / 350 = 6.142857142857143 -> minHeightRatio
     * 2150 / 960 = 2.2395833333333335 -> maxHeightRatio
     *
     *  2.4375 > 1 || 2.2395833333333335 -> true
     *     2.4375 >= 2.2395833333333335 -> true
     *          3120 / 2.4375 = 1280.0
     *          finalWidth = max(1280.0, 350)
     *          2150 / 2.4375 = 882.0512820512821
     *          finalHeight = max(882.0512820512821, 350)
     *
     *    1280 is width
     *    882 is height
     */
    @Test
    fun setPhotoDimensionWhenDimensionIsNotInBounds_3120_2150() {
        photoDimension.width = 3120
        photoDimension.height = 2150
        photoDimension.setPhotoDimensionWhenDimensionIsNotInBounds(imageViewBounds.copy(minWidth = 350, maxWidth = 1280, minHeight = 350, maxHeight = 960))
        println(photoDimension.toString())
        assert(photoDimension.width == 1280)
        assert(photoDimension.height == 882)
    }

    /**
     * calculate aspectRatio for 250x250
     *
     * 250 / 350 = 0.7142857142857143 -> minWidthRatio
     * 250 / 1280 = 0.1953125 -> maxWidthRatio
     * 250 / 350 = 0.7142857142857143 -> minHeightRatio
     * 250 / 960 = 0.2604166666666667 -> maxHeightRatio
     *
     *  0.7142857142857143 > 1 || 0.2604166666666667 > 1 -> false
     *  0.7142857142857143 < 1 || 0.2604166666666667 < 1 -> true
     *      0.7142857142857143 <= 0.7142857142857143 -> true
     *          250 / 0.7142857142857143 = 350.0
     *          finalWidth = max(350.0, 960)
     *          250 / 0.7142857142857143 = 960.0
     *          finalHeight = max(960.0, 350)
     *
     *    540 is width
     *    960 is height
     */
    @Test
    fun setPhotoDimensionWhenDimensionIsNotInBounds_250_250() {
        photoDimension.width = 250
        photoDimension.height = 250
        photoDimension.setPhotoDimensionWhenDimensionIsNotInBounds(imageViewBounds.copy(minWidth = 350, maxWidth = 1280, minHeight = 350, maxHeight = 960))
        println(photoDimension.toString())
        assert(photoDimension.width == 350)
        assert(photoDimension.height == 350)
    }

    /**
     * calculate aspectRatio for 500x250
     *
     * 500 / 350 = 1.4285714285714286 -> minWidthRatio
     * 500 / 1280 = 0.390625 -> maxWidthRatio
     * 250 / 350 = 0.7142857142857143 -> minHeightRatio
     * 250 / 960 = 0.2604166666666667 -> maxHeightRatio
     *
     *  0.390625 > 1 || 0.2604166666666667 > 1 -> false
     *  1.4285714285714286 < 1 || 0.7142857142857143 < 1 -> true
     *       1.4285714285714286 <= 0.7142857142857143 -> false
     *       else -> true
     *          500 / 0.7142857142857143 = 700.0
     *          finalWidth = max(700.0, 960)
     *          250 / 0.7142857142857143 = 350.0
     *          finalHeight = max(350.0, 350)
     *
     *    700 is width
     *    350 is height
     */
    @Test
    fun setPhotoDimensionWhenDimensionIsNotInBounds_500_250() {
        photoDimension.width = 500
        photoDimension.height = 250
        photoDimension.setPhotoDimensionWhenDimensionIsNotInBounds(imageViewBounds.copy(minWidth = 350, maxWidth = 1280, minHeight = 350, maxHeight = 960))
        println(photoDimension.toString())
        assert(photoDimension.width == 700)
        assert(photoDimension.height == 350)
    }

    @Test
    fun setPhotoWhenDimensionIsInBounds_960_500() {
        photoDimension.width = 960
        photoDimension.height = 500
        photoDimension.setPhotoDimensionWhenDimensionIsNotInBounds(
            imageViewBounds.copy(
                minWidth = 400, maxWidth = 960, minHeight = 400, maxHeight = 960
            )
        )

        assert(photoDimension.width == 960)
        assert(photoDimension.height == 500)
    }

    @Test
    fun setWidthAndHeightTo() {
        photoDimension.setWidthAndHeightTo(512)
        assert(photoDimension.width == 512)
        assert(photoDimension.height == 512)
        photoDimension.setWidthAndHeightTo(0)
        assert(photoDimension.isNotSet())
    }

    @Test
    fun check_width_and_height_is_set() {
        assert(!photoDimension.isNotSet())
    }

    @Test
    fun check_width_and_height_is_not_set() {
        photoDimension.width = 0
        photoDimension.height = 0
        assert(photoDimension.isNotSet())
    }
}