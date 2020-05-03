package ir.mehdiyari.ariv.model

data class PhotoBounds(
        val minWidth: Int = 0,
        val maxWidth: Int = 0,
        val minHeight: Int = 0,
        val maxHeight: Int = 0
) {
    fun isNotSet(): Boolean = minWidth == 0 && maxWidth == 0 && minHeight == 0 && maxHeight == 0
}