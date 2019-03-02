package practicaltest.com.practical.viewmodel

import android.app.Application
import android.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import practicaltest.com.practical.R
import practicaltest.com.practical.base.ViewModelBase
import practicaltest.com.practical.model.ModelUserList
import practicaltest.com.practical.network.ApiCallFactory
import practicaltest.com.practical.utility.SingleLiveEvent
import kotlin.collections.ArrayList

class ViewModelActivitySelectedUserList(application: Application) : ViewModelBase(application) {

    var selectedUserList: List<ModelUserList> = ArrayList<ModelUserList>()
    var isloading = ObservableField<Boolean>(false)
    private val liveEvent = SingleLiveEvent<Boolean>()

    fun getParsingDoneEvent(): SingleLiveEvent<Boolean> {
        return liveEvent
    }
}