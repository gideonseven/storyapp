package com.don.storyApp.presentation.stories

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.don.storyApp.domain.model.Story
import com.don.storyApp.domain.repository.auth.IAuthRepository
import com.don.storyApp.domain.repository.stories.IStoriesRepository
import com.don.storyApp.util.StateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class StoriesViewModel @Inject constructor(
    private val repository: IStoriesRepository,
    private val authRepository: IAuthRepository
) : ViewModel() {

    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

    fun getStories(
        errorMessage: (String) -> Unit,
        onSuccess: (PagingData<Story>) -> Unit
    ) {
        viewModelScope.launch {
            repository.getPagingStories().collect {
                onSuccess(it)
            }
        }
    }

    fun logout() {
        authRepository.doLogOut()
    }

    fun hasAuthCode() = authRepository.hasAccessToken()
}