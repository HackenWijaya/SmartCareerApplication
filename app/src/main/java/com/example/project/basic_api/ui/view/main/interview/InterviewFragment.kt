package com.example.project.basic_api.ui.view.main.interview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.example.project.R
import com.example.project.databinding.FragmentInterviewBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class InterviewFragment : Fragment() {

    private lateinit var binding: FragmentInterviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        binding = FragmentInterviewBinding.inflate(inflater, container, false)

        // List of YouTube video IDs
        val videoIds = listOf("R3I3hm27G1U", "1kycNo14cIQ", "AqrBLVg0T3U")
        // List of YouTubePlayerView objects (using ViewBinding)
        val playerViews = listOf(
            binding.youtubePlayerView1,
            binding.youtubePlayerView2,
            binding.youtubePlayerView3
        )

        // Loop through each player view to initialize and set up listeners
        for (i in playerViews.indices) {
            val youtubePlayerView = playerViews[i]
            lifecycle.addObserver(youtubePlayerView)  // Add lifecycle observer for each player view

            youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoIds[i], 0f)  // Load each video by ID
                }
            })
        }
        setupToolbar()
        return binding.root
    }
    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.title = "Tips dan Trik Interview"
        toolbar.setNavigationIcon(R.drawable.ic_back2)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
