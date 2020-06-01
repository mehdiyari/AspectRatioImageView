package ir.mehdiyari.example.staggered

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ir.mehdiyari.example.PhotosViewModel
import ir.mehdiyari.example.R
import ir.mehdiyari.example.utils.Photo
import ir.mehdiyari.example.utils.divideScreenToEqualPart
import ir.mehdiyari.example.utils.dpToPx
import kotlinx.android.synthetic.main.staggered_activity.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.util.*

class StaggeredLayoutManagerActivity : AppCompatActivity() {

    private val devicePhotoAdapter = StaggeredDevicePhotoAdapter()
    private lateinit var viewModel: PhotosViewModel

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.staggered_activity)
        viewModel = ViewModelProvider(this)[PhotosViewModel::class.java]

        val spanCount = divideScreenToEqualPart(
            displayWidth = resources.displayMetrics.widthPixels,
            itemWidth = resources.getDimension(R.dimen.bucket_max_width),
            minCount = 2
        )

        /**
         * get imageView width based in below formula :
         * spanCount = screenWidth / layoutWidth
         * imageViewSize = (screenWidth / spanCount) - paddingBetweenViewHolders * spanCount
         */
        devicePhotoAdapter.getImageViewWidth = {
            if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
                (resources.displayMetrics.widthPixels /  spanCount) - resources.getDimensionPixelSize(R.dimen.pdding_between_staggred_items) * spanCount
            } else dpToPx(200)
        }

        recyclerView.apply {
            adapter = devicePhotoAdapter
            layoutManager = StaggeredGridLayoutManager(
                spanCount, RecyclerView.VERTICAL
            )
        }

        lifecycleScope.launch {
            viewModel.getPhotos().collect(object : FlowCollector<List<Photo>> {
                override suspend fun emit(value: List<Photo>) {
                    devicePhotoAdapter.submitList(value.shuffled(Random()))
                }
            })
        }
    }
}