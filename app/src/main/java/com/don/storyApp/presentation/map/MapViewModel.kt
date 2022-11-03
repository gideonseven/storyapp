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


    fun getListLocation(
        onSuccess: (List<Story>) -> Unit
    ) {
        viewModelScope.launch {
            repository.getListLocation().collect {
                onSuccess(it)
            }
        }
    }
}