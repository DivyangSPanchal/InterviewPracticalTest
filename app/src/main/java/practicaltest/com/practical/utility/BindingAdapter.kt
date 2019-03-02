package practicaltest.com.practical.utility

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import practicaltest.com.practical.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_vector_avatar).into(imageView)
}