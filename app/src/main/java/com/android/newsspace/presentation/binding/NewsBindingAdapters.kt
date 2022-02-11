package com.android.newsspace.presentation.binding

import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.android.newsspace.R
import com.android.newsspace.data.model.News
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@BindingAdapter("newsTitle")
fun TextView.setNewsTitle(news: News?) {
    news?.let {
        text = it.title
    }
}

@BindingAdapter("newsSummary")
fun TextView.setNewsSummary(news: News?) {
    news?.let {
        text = news.summary
    }
}


@BindingAdapter("newsImage")
fun ImageView.setImage(news: News?) {
    news?.let {

        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(this).load(news.imageUrl)
            .placeholder(circularProgressDrawable).into(this)

    }
}

@BindingAdapter("itemHasLaunch")
fun Chip.setHasLaunch(news: News?) {
    news?.let {
        visibility = if (it.hasLaunch()) {
            View.VISIBLE
        } else {
            View.GONE
        }
        val count = news.getLaunchCount()
        this.text = resources.getQuantityString(R.plurals.numberOfLaunchEvents, count, count)
    }
}



/**
 * Esse adapter converte a data em formato String usando a classe Instant
 * e depois formata para o padrão dd-mm-aaaa.
 */
@RequiresApi(Build.VERSION_CODES.O)
@BindingAdapter("newsPublishedDate")
fun Chip.setUpdate(news: News?) {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu")
        .withZone(ZoneId.from(ZoneOffset.UTC))
    with(formatter) {
        news?.let {
            val date = Instant.parse(news.publishedAt)
            text = this.format(date)
        }
    }
}