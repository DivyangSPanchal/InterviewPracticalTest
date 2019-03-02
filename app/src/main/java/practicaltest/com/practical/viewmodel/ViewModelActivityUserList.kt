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
import practicaltest.com.practical.utility.UDF
import kotlin.collections.ArrayList

class ViewModelActivityUserList(application: Application) : ViewModelBase(application) {

    var userList: List<ModelUserList> = ArrayList<ModelUserList>()
    var selectedUserList: List<ModelUserList> = ArrayList<ModelUserList>()
    var isloading = ObservableField<Boolean>(false)
    var nextSince = ObservableField<Int>(0)
    var totalPage = 1
    private val liveEvent = SingleLiveEvent<Boolean>()

    fun getParsingDoneEvent(): SingleLiveEvent<Boolean> {
        return liveEvent
    }

    fun callApiUserList(since : String) {
        if(UDF.isOnline(getApplication())) {
            ApiCallFactory.create().getUsers(since)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { isloading.set(false) }
                    .doOnComplete { isloading.set(true) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(Consumer { result ->
                        if (result.isSuccessful && result.body() != null && !result.body()!!.isEmpty()) {
                            /*val header = result.headers().get("Link")
                        val nextLink = header?.split(",")*/
                            if (since.equals("0")) {
                                userList = result.body() as ArrayList<ModelUserList>
                            } else {
                                (userList as ArrayList<ModelUserList>).addAll(result.body() as ArrayList<ModelUserList>)
                                nextSince.set(userList.size + 1)
                            }
                            liveEvent.value = true
                            liveEvent.call()
                        }
                    }, Consumer { error ->
                        showSnackbarMessage(R.string.general_error)
                    })
        }else{
            showSnackbarMessage(R.string.message_no_connection)
        }
    }
}