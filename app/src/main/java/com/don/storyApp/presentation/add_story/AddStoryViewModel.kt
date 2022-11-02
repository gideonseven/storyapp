package com.don.storyApp.presentation.add_story

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.don.storyApp.domain.repository.stories.IStoriesRepository
import com.don.storyApp.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val repository: IStoriesRepository
) : ViewModel() {
    var description: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    val isValidImage: MutableLiveData<Boolean> = MutableLiveData(false)
    val isValidText: MutableLiveData<Boolean> = MutableLiveData(false)
    val stateType: MutableLiveData<StateType> = MutableLiveData(StateType.CONTENT)

    var myFile: File? = null

    fun addStory(
        errorMessage: (String) -> Unit,
        onSuccess: (SimpleNetworkModel) -> Unit
    ) {
        viewModelScope.launch {
            myFile?.let {
                repository.addStory(description.value.orEmpty(), reduceFileImage(it))
                    .collect { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                stateType.value = StateType.CONTENT
                                resource.data?.let(onSuccess)
                            }
                            is Resource.Loading -> {
                                stateType.value = StateType.LOADING
                            }
                            is Resource.Error -> {
                                stateType.value = StateType.ERROR
                                errorMessage(resource.message.orEmpty())
                            }
                        }
                    }
            }
        }
    }
}