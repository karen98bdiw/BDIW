package com.example.bdiw

import android.content.Context
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.bdiw.RealTimeNewsParser.RealTimeNewsBaseModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Utils {

    companion object {

        val NEWS_API_BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/emailed/"
        val REAL_TIME_NEWS_BASE_URL = "https://api.nytimes.com/svc/news/v3/content/all/"
        val NEWS_API_KEY = "eqPZhtMDSGS1MHqeeWALczfD1G8qCaGT"

        var REQUEST_CALLED = false

        fun constructMainViewPager(viewPager: ViewPager, fm: FragmentManager){
            val mainPagesAdapter = MainPagesAdapter(fm)
            mainPagesAdapter.addFragment(Fragment1(),"first")
            mainPagesAdapter.addFragment(Fragment2(),"secont")
            mainPagesAdapter.addFragment(Fragment3(),"third")
            viewPager.adapter = mainPagesAdapter
        }

        fun doRequestForCurentArticles(context:Context,articlesRecyclerView: RecyclerView){
            val retrofit = Retrofit.Builder()
                .baseUrl(Utils.REAL_TIME_NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val requestInterface = retrofit.create(ArticlesRequestInterface::class.java)

            val call = requestInterface.getNews(Utils.NEWS_API_KEY)

            call.enqueue(object :retrofit2.Callback<RealTimeNewsBaseModel>{
                override fun onFailure(call: Call<RealTimeNewsBaseModel>, t: Throwable) {
                    Log.e("onResponseFailCuresnt","failr")
                    t.printStackTrace()
                    doRequestForMostPopularArticles(context, articlesRecyclerView)
                }

                override fun onResponse(call: Call<RealTimeNewsBaseModel>, response: Response<RealTimeNewsBaseModel>) {

                    for(i in response.body()!!.results){
                        Log.e("response","tittle:${i.title}|desc${i.abstract}|img:${i.multimedia.get(0).url}")
                    }

                    val adapter = ArticlesRecyclerViewAdapter(response!!.body()!!.results,context)
                    val layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL ,false)

                    articlesRecyclerView.layoutManager = layoutManager
                    articlesRecyclerView.adapter = adapter
                }

            })
        }

       fun doRequestForMostPopularArticles(context: Context,articlesRecyclerView: RecyclerView){
           Log.e("doRequestMost","called")
           val retrofit = Retrofit.Builder()
               .baseUrl(Utils.NEWS_API_BASE_URL)
               .addConverterFactory(GsonConverterFactory.create())
               .build()

           val requestInterface = retrofit.create(ArticlesRequestInterface::class.java)

           val call = requestInterface.getArchiveNews(Utils.NEWS_API_KEY)

           call.enqueue(object :retrofit2.Callback<NewsJsonBaseModel>{
               override fun onFailure(call: Call<NewsJsonBaseModel>, t: Throwable) {
                   Log.e("onResponseFailPopilar","failr")
                   t.printStackTrace()
               }

               override fun onResponse(call: Call<NewsJsonBaseModel>, response: Response<NewsJsonBaseModel>) {

                    for(i in response.body()!!.results){
                        Log.e("response","tittle:${i.title}|desc${i.abstract}|img:${i.media.get(0).`media-metadata`.get(0).url}")
                    }

                    val adapter = ArchiveArticlesRecyclerViewAdapter(response.body()!!.results,context)
                    val layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

                    articlesRecyclerView.layoutManager = layoutManager
                    articlesRecyclerView.adapter = adapter
               }

           })
        }

    }

}