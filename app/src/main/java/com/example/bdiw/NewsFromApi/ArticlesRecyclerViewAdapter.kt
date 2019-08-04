package com.example.bdiw.NewsFromApi

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.bdiw.BdiwBrowser.BdiwBrowser
import com.example.bdiw.R
import com.example.bdiw.RealTimeNewsParser.Results
import kotlinx.android.synthetic.main.articles_recycler_item.view.*

class ArticlesRecyclerViewAdapter(data:List<com.example.bdiw.RealTimeNewsParser.Results>, context:Context) : RecyclerView.Adapter<ArticlesRecyclerViewAdapter.ViewHolder>() {

    lateinit var data:List<Results>
    lateinit var context: Context

    init {
        this.data = data
        this.context = context
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val inflater = LayoutInflater.from(p0.context)
        val view = inflater.inflate(R.layout.articles_recycler_item,p0,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        Log.e("newsRecycler",data.get(p1).title)

        val curentArticle = data.get(p1)

        p0.articleTitleView.setText(curentArticle.title)
        p0.articleAbstractView.setText(curentArticle.abstract)
        val imgForshowWidth = curentArticle.multimedia.last().width
        val imgForshowHeight = curentArticle.multimedia.last().height
        Log.e("imgPropertys","${imgForshowWidth}:${imgForshowHeight}")
        Glide
            .with(p0.articleImageView.context)
            .asBitmap()
            .load(curentArticle.multimedia.last().url)
            .into(object :CustomTarget<Bitmap>(imgForshowWidth,imgForshowHeight){
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    p0.articleImageView.setImageBitmap(resource)
                }

            })
        p0.showMoreView.setOnClickListener {
            val intent = Intent(this.context,BdiwBrowser::class.java)
            intent.setData(Uri.parse(curentArticle.url))
            this.context.startActivity(intent)
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val articleTitleView = itemView.articleTittleView
        val articleImageView = itemView.articleImageView
        val articleAbstractView = itemView.articleAbstractView
        val showMoreView = itemView.showMoreView

    }
}