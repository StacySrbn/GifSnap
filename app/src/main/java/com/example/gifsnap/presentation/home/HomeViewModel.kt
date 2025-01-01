package com.example.gifsnap.presentation.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.gifsnap.domain.usecases.GetGifListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGifs: GetGifListUseCase
): ViewModel() {

    private val _gifListState = MutableStateFlow(HomeState())
    val gifListState = _gifListState.asStateFlow()

    init {
        getGifList()
    }

    fun refreshData(){
        getGifList()
    }

    private fun getGifList() {
        viewModelScope.launch {
            _gifListState.update {
                it.copy(isLoading = true)
            }
            try {
                val gifsFlow = getGifs().cachedIn(viewModelScope)
                _gifListState.update {
                    it.copy(
                        gifsList = gifsFlow,
                        errorMessage = null,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                val errorMessage = when (e) {
                    is IOException -> "Network error. Check your connection."
                    is HttpException -> {
                        when (e.code()) {
                            400 -> "Bad Request. Please try again."
                            401 -> "Unauthorized. Check your credentials."
                            403 -> "Forbidden. You don't have access to this resource."
                            404 -> "Not Found. The resource doesn't exist."
                            500 -> "Internal Server Error. Please try again later."
                            else -> "Unexpected server error (${e.code()})."
                        }
                    }
                    else -> "An unknown error occurred."
                }
                _gifListState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            }
        }
    }
}
