package com.example.gifsnap.presentation.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifsnap.util.Resource
import com.example.gifsnap.domain.usecases.GetSingleGifUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getGif: GetSingleGifUseCase,
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    fun loadGifById(gifId: String) {
        getSingleGif(gifId)
    }

    fun refreshData(gifId: String){
        getSingleGif(gifId)
    }

    private fun getSingleGif(gifId: String){
        Log.d("Error Check", "View Model id $gifId")

        viewModelScope.launch {
            getGif(gifId).collectLatest { result->
                when(result){
                    is Resource.Loading -> {
                        _detailState.update {
                            it.copy(isLoading = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { gif->
                            Log.d("Error Check", "View Model $gif")
                            _detailState.update {
                                it.copy(
                                    gif = gif,
                                    isLoading = false
                                )
                            }
                        }
                    }
                    is Resource.Error -> {
                        _detailState.update {
                            it.copy(
                                errorMessage = result.message,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}