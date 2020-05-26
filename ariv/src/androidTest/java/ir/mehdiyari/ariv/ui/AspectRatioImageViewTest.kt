package ir.mehdiyari.ariv.ui

import android.content.Intent
import android.util.TypedValue
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import ir.mehdiyari.ariv.MockActivity
import ir.mehdiyari.ariv.R
import ir.mehdiyari.ariv.ViewSizeMatcher
import ir.mehdiyari.ariv.model.PhotoBounds
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AspectRatioImageViewTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MockActivity::class.java, true, false)
    private var bounds =
            PhotoBounds(minWidth = 200, maxWidth = 400, minHeight = 200, maxHeight = 400)

    @Test
    fun test_set_bounds() {
        activityRule.launchActivity(Intent())

        onView(withId(R.id.aspectRatioImageView)).check { view, _ ->
            (view as AspectRatioImageView).also {
                it.photoBounds = bounds
                assert(it.photoBounds != bounds)
                bounds =
                        PhotoBounds(minWidth = 400, maxWidth = 1280, minHeight = 560, maxHeight = 960)
                it.photoBounds = bounds
                assert(it.photoBounds != bounds)
            }
        }
    }

    @Test
    fun test_set_photo_dimension() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.aspectRatioImageView)).check { view, _ ->
            (view as AspectRatioImageView).also {
                it.manuallySetPhotoDimension = true
                it.adapterMode = false
                it.photoBounds = PhotoBounds(minWidth = 400, maxWidth = 1280, minHeight = 560, maxHeight = 960)
                it.setPhotoDimension(350, 600)
            }
        }

        onView(withId(R.id.aspectRatioImageView)).check(matches(ViewSizeMatcher(400, 685)))
    }

    @Test
    fun test_set_drawable() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.aspectRatioImageView)).check { view, _ ->
            (view as AspectRatioImageView).also {
                it.manuallySetPhotoDimension = false
                it.adapterMode = false
                it.photoBounds = PhotoBounds(minWidth = 300, maxWidth = 800, minHeight = 200, maxHeight = 600)
                it.setImageResource(R.drawable.bridge)
            }
        }

        onView(withId(R.id.aspectRatioImageView)).check(matches(ViewSizeMatcher(790, 600)))
    }

    @Test
    fun test_set_corner_radius() {
        activityRule.launchActivity(Intent())
        onView(withId(R.id.aspectRatioImageView)).check { view, _ ->
            (view as AspectRatioImageView).also {
                it.manuallySetPhotoDimension = false
                it.photoBounds = PhotoBounds(minWidth = 300, maxWidth = 800, minHeight = 200, maxHeight = 600)
                val twelveDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, it.context.resources.displayMetrics)
                it.cornerRadius = twelveDip
                it.adapterMode = false
                it.setImageResource(R.drawable.road)
                assert(it.cornerRadius == twelveDip)
                val threeDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3f, it.context.resources.displayMetrics)
                it.cornerRadius = threeDip
                assert(it.cornerRadius == threeDip)
            }
        }

        onView(withId(R.id.aspectRatioImageView)).check(matches(ViewSizeMatcher(600, 400)))
    }
}