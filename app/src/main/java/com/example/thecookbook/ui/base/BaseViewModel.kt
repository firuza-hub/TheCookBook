package com.example.thecookbook.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.thecookbook.utils.SingleLiveEvent

abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    //val navigationCommand: SingleLiveEvent<NavigationCommand> = SingleLiveEvent()
    val showErrorMessage: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBar: SingleLiveEvent<String> = SingleLiveEvent()
    val showSnackBarInt: SingleLiveEvent<Int> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String> = SingleLiveEvent()
    val showLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val showNoData: MutableLiveData<Boolean> = MutableLiveData()
    val showNoConnection: SingleLiveEvent<Boolean> = SingleLiveEvent()

}