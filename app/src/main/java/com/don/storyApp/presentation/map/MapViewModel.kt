package com.don.storyApp.presentation.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.domain.model.Story
import com.don.storyApp.domain.repository.stories.IStoriesRepository
import com.don.storyApp.util.StateType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Created by gideon on 03 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: IStoriesRepository
) : ViewModel() {
    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)
    val listStory: MutableLiveData<List<Story>> = MutableLiveData<List<Story>>()

    fun getListLocation() {
        viewModelScope.launch {
            repository.getListLocation().collect {
                if (it.isNotEmpty()) {
                    stateType.value = StateType.CONTENT
                    listStory.value = it
                } else {
                    stateType.value = StateType.ERROR
                    listStory.value = listOf()
                }
            }
        }
    }
}