package ir.mehdiyari.example.staggered

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.mehdiyari.ariv.model.PhotoBounds
import ir.mehdiyari.ariv.ui.AspectRatioImageView
import ir.mehdiyari.example.R
import ir.mehdiyari.example.utils.Photo
import ir.mehdiyari.example.utils.dp
import ir.mehdiyari.example.utils.getColorByAlphabet
import ir.mehdiyari.example.utils.photoDiffCallback
import kotlinx.android.synthetic.main.staggered_item_view.view.*

class StaggeredDevicePhotoAdapter :
    ListAdapter<Photo, StaggeredDevicePhotoAdapter.DevicePhotoViewHolder>(photoDiffCallback) {

    /*
     when using staggeredLayoutManager imageView width calculated in runtime based on screenWidth or spanCount
     with this callback adapter get the imageViewSize from recyclerView
     */
    var getImageViewWidth: (() -> Int)? = null
    private var imageViewSizeForScalingImages = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicePhotoViewHolder =
        DevicePhotoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.staggered_item_view, parent, false))

    override fun onBindViewHolder(holder: DevicePhotoViewHolder, position: Int) = holder.bind()

    private fun getWidthBasedOnDeviceWidth(): Int {
        if (imageViewSizeForScalingImages == 0)
            imageViewSizeForScalingImages = getImageViewWidth!!.invoke()

        return imageViewSizeForScalingImages
    }

    inner class DevicePhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.aspectRatioImageView.manuallySetPhotoDimension = true
            itemView.aspectRatioImageView.photoBounds = PhotoBounds(
                getWidthBasedOnDeviceWidth(),
                getWidthBasedOnDeviceWidth(),
                dp(itemView.context, 110f),
                dp(itemView.context, 300f)
            )
        }

        fun bind() {
            getItem(adapterPosition).also { currentPhoto ->
                getWidthBasedOnDeviceWidth().also { scaledWidth ->
                    val scaledHeightBasedOnWidth = AspectRatioImageView.getHeightBasedOnScaledWidth(currentPhoto.width, currentPhoto.height, scaledWidth)
                    itemView.aspectRatioImageView.setPhotoDimension(
                        scaledWidth,
                        scaledHeightBasedOnWidth
                    )

                    Glide.with(itemView.aspectRatioImageView)
                        .load(currentPhoto.path)
                        .placeholder(
                            ColorDrawable(
                                Color.parseColor(
                                    getColorByAlphabet(
                                        ('A'..'Z').random()
                                    )
                                )
                            )
                        )
                        .override(scaledWidth, scaledHeightBasedOnWidth)
                        .into(itemView.aspectRatioImageView)
                }
            }
        }
    }
}