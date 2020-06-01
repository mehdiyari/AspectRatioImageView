package ir.mehdiyari.example.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import kotlin.math.floor

fun dpToPx(dp: Int): Int = (dp * Resources.getSystem().displayMetrics.density).toInt()
fun pxToDp(px: Int): Int = (px / Resources.getSystem().displayMetrics.density).toInt()

fun dp(context: Context, size: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, context.resources.displayMetrics).toInt()

fun sp(context: Context, size: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, context.resources.displayMetrics).toInt()


fun divideScreenToEqualPart(
    @IntRange(from = 1) displayWidth: Int,
    @FloatRange(from = 1.toDouble()) itemWidth: Float,
    @IntRange(from = 1) minCount: Int
): Int = floor((displayWidth / itemWidth).toDouble()).toInt().let {
    if (it == 0)
        minCount
    else
        it
}

fun createSimpleRoundRect(
    radius: Float,
    @ColorInt defaultColor: Int
): Drawable? = ShapeDrawable(RoundRectShape(floatArrayOf(radius, radius, radius, radius, radius, radius, radius, radius), null, null)).apply {
    paint.color = defaultColor
}

fun getColorByAlphabet(char: Char): String = when (char.toUpperCase()) {
    'A' -> "#D32F2F"
    'B' -> "#C2185B"
    'C' -> "#7B1FA2"
    'D' -> "#512DA8"
    'E' -> "#303F9F"
    'F' -> "#1976D2"
    'G' -> "#0288D1"
    'H' -> "#0097A7"
    'I' -> "#00796B"
    'J' -> "#388E3C"
    'K' -> "#689F38"
    'L' -> "#AFB42B"
    'M' -> "#FBC02D"
    'N' -> "#FFA000"
    'O' -> "#F57C00"
    'P' -> "#E64A19"
    'Q' -> "#5D4037"
    'R' -> "#616161"
    'S' -> "#455A64"
    'T' -> "#e53935"
    'U' -> "#D81B60"
    'V' -> "#8E24AA"
    'W' -> "#5E35B1"
    'X' -> "#3949AB"
    'Y' -> "#1E88E5"
    'Z' -> "#039BE5"
    else -> "#D32F2F"
}