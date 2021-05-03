package com.dlab.sinsungo

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dlab.sinsungo.data.model.Recipe

object CustomBindingAdapter {
    @JvmStatic
    @BindingConversion
    fun convertBooleanToVisibility(visible: Boolean): Int {
        return if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("exDateFormat")
    fun dateFormatting(view: TextView, value: Date) {
        view.text = SimpleDateFormat("yyyy-MM-dd", Locale.KOREAN).format(value)
    }

    @JvmStatic
    @BindingAdapter("tvRemain", "tvExdateType")
    fun setExDateMessage(view: TextView, value: Long, type: String) {
        if (type == "유통기한") {
            if (value >= 0) view.text = "%s까지 %d일 남았습니다.".format(type, value)
            else view.text = "%s에서 %d일 지났습니다.".format(type, abs(value))
        } else view.text = "%s에서 %d일 지났습니다.".format(type, abs(value))
    }

    @JvmStatic
    @BindingAdapter("remain", "exdateType", "drawableGood", "drawableBad")
    fun setProgressBarState(
        view: ProgressBar,
        value: Long,
        type: String,
        drawableGood: Drawable,
        drawableBad: Drawable,
    ) {
        if (type == "유통기한") {
            when {
                value >= 10 -> view.progress = 25
                value in 5..9 -> view.progress = 50
                value in 3..4 -> view.progress = 75
                value in 1..2 -> view.progress = 90
                value <= 0 -> view.progress = 100
            }
        } else {
            when {
                value == 0L -> view.progress = 5
                value in -4..-1 -> view.progress = 25
                value in -9..-5 -> view.progress = 50
                value in -14..-10 -> view.progress = 75
                value <= -15 -> view.progress = 90
            }
        }
        if (view.progress > 50) view.progressDrawable = drawableBad
        else view.progressDrawable = drawableGood
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        Glide.with(imageView.context).load(url).error(R.drawable.image_empty_refrigerator).thumbnail(0.1f)
            .into(imageView)
    }

    @BindingAdapter(*["recipeData", "scrollTop"])
    @JvmStatic
    fun bindRecipe(recyclerView: RecyclerView, recipes: ArrayList<Recipe>?, scrollTop: Boolean) {
        val adapter = recyclerView.adapter as RecipeAdapter

        adapter.submitList(recipes) {
            if (scrollTop) {
                recyclerView.scrollToPosition(0)
            }
        }
    }
}
