package com.example.bdiw.NewsFromApi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bdiw.BdiwBrowser.BdiwBrowser
import com.example.bdiw.R
import kotlinx.android.synthetic.main.articles_recycler_item.view.*

class ArchiveArticlesRecyclerViewAdapter(data:List<com.example.bdiw.NewsJsonParserModel.Results>, context:Context) : RecyclerView.Adapter<ArchiveArticlesRecyclerViewAdapter.ViewHolder>() {

    lateinit var data:List<com.example.bdiw.NewsJsonParserModel.Results>
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
        Glide
            .with(p0.articleImageView.context)
            .load(curentArticle.media.get(0).`media-metadata`.get(0).url)
            .into(p0.articleImageView)
        p0.showMoreView.setOnClickListener {
            val intent = Intent(this.context,BdiwBrowser::class.java)
            intent.data = Uri.parse(curentArticle.url)
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