package practicaltest.com.practical.network


import com.google.gson.JsonObject
import io.reactivex.Observable
import practicaltest.com.practical.model.ModelUserList
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by Divyang.
 */

interface ApiHelper {
    @GET("users")
    abstract fun getUsers(@Query("since") page : String): Observable<Response<ArrayList<ModelUserList>>>
}
