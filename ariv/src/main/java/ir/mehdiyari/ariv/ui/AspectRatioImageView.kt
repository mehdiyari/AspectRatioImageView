package ir.mehdiyari.ariv.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import com.makeramen.roundedimageview.RoundedImageView
import ir.mehdiyari.ariv.R
import ir.mehdiyari.ariv.model.PhotoBounds
import ir.mehdiyari.ariv.model.PhotoDimension

class AspectRatioImageView(context: Context, attributeSet: AttributeSet?) :
    RoundedImageView(context, attributeSet) {

    private val photoDimension = PhotoDimension(0, 0)
    var adapterMode = false

    var photoBounds = PhotoBounds()
        set(value) {
            if (this.photoBounds == value) return
            field = value
            if (adapterMode) forceLayout() else requestLayout()
        }

    /**
     * if true you must call [setPhotoDimension] before [setImageDrawable]
     */
    var manuallySetPhotoDimension = false

    init {
        context.obtainStyledAttributes(attributeSet, R.styleable.AspectRatioImageView).use {
            if (it.hasValue(R.styleable.AspectRatioImageView_ariv_minWidth))
                photoBounds = photoBounds.copy(
                    minWidth = it.getDimensionPixelSize(
                        R.styleable.AspectRatioImageView_ariv_minWidth,
                        0
                    )
                )

            if (it.hasValue(R.styleable.AspectRatioImageView_ariv_maxWidth))
                photoBounds = photoBounds.copy(
                    maxWidth = it.getDimensionPixelSize(
                        R.styleable.AspectRatioImageView_ariv_maxWidth,
                        0
                    )
                )

            if (it.hasValue(R.styleable.AspectRatioImageView_ariv_minHeight))
                photoBounds = photoBounds.copy(
                    minHeight = it.getDimensionPixelSize(
                        R.styleable.AspectRatioImageView_ariv_minHeight,
                        0
                    )
                )

            if (it.hasValue(R.styleable.AspectRatioImageView_ariv_maxHeight))
                photoBounds = photoBounds.copy(
                    maxHeight = it.getDimensionPixelSize(
                        R.styleable.AspectRatioImageView_ariv_maxHeight,
                        0
                    )
                )

            if (it.hasValue(R.styleable.AspectRatioImageView_ariv_adapterMode))
                adapterMode =
                    it.getBoolean(R.styleable.AspectRatioImageView_ariv_adapterMode, false)

            if (it.hasValue(R.styleable.AspectRatioImageView_android_src))
                super.setImageDrawable(it.getDrawable(R.styleable.AspectRatioImageView_android_src))
        }
    }

    constructor(context: Context) : this(context, null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setPhotoDimension()
        if (photoDimension.isNotSet()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            return
        }

        super.onMeasure(
            MeasureSpec.makeMeasureSpec(
                photoDimension.width + paddingLeft + paddingRight,
                MeasureSpec.EXACTLY
            ),
            MeasureSpec.makeMeasureSpec(
                photoDimension.height + paddingTop + paddingBottom,
                MeasureSpec.EXACTLY
            )
        )
    }

    private fun setPhotoDimension() {
        /*
         * check [photoDimension] & [photoBounds] values is valid
         *  also checking
         * [photoDimension].width in bound or not of [photoDimension].minWidth and [photoDimension].maxWidth &
         * [photoDimension].height in bound or not of [photoDimension].minHeight and [photoDimension].maxHeight
         */
        if (validatePhotoDimensionAndBounds()
            && (!(photoDimension.width.toDouble() >= photoBounds.minWidth && photoDimension.width.toDouble() <= photoBounds.maxWidth)
                    || !(photoDimension.height.toDouble() >= photoBounds.minHeight && photoDimension.height.toDouble() <= photoBounds.maxHeight))
        ) photoDimension.setPhotoDimensionWhenDimensionIsNotInBounds(photoBounds)
    }

    override fun setImageBitmap(bm: Bitmap?) {
        bm?.also(this::applyAutoDimension)
        super.setImageBitmap(bm)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        if (drawable is BitmapDrawable) drawable.bitmap?.also(this::applyAutoDimension)
        super.setImageDrawable(drawable)
    }

    override fun setImageResource(resId: Int) {
        ContextCompat.getDrawable(context, resId).also {
            if (it is BitmapDrawable) it.bitmap.also(this::applyAutoDimension)
            super.setImageDrawable(it)
        } ?: super.setImageResource(resId)
    }

    private fun validatePhotoDimensionAndBounds(): Boolean =
        ((photoDimension.height == 0 || photoDimension.width == 0) || photoBounds.isNotSet()).apply {
            if (this) photoDimension.setWidthAndHeightTo(0)
        }.not()

    /**
     * set [photoDimension] and invalidate view for measuring view with new dimension
     * this method is used for when [manuallySetPhotoDimension] == true and developer wants to
     * set manually size of imageView before loading drawable/bitmap/resource
     */
    fun setPhotoDimension(@IntRange(from = 1) width: Int, @IntRange(from = 1) height: Int) {
        if (photoDimension.width == width && photoDimension.height == height) return
        photoDimension.width = width
        photoDimension.height = height
        if (adapterMode) forceLayout() else requestLayout()
    }

    private fun applyAutoDimension(bitmap: Bitmap) {
        if (!manuallySetPhotoDimension) {
            photoDimension.width = bitmap.width
            photoDimension.height = bitmap.height
        }
    }

    /**
     * get current [photoDimension] as Pair<Width, Height>
     */
    fun getDimension(): Pair<Int, Int> = photoDimension.width to photoDimension.height
}