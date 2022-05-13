package com.example.pretest_background_and_file_processing_farhan_fadhilah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pretest_background_and_file_processing_farhan_fadhilah.databinding.ActivityVideoHandleBinding
import com.example.pretest_background_and_file_processing_farhan_fadhilah.utils.Utils
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util
import javax.sql.DataSource

class VideoHandleActivity : AppCompatActivity() {
    private var mPlayer: ExoPlayer? = null
    private lateinit var binding: ActivityVideoHandleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoHandleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initExoPlayer()
    }

    private fun initExoPlayer(){
        mPlayer = ExoPlayer.Builder(this).build()
        binding.playerView.player = mPlayer
        mPlayer!!.playWhenReady = true
        mPlayer!!.setMediaSource(buildMediaSource())
        mPlayer!!.prepare()
    }

    private fun buildMediaSource(): MediaSource {
        val dataSourceFactory: com.google.android.exoplayer2.upstream.DataSource.Factory = DefaultHttpDataSource.Factory()

        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Utils.videoUrl()))

        return mediaSource
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initExoPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || mPlayer == null) {
            initExoPlayer()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        releasePlayer()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        finish()
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }else{
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (mPlayer == null) {
            return
        }
        mPlayer!!.release()
        mPlayer = null
    }
}