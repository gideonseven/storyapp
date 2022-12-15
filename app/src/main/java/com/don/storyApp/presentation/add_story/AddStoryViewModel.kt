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
    val errorMessage: MutableLiveData<String> = MutableLiveData(Constant.TEXT_BLANK)
    val simpleNetworkModel: MutableLiveData<SimpleNetworkModel> =
        MutableLiveData(SimpleNetworkModel())

    var lat: Double = 0.0
    var lon: Double = 0.0

    var myFile: File? = null

    fun addStory() {
        viewModelScope.launch {
            myFile?.let {
                repository.addStory(
                    description.value.orEmpty(),
                    reduceFileImage(it),
                    lat,
                    lon
                )
                    .collect { resource ->
                        when (resource) {
                            is Resource.Success -> {
                                stateType.value = StateType.CONTENT
                                resource.data?.let { model ->
                                    simpleNetworkModel.value = model
                                    errorMessage.value = model.message.orEmpty()
                                }
                            }
                            is Resource.Loading -> {
                                stateType.value = StateType.LOADING
                            }
                            is Resource.Error -> {
                                stateType.value = StateType.ERROR
                                errorMessage.value = resource.message.orEmpty()
                            }
                        }
                    }
            }
        }
    }

    fun addStoryDummy() {
        viewModelScope.launch {
            val file = File("")
            repository.addStory(
                description.value.orEmpty(),
                file,
                lat,
                lon
            )
                .collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            stateType.value = StateType.CONTENT
                            resource.data?.let { model ->
                                simpleNetworkModel.value = model
                                errorMessage.value = model.message.orEmpty()
                            }
                        }
                        is Resource.Loading -> {
                            stateType.value = StateType.LOADING
                        }
                        is Resource.Error -> {
                            stateType.value = StateType.ERROR
                            errorMessage.value = resource.message.orEmpty()
                        }
                    }
                }
        }
    }
}