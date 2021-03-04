package com.example.kotlintest.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.kotlintest.R
import com.example.kotlintest.databinding.FragmentArtDetailsBinding
import com.example.kotlintest.util.Status
import com.example.kotlintest.view.ArtDetailsFragmentDirections
import com.example.kotlintest.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class ArtDetailsFragment  @Inject constructor(
        val glide : RequestManager
) :Fragment(R.layout.fragment_art_details) {
    lateinit var viewModel:ArtViewModel

    private var fragmentBinding : FragmentArtDetailsBinding? =null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding=FragmentArtDetailsBinding.bind(view)
        fragmentBinding=binding
        subscribeToObserves()
        binding.artImageView.setOnClickListener{
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToİmageApiFragment())

        }
        val callback=object:OnBackPressedCallback(true)
        {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        binding.saveButton.setOnClickListener{
            viewModel.makeArt(binding.nameText.text.toString(),binding.artistText.text.toString(),binding.yearText.text.toString())
        }
    }
    private fun subscribeToObserves()
    {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            fragmentBinding?.let {
                glide.load(url).into(it.artImageView)
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireActivity(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                }

                Status.LOADING -> {

                }
            }
        })

    }

    override fun onDestroyView() {
        fragmentBinding=null
        super.onDestroyView()
    }
}