package practicaltest.com.practical.utility

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.View
import android.widget.TextView
import practicaltest.com.practical.R

object UDF {
    /**
     * To check for internet availability status
     *
     * @param context the Context to access System Service
     * @return a boolean value (TRUE or FALSE)
     */
    fun isOnline(context: Context): Boolean {
        val cm = context.getSystemService(Context
                .CONNECTIVITY_SERVICE) as ConnectivityManager
        var result = false
        try {
            if (cm != null && cm.activeNetworkInfo != null && cm.activeNetworkInfo
                            .isAvailable && cm.activeNetworkInfo.isConnected) {
                result = true
            } else {
                result = false
            }
        } catch (e: Exception) {

        }

        return result
    }

    fun showDialogWithPositiveNegativeButton(context: Context,
                                             title: Int?, msg: Int?, positiveButton: Int?, positiveClickListener: DialogInterface.OnClickListener, negativeButton: Int?, negativeClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {

        val alertdialog = AlertDialog.Builder(context)
        alertdialog.setTitle(context.resources.getString(title!!))
        alertdialog.setMessage(context.resources.getString(msg!!))
        alertdialog.setIcon(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
        alertdialog.setPositiveButton(context.resources.getString(positiveButton!!), positiveClickListener)
        alertdialog.setNegativeButton(context.resources.getString(negativeButton!!), negativeClickListener)
        alertdialog.setCancelable(false)
        val dialog = alertdialog.show()
        val messageText = dialog.findViewById<View>(android.R.id.message) as TextView?
        messageText!!.gravity = Gravity.CENTER_HORIZONTAL
        return alertdialog
    }

}