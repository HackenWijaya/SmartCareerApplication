package com.example.project.basic_api.ui.view.main.message

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project.R
import com.example.project.basic_api.data.model.ProductPostRequest
import com.example.project.basic_api.data.network.RetrofitInstance
import com.example.project.basic_api.data.repository.ProductRepository
import com.example.project.basic_api.data.repository.UserRepository
import com.example.project.basic_api.ui.view.main.home.NewsHorizontalAdapter
//import com.example.project.basic_api.ui.view.main.home.NewsHorizontalAdapter
import com.example.project.basic_api.ui.view.main.home.NewsHorizontalModel
import com.example.project.basic_api.ui.viewmodel.ProductViewModel
import com.example.project.basic_api.ui.viewmodel.UserViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.basic_api.utils.ViewModelFactory
import com.example.project.databinding.FragmentHomeBinding
import com.example.project.databinding.FragmentNotifikasiBinding
import com.google.android.material.snackbar.Snackbar

class NotifikasiFragment : Fragment() {

    private var _binding: FragmentNotifikasiBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : NewsHorizontalAdapter

    private val productViewModel: ProductViewModel by activityViewModels {
        ViewModelFactory(ProductViewModel::class.java) {
            val repository = ProductRepository(RetrofitInstance.getCrudApi())
            ProductViewModel(repository)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotifikasiBinding.inflate(inflater,container,false)

        adapter = NewsHorizontalAdapter(emptyList())
        binding.productList.adapter = adapter
        binding.productList.layoutManager = LinearLayoutManager(this.context)

        getProduct()

        return binding.root
    }
    private fun getProduct(){
        productViewModel.getProducts(requireContext())
        productViewModel.data.observe(requireActivity()) { resource ->
            when (resource) {
                is Resource.Empty -> {
                    Log.d("EMPTY DATA", "Data Tidak Tersedia")
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.emptyProduct.root.visibility = View.VISIBLE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.GONE
                }

                is Resource.Error -> {
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.VISIBLE
                    binding.productList.visibility = View.GONE
                    binding.errorProduct.errorMessage.text = resource.message
                }

                is Resource.Loading -> {
                    binding.loadingProduct.root.visibility = View.VISIBLE
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.GONE
                }

                is Resource.Success -> {
                    binding.loadingProduct.root.visibility = View.GONE
                    binding.emptyProduct.root.visibility = View.GONE
                    binding.errorProduct.root.visibility = View.GONE
                    binding.productList.visibility = View.VISIBLE

                    val menuItems = resource.data!!.items.mapIndexed { index, data ->
                        NewsHorizontalModel(
                            data.name,
                            "https://images.unsplash.com/photo-1542345812-d98b5cd6cf98?w=1024"
                        )
                    }

                    adapter.updateDataSet(menuItems)
                }
            }
        }
    }
    private fun createProduct() {
        val name = "323232323232323Zero Zoro"
        val desc = "Ini Deskripsi Komputer"
        val price = 1450000

        val products = listOf(
            ProductPostRequest(
                name = name,
                description = desc,
                price = price
            )
        )
        productViewModel.createProduct(requireContext(), products)
        productViewModel.createStatus.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Show a loading indicator for create operation
                }

                is Resource.Success -> {


                    Snackbar.make(
                        binding.root,
                        "Product created successfully!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                is Resource.Error -> {
                    Snackbar.make(
                        binding.root,
                        resource.message ?: "Failed to create product.",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is Resource.Empty -> TODO()
            }
        }
    }
}