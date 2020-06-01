package ir.mehdiyari.example.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import ir.mehdiyari.example.R
import ir.mehdiyari.example.utils.Photo
import ir.mehdiyari.example.PhotosViewModel
import kotlinx.android.synthetic.main.chat_activity.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch
import java.util.*


class ChatActivity : AppCompatActivity() {

    private val chatDevicePhotoAdapter = ChatDevicePhotoAdapter()
    private lateinit var viewModel: PhotosViewModel

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_activity)
        viewModel = ViewModelProvider(this)[PhotosViewModel::class.java]

        recyclerView.apply {
            adapter = chatDevicePhotoAdapter
            layoutManager =
                LinearLayoutManager(this@ChatActivity, LinearLayoutManager.VERTICAL, true)
        }

        lifecycleScope.launch {
            viewModel.getPhotos().collect(object : FlowCollector<List<Photo>> {
                override suspend fun emit(value: List<Photo>) {
                    chatDevicePhotoAdapter.submitList(value.shuffled(Random()))
                }
            })
        }
    }

}