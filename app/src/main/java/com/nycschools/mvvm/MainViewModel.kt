package com.nycschools.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nycschools.data.network.Status.*
import com.nycschools.mvvm.repo.NycRepository
import com.nycschools.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * View model is responsible will provide the timely updates to UX layer.
 */
@HiltViewModel
class MainViewModel @Inject constructor(var nycRepository: NycRepository) : ViewModel() {

    private val _schoolsList: MutableLiveData<UIState> = MutableLiveData()
    val schoolsList: LiveData<UIState> = _schoolsList

    private val _schoolDetailScores: MutableLiveData<UIState> = MutableLiveData()
    val schoolDetailScores: LiveData<UIState> = _schoolDetailScores

    fun getNycSchools() {
        _schoolsList.value = UIState.Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = nycRepository.getNewyorkSchools()
            _schoolsList.postValue(when (response.status) {
                SUCCESS -> {
                    response.data?.let {
                        nycRepository.insertNycHighSchoolRecords(it)
                        getSatScoreForSchool()
                        UIState.NYCSchoolLists(nycRepository.getAllRecords())
                    }
                }
                ERROR -> {
                    UIState.Error(response.throwable as Exception?)
                }
            })
        }
    }

    private fun getSatScoreForSchool() {
        viewModelScope.launch {
            val response = nycRepository.getSatScoresForSchools()
            _schoolDetailScores.postValue(UIState.Loading(false))
            when (response.status) {
                SUCCESS -> {
                    response.data?.let {
                        nycRepository.updateNycSchoolRecords(it)
                    }
                }
                else -> {}
            }
        }
    }

    fun getSchoolRecord(schoolId: String) {
        viewModelScope.launch {
            _schoolDetailScores.postValue(
                UIState.NycSchoolRecord(
                    nycRepository.getNycSchoolRecord(
                        schoolId
                    )
                )
            )
        }
    }
}