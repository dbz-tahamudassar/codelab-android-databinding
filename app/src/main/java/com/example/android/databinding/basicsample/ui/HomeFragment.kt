package com.example.android.databinding.basicsample.ui
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.Popularity
import com.example.android.databinding.basicsample.data.SimpleViewModel
import com.example.android.databinding.basicsample.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment: Fragment(R.layout.fragment_home) //modern way to do ts, google boilerplate, llms usually gives outdated method
{
    private val viewModel: SimpleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentHomeBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.likeButton.setOnClickListener {
            viewModel.onLike()
        }
        binding.detail.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }



    }
}