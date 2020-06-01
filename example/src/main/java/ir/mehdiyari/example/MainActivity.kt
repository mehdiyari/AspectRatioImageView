package ir.mehdiyari.example

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.mehdiyari.example.chat.ChatActivity
import ir.mehdiyari.example.staggered.StaggeredLayoutManagerActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonChatRecyclerView.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this@MainActivity, ChatActivity::class.java))
                } else {
                    requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
                }
            } else {
                startActivity(Intent(this@MainActivity, StaggeredLayoutManagerActivity::class.java))
            }
        }

        buttonStaggeredGridLayoutManager.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(Intent(this@MainActivity, StaggeredLayoutManagerActivity::class.java))
                } else {
                    requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 10)
                }
            } else {
                startActivity(Intent(this@MainActivity, StaggeredLayoutManagerActivity::class.java))
            }
        }
    }
}
