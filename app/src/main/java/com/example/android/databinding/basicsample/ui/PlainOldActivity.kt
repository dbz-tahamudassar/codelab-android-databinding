/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.databinding.basicsample.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.android.databinding.basicsample.R
import com.example.android.databinding.basicsample.data.Popularity
import com.example.android.databinding.basicsample.data.SimpleViewModel
import com.example.android.databinding.basicsample.databinding.PlainActivityBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.jvm.java


class PlainOldActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(SimpleViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: PlainActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.plain_activity)

        // For elements still using Data Binding (like name/lastname) we can maintain them
        binding.lifecycleOwner = this

        binding.likeButton.setOnClickListener {
            viewModel.onLike()
        }

        // Collect UI state Flows cleanly using lifecycleScope matching modern MVVM principles
        lifecycleScope.launch {
            viewModel.likesCountString.collect { likesStr ->
                binding.likes.text = likesStr
            }
        }

        lifecycleScope.launch {
            viewModel.isProgressBarVisible.collect { isVisible ->
                binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.progressPercent.collect { progress ->
                binding.progressBar.progress = progress
            }
        }

        lifecycleScope.launch {
            viewModel.popularity.collect { popularity ->
                val color = getAssociatedColor(popularity)

                ImageViewCompat.setImageTintList(binding.imageView, ColorStateList.valueOf(color))
                binding.imageView.setImageDrawable(getDrawablePopularity(popularity))
                binding.progressBar.progressTintList = ColorStateList.valueOf(color)
            }
        }
    }

    private fun getAssociatedColor(popularity: Popularity): Int {
        return when (popularity) {
            Popularity.NORMAL -> theme.obtainStyledAttributes(intArrayOf(android.R.attr.colorForeground)).getColor(0, 0x000000)
            Popularity.POPULAR -> ContextCompat.getColor(this, R.color.popular)
            Popularity.STAR -> ContextCompat.getColor(this, R.color.star)
        }
    }

    private fun getDrawablePopularity(popularity: Popularity) = when (popularity) {
        Popularity.NORMAL -> ContextCompat.getDrawable(this, R.drawable.ic_person_black_96dp)
        Popularity.POPULAR, Popularity.STAR -> ContextCompat.getDrawable(this, R.drawable.ic_whatshot_black_96dp)
    }
}
