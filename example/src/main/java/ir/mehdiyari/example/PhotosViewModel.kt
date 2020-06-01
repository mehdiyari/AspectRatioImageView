package ir.mehdiyari.example

import android.app.Application
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.lifecycle.AndroidViewModel
import ir.mehdiyari.example.utils.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PhotosViewModel(private val context: Application) : AndroidViewModel(context) {

    private val photoProjection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media.DATE_ADDED
    )

    fun getPhotos(): Flow<List<Photo>> = flow {
        val photos = mutableListOf<Photo>()
        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            photoProjection,
            null,
            null,
            "${MediaStore.Images.Media.DATE_ADDED} DESC"
        )?.use { cursor ->
            while (cursor.moveToNext()) {
                try {
                    val path = cursor.getString(cursor.getColumnIndexOrThrow(photoProjection[1]))
                    getPhotoDimension(path).also {
                        photos.add(
                            Photo(
                                it.first,
                                it.second,
                                path
                            )
                        )
                    }
                } catch (ignored: Throwable) {
                    ignored.printStackTrace()
                }
            }
            cursor.close()
        }

        this.emit(photos)
    }.flowOn(Dispatchers.IO)

}

fun getPhotoDimension(path: String): Pair<Int, Int> = BitmapFactory.Options().apply {
    inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, this)
}.let {
    it.outWidth to it.outHeight
}