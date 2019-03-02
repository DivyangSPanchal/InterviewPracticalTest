package practicaltest.com.practical.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.ViewModel

import io.reactivex.disposables.CompositeDisposable
import practicaltest.com.practical.utility.SingleLiveEvent

/**
 * Created by Divyang.
 */

open class ViewModelBase(application: Application) : AndroidViewModel(application) {
    private val mCompositeDisposable = CompositeDisposable()
    val snackbarMessage = SingleLiveEvent<Any>()
    val loadingDialog = SingleLiveEvent<Boolean>()
    /**
     * Getter method for get HideKeyBoar Event
     *
     * @return
     */
    val hideKeyBoardEvent = SingleLiveEvent<Void>()

    /**
     * Method for show snack bar
     *
     * @param message
     */
    fun showSnackbarMessage(message: Any) {
        snackbarMessage.value = message
    }


    fun showLoadingDialog(isShow: Boolean) {
        loadingDialog.value = isShow
        loadingDialog.call()
    }


    /**
     * Method for hide keyboard
     */
    fun hideKeyboard() {
        hideKeyBoardEvent.call()
    }


    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }
}
