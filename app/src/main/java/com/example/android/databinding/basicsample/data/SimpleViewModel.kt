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

package com.example.android.databinding.basicsample.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

/**
 * A simple VM for [com.example.android.databinding.basicsample.ui.PlainOldActivity].
 */
class SimpleViewModel : ViewModel() {
    private val _name = MutableStateFlow("Ada")
    private val _lastName = MutableStateFlow("Lovelace")
    private val _likes = MutableStateFlow(0)

    val name: StateFlow<String> = _name.asStateFlow()
    val lastName: StateFlow<String> = _lastName.asStateFlow()
    
    // Expose structural UI states as continuous Flows mapping business data
    val likesCountString: StateFlow<String> = _likes
        .map { it.toString() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "0")
    
    val isProgressBarVisible: Flow<Boolean> = _likes.map { it > 0 }
    
    val progressPercent: Flow<Int> = _likes.map { (it * 100 / 5).coerceAtMost(100) }

    val popularity: Flow<Popularity> = _likes.map {
        when {
            it > 9 -> Popularity.STAR
            it > 4 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }

    fun onLike() {
        _likes.value = (_likes.value ?: 0) + 1
    }
}

enum class Popularity {
    NORMAL,
    POPULAR,
    STAR
}
