package ir.mehdiyari.ariv

import android.view.View
import org.hamcrest.TypeSafeMatcher

class ViewSizeMatcher(private val expectedWith: Int, private val expectedHeight: Int) : TypeSafeMatcher<View?>(View::class.java) {

    override fun matchesSafely(item: View?): Boolean = item?.width ?: 0 == expectedWith && item?.height ?: 0 == expectedHeight

    override fun describeTo(description: org.hamcrest.Description?) = description?.appendText("with SizeMatcher: $expectedWith x $expectedHeight").let { Unit }
}