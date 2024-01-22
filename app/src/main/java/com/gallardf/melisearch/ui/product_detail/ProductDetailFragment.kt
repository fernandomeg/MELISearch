package com.gallardf.melisearch.ui.product_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.gallardf.melisearch.R
import com.gallardf.melisearch.databinding.FragmentProductDetailBinding
import com.gallardf.melisearch.utils.formatToCurrency
import com.gallardf.melisearch.utils.instantiateDataProgressBar
import com.gallardf.melisearch.utils.showErrorToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? =null
    private val binding get() = _binding!!

    private val productDetailViewModel by viewModels<ProductsDetailViewModel>()
    private val args by navArgs<ProductDetailFragmentArgs>()
    private val progressBar by lazy { instantiateDataProgressBar(getString(R.string.product_detail_loading_message)) }

    private val carrouselImageAdapter by lazy { CarrouselAdapter(requireContext(),emptyList()) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args.let {
            productDetailViewModel.getDetail(it.productId)
            productDetailViewModel.getDescription(it.productId)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater,container,false)
        return binding.apply {
            adapter = carrouselImageAdapter
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers(){
        productDetailViewModel.run {
            productDetail.observe(viewLifecycleOwner, Observer {
                binding.fragmentProductDetailTitle.text = it.title
                binding.fragmentProductDetailPrice.text = it.price?.formatToCurrency()
                binding.fragmentProductDetailAddress.text = it.generalAddress

                if(!it.pictures.isNullOrEmpty()){
                    carrouselImageAdapter.updateList(it!!.pictures!!)
                }
            })

            productDescription.observe(viewLifecycleOwner, Observer {
                binding.fragmentProductDetailDescription.text =
                    getString(R.string.product_detail_description_title,it)
            })

            snackBarText.observe(viewLifecycleOwner, Observer {event->
                event.getContentIfNotHandled()?.let {
                    binding.fragmentProductDetailInfoContainer!!.visibility = View.GONE
                    setConnectivityScreen()
                    showErrorToast(it)
                }
            })

            loading.observe(viewLifecycleOwner, Observer { showLoading ->
                if(showLoading) progressBar.show() else progressBar.dismiss()
            })
        }
    }

    private fun setConnectivityScreen(){
        binding.fragmentProductDetailEmptyScreen!!.root.visibility = View.VISIBLE
        binding.fragmentProductDetailEmptyScreen!!.viewEmptyMainProductsIcon
            .setImageResource(R.drawable.ic_error_connection)
        binding.fragmentProductDetailEmptyScreen!!.viewEmptyMainProductsMessage.text =
            getString(R.string.error_connectivity_message)
    }

    override fun onDestroyView() {
        binding.fragmentProductDetailRecycler?.adapter = null
        _binding =null
        super.onDestroyView()
    }
}