package practicaltest.com.practical.utility

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.support.annotation.MainThread
import android.util.Log

import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Divyang.
 */

class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)

    /**
     * Method call for listen onChanged() method
     * @param owner
     * @param observer
     */
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {

        if (hasActiveObservers()) {
            Log.e("SingleLiveEvent", "Multiple observers registered but only one will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, Observer { t ->
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        postValue(null)
    }
}
