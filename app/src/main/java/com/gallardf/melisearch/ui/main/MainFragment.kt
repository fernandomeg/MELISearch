package com.gallardf.melisearch.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.gallardf.melisearch.R
import com.gallardf.melisearch.databinding.FragmentMainBinding
import com.gallardf.melisearch.domain.models.ProductModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? =null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ProductsViewModel>()
    private val navController by lazy { findNavController() }
    private val productsAdapter by lazy { ProductsAdapter(requireContext(), emptyList(),::onItemSelected) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.apply {
            adapter = productsAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initSearchView()
    }

    private fun initObservers(){
        viewModel.run {
            errorAlert.observe(viewLifecycleOwner, Observer { event ->
                event.getContentIfNotHandled()?.let {
                    setConnectivityScreen()
                }
            })

            loading.observe(viewLifecycleOwner,Observer{
                binding.fragmentMainEmptyScreen.root.visibility = View.GONE
                binding.fragmentMainShimmerLoading.visibility = if(it) View.VISIBLE else View.GONE
                binding.fragmentMainShimmerLoading.showShimmer(it)
            })

            products.observe(viewLifecycleOwner,Observer{
                productsAdapter.updateList(it)
                if(it.isNotEmpty()){
                    binding.fragmentMainEmptyScreen.root.visibility = View.GONE
                }else{
                    binding.fragmentMainEmptyScreen.viewEmptyMainProductsMessage.text = getString(R.string.empty_search_message)
                    binding.fragmentMainEmptyScreen.root.visibility = View.VISIBLE
                }

            })
        }
    }

    private fun initSearchView(){
        binding.fragmentMainSearch.clearFocus()
        binding.fragmentMainSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.fragmentMainSearch.clearFocus()
                if(!query.isNullOrEmpty())
                    viewModel.searchProductsByQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun onItemSelected(product:ProductModel) {
        navController.navigate(
            R.id.action_main_fragment_to_product_detail_fragment,
            bundleOf("productId" to product.id)
        )
    }

    private fun setConnectivityScreen(){
        binding.fragmentMainEmptyScreen.root.visibility = View.VISIBLE
        binding.fragmentMainEmptyScreen.viewEmptyMainProductsIcon
            .setImageResource(R.drawable.ic_error_connection)
        binding.fragmentMainEmptyScreen.viewEmptyMainProductsMessage.text =
            getString(R.string.error_connectivity_message)
    }

    override fun onDestroyView() {
        binding.fragmentMainRecycler.adapter = null
        _binding =null
        super.onDestroyView()
    }
}