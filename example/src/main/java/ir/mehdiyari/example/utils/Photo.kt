package ir.mehdiyari.example.utils

import androidx.recyclerview.widget.DiffUtil

data class Photo(
    val width:Int,
    val height:Int,
    val path:String
)

val photoDiffCallback = object:
    DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.path == newItem.path
    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem
}