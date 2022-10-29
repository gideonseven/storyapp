package com.don.storyApp.presentation.stories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.data.remote.dto.StoryResponse
import com.don.storyApp.domain.model.Story
import com.don.storyApp.domain.repository.auth.IAuthRepository
import com.don.storyApp.domain.repository.stories.IStoriesRepository
import com.don.storyApp.util.Resource
import com.don.storyApp.util.SimpleNetworkModel
import com.don.storyApp.util.StateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class StoriesViewModel @Inject constructor(
    private val repository: IStoriesRepository
) : ViewModel() {

    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

    fun getStories(
        errorMessage: (String) -> Unit,
        onSuccess: (List<Story>) -> Unit
    ) {
        viewModelScope.launch {
            repository.getStories().collect {
                when (it) {
                    is Resource.Success -> {
                        Timber.e("== RESPONSE Success")
                        Timber.e(
                            "== RESPONSE ${it.data}"
                        )
                        stateType.value = StateType.CONTENT
                        it.data?.let(onSuccess)
                    }
                    is Resource.Loading -> {
                        Timber.e("== RESPONSE Loading")
                        stateType.value = StateType.LOADING
                    }
                    is Resource.Error -> {
                        Timber.e("== RESPONSE Error")
                        Timber.e(
                            "== RESPONSE ${
                                it.message
                            }"
                        )
                        stateType.value = StateType.ERROR
                        errorMessage(it.message.orEmpty())
                    }
                }
            }
        }
    }
}