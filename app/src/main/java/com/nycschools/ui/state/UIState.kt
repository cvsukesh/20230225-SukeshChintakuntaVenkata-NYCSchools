package com.nycschools.ui.state

import com.nycschools.data.model.NycSchool

sealed class UIState {
    data class Loading(var loading: Boolean) : UIState()
    data class NYCSchoolLists(var listOfItems: List<NycSchool>) : UIState()
    data class Error(var exception: java.lang.Exception?) : UIState()
    data class NycSchoolRecord(var nycSchool: NycSchool) : UIState()
}