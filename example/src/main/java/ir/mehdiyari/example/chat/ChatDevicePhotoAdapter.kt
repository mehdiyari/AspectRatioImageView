package ir.mehdiyari.example.chat

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ir.mehdiyari.ariv.model.PhotoBounds
import ir.mehdiyari.example.R
import ir.mehdiyari.example.utils.Photo
import ir.mehdiyari.example.utils.getColorByAlphabet
import ir.mehdiyari.example.utils.photoDiffCallback
import kotlinx.android.synthetic.main.chat_item_view.view.*

class ChatDevicePhotoAdapter :
    ListAdapter<Photo, ChatDevicePhotoAdapter.ChatDevicePhotoViewHolder>(photoDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatDevicePhotoAdapter.ChatDevicePhotoViewHolder = ChatDevicePhotoViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.chat_item_view, parent, false)
    )

    override fun onBindViewHolder(holder: ChatDevicePhotoViewHolder, position: Int) = holder.bind()

    inner class ChatDevicePhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.aspectRatioImageView.manuallySetPhotoDimension = true
            itemView.aspectRatioImageView.photoBounds = PhotoBounds(
                minWidth = itemView.context.resources.getDimensionPixelSize(R.dimen.chat_item_min_width),
                maxWidth = itemView.context.resources.getDimensionPixelSize(R.dimen.chat_item_max_width),
                minHeight = itemView.context.resources.getDimensionPixelSize(R.dimen.chat_item_min_height),
                maxHeight = itemView.context.resources.getDimensionPixelSize(R.dimen.chat_item_max_height)
            )
        }

        fun bind() {
            getItem(adapterPosition).also { currentPhoto ->
                val (scaledWidth, scaledHeight) = itemView.aspectRatioImageView.photoBounds.calculateScaledWidthAndHeightBasedOnBounds(
                    currentPhoto.width,
                    currentPhoto.height
                )

                itemView.aspectRatioImageView.setPhotoDimension(
                    scaledWidth,
                    scaledHeight
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
                    .override(scaledWidth, scaledHeight)
                    .into(itemView.aspectRatioImageView)
            }
        }
    }
}