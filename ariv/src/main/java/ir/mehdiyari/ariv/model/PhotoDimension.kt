package ir.mehdiyari.ariv.model

import kotlin.math.max
import kotlin.math.min

internal class PhotoDimension(
        var width: Int,
        var height: Int
) {

    fun setPhotoDimensionWhenDimensionIsNotInBounds(
            photoBounds: PhotoBounds
    ) {
        val minWidthRatio = this.width.toDouble() / photoBounds.minWidth
        val maxWidthRatio = this.width.toDouble() / photoBounds.maxWidth
        val minHeightRatio = this.height.toDouble() / photoBounds.minHeight
        val maxHeightRatio = this.height.toDouble() / photoBounds.maxHeight

        if (maxWidthRatio > 1 || maxHeightRatio > 1) // image width or height is bigger then preferred width or height
            setPhotoDimensionIfAspectRatioBiggerThen1(maxWidthRatio, maxHeightRatio, width.toDouble(), height.toDouble(), photoBounds)
        else if (minWidthRatio < 1 || minHeightRatio < 1)
            setPhotoDimensionIfAspectRationLessThen1(minWidthRatio, minHeightRatio, width.toDouble(), height.toDouble(), photoBounds)
    }

    private fun setPhotoDimensionIfAspectRatioBiggerThen1(
            maxWidthRatio: Double,
            maxHeightRatio: Double,
            measuredWidth: Double,
            measuredHeight: Double,
            photoBounds: PhotoBounds
    ) = if (maxWidthRatio >= maxHeightRatio) {
        width = max(measuredWidth / maxWidthRatio, photoBounds.minWidth.toDouble()).toInt()
        height = max(measuredHeight / maxWidthRatio, photoBounds.minHeight.toDouble()).toInt()
    } else {
        width = max(measuredWidth / maxHeightRatio, photoBounds.minWidth.toDouble()).toInt()
        height = max(measuredHeight / maxHeightRatio, photoBounds.minHeight.toDouble()).toInt()
    }

    private fun setPhotoDimensionIfAspectRationLessThen1(
            minWidthRatio: Double,
            minHeightRatio: Double,
            measuredWidth: Double,
            measuredHeight: Double,
            photoBounds: PhotoBounds
    ) = if (minWidthRatio <= minHeightRatio) {
        width = min(measuredWidth / minWidthRatio, photoBounds.maxWidth.toDouble()).toInt()
        height = min(measuredHeight / minWidthRatio, photoBounds.maxHeight.toDouble()).toInt()
    } else {
        width = min(measuredWidth / minHeightRatio, photoBounds.maxWidth.toDouble()).toInt()
        height = min(measuredHeight / minHeightRatio, photoBounds.maxHeight.toDouble()).toInt()
    }

    fun setWidthAndHeightTo(newValue: Int) {
        width = newValue
        height = newValue
    }

    fun isNotSet(): Boolean = width == 0 && height == 0

    override fun toString(): String = "width($width) height($height)"
}