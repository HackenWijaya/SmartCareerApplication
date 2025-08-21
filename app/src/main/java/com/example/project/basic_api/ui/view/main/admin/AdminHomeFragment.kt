package com.example.project.basic_api.ui.view.main.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.ListFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.basic_api.data.network.RetrofitInstance
import com.example.project.basic_api.data.repository.NewsRepository
import com.example.project.basic_api.data.repository.UserRepository
import com.example.project.basic_api.ui.adapter.NewsAdapter
import com.example.project.basic_api.ui.view.main.home.AutoSliderAdapter
import com.example.project.basic_api.ui.view.main.interview.InterviewFragment
import com.example.project.basic_api.ui.view.main.panduancv.PanduanCVFragment
import com.example.project.basic_api.ui.view.main.rekomendasipekerjaan.RekomendasiFragment
import com.example.project.basic_api.ui.viewmodel.NewsViewModel
import com.example.project.basic_api.ui.viewmodel.UserViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.basic_api.utils.ViewModelFactory
import com.example.project.databinding.FragmentAdminHomeBinding
import com.example.project.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class AdminHomeFragment : Fragment() {

    private var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AdminHomeFragment


    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory(UserViewModel::class.java) {
            val repository = UserRepository(RetrofitInstance.getJsonPlaceholderApi())
            UserViewModel(repository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)

        setupAutoSlider(binding)
        setupGridMenu(binding)
        setupApiNewsHorizonatal(binding)
        return binding.root
    }
    private fun setupApiNewsHorizonatal(binding: FragmentAdminHomeBinding){
        val newsAdapter = NewsAdapter()
        binding.newsHortList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.newsHortList.adapter = newsAdapter
        val apiService = RetrofitInstance.getApiService()
        val repository = NewsRepository(apiService)

        val factory = ViewModelFactory(NewsViewModel::class.java) { NewsViewModel(repository) }
        val viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)



        userViewModel.getUsers(requireContext())
        userViewModel.data.observe(requireActivity()){ resource ->
            when(resource){
                is Resource.Empty->{
                    binding.loadingNewsVertical.root.visibility = View.GONE
                    binding.emptyNewsVertical.root.visibility = View.VISIBLE
                    binding.errorNewsVertical.root.visibility = View.GONE

                    Log.d("a","a")
                }
                is Resource.Error->{
                    binding.loadingNewsVertical.root.visibility = View.GONE
                    binding.emptyNewsVertical.root.visibility = View.GONE
                    binding.errorNewsVertical.root.visibility = View.VISIBLE

                    Log.d("b","b")
                }
                is Resource.Loading->{
                    binding.loadingNewsVertical.root.visibility = View.VISIBLE
                    binding.emptyNewsVertical.root.visibility = View.GONE
                    binding.errorNewsVertical.root.visibility = View.GONE
                    Log.d("c","c")
                }
                is Resource.Success->{
                    binding.loadingNewsVertical.root.visibility = View.GONE
                    binding.emptyNewsVertical.root.visibility = View.GONE
                    binding.errorNewsVertical.root.visibility = View.GONE
                    Log.d("d","d")
                    viewModel.getNews(
                        query = "Lowongan Kerja",
                        from = "2024-12-22", // Format tanggal sudah benar
                        to = "2024-11-22",   // Format tanggal sudah benar
                        sortBy = "publishedAt",
                        apiKey = "a62912bb4a074bcd9e38310453a01b48"
                    ).observe(viewLifecycleOwner) { articles ->
                        if (articles.isNullOrEmpty()) {
                            Log.d("HomeFragment", "No articles found")
                            Toast.makeText(requireContext(), "No news available", Toast.LENGTH_SHORT).show()
                        } else {
                            newsAdapter.setArticles(articles)
                        }
                    }
                }
            }
//
        }
        binding.errorNewsVertical.retryButton.setOnClickListener{
            userViewModel.getUsers(requireContext(), true)
        }

        binding.newsHortList.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setupAutoSlider(binding: FragmentAdminHomeBinding) {
        val images = listOf(
            R.drawable.lo,
            R.drawable.ic_home,
            R.drawable.ic_menu

        )

        val imagesUrl = listOf(
            "https://images.unsplash.com/photo-1532009324734-20a7a5813719?w=1024",
            "https://images.unsplash.com/photo-1524429656589-6633a470097c?w=1024",
            "https://images.unsplash.com/photo-1530224264768-7ff8c1789d79?w=1024"
        )


        binding.autoSlider.adapter = AutoSliderAdapter(imagesUrl, binding.autoSlider)
        binding.dotIndicator.attachTo(binding.autoSlider)
    }

    private fun setupGridMenu(binding : FragmentAdminHomeBinding){


        binding.includedGridMenuHome2.cardMenu1.setOnClickListener() {
            val rekomendasi = KelolaFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, rekomendasi)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.includedGridMenuHome2.cardMenu2.setOnClickListener()  {
            val list = LamaranAdminFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, list)
            transaction.addToBackStack(null)
            transaction.commit() }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Prevent memory leaks
    }

//    private fun setupNewsHorizontal(binding: FragmentHomeBinding){
//        val newsitem = listOf(
//            NewsHorizontalModel("News A", "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=1024"),
//            NewsHorizontalModel("News B","https://images.unsplash.com/photo-1542345812-d98b5cd6cf98?w=1024"),
//            NewsHorizontalModel("News C", "https://images.unsplash.com/photo-1481277542470-605612bd2d61?w=1024"),
//            NewsHorizontalModel("News D","https://images.unsplash.com/photo-1519985176271-adb1088fa94c?w=1024"),
//            NewsHorizontalModel("News F", "https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=1024")
//
//        )
//
//        binding.newsHortList.adapter = NewsHorizontalAdapter(newsitem)
//        binding.newsHortList.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
//    }
}
