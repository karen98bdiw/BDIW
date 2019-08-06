package com.example.bdiw

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.bdiw.Adapters.MainPagesAdapter
import com.example.bdiw.MainFragments.Fragment1
import com.example.bdiw.MainFragments.Fragment2
import com.example.bdiw.MainFragments.Fragment3
import com.example.bdiw.NewsFromApi.ArchiveArticlesRecyclerViewAdapter
import com.example.bdiw.NewsFromApi.ArticlesRecyclerViewAdapter
import com.example.bdiw.NewsFromApi.ArticlesRequestInterface
import com.example.bdiw.NewsJsonParserModel.NewsJsonBaseModel
import com.example.bdiw.RealTimeNewsParser.RealTimeNewsBaseModel
import com.google.android.material.navigation.NavigationView
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.navigationmenu_header.view.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Utils {

    companion object {

        val MESAGE_VIEW_LEFT = 1
        val MESAGE_VIEW_RIGHT = 2
        val NEWS_API_BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/emailed/"
        val REAL_TIME_NEWS_BASE_URL = "https://api.nytimes.com/svc/news/v3/content/all/"
        val NEWS_API_KEY = "eqPZhtMDSGS1MHqeeWALczfD1G8qCaGT"


        var REQUEST_CALLED = false

        var HAVE_RESPONSE = false

        var pd:ProgressDialog?=null

        fun constructMainViewPager(viewPager: ViewPager, fm: FragmentManager){
            val mainPagesAdapter = MainPagesAdapter(fm)
            mainPagesAdapter.addFragment(Fragment1(),"first")
            mainPagesAdapter.addFragment(Fragment2(),"secont")
            mainPagesAdapter.addFragment(Fragment3(),"third")
            viewPager.adapter = mainPagesAdapter
        }

        fun constructBottomNavigationView(bottomNavigationView:BottomNavigationViewEx){
            bottomNavigationView.setTextVisibility(false)
            bottomNavigationView.enableShiftingMode(false)
            bottomNavigationView.enableItemShiftingMode(false)
            bottomNavigationView.enableAnimation(false)
            for(i in 0 until  bottomNavigationView.menu.size()){
                bottomNavigationView.setIconTintList(i,null)
            }
        }

        fun constructToolBar(toolbar: androidx.appcompat.widget.Toolbar,drawerLayout:DrawerLayout){


            toolbar.setNavigationIcon(R.drawable.ic_menu)

            toolbar.setNavigationOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        fun costumizeNavigationView(nV:NavigationView,name:String,surname:String){

            val navHeader = nV.getHeaderView(0)

            navHeader.navigationViewUserName.setText("${name+ " " +surname}")


        }

        fun setUpProgresDialog(pd:ProgressDialog){

            pd.setTitle("ProgressDialog")
            pd.setMessage("loading ...")
            pd.max = 100
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            this.pd = pd



        }

        fun doRequestForCurentArticles(context:Context,articlesRecyclerView: RecyclerView){
            pd!!.show()
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
                    pd!!.dismiss()
                    HAVE_RESPONSE = true

                    for(i in response.body()!!.results){
                        Log.e("response","tittle:${i.title}|desc${i.abstract}|img:${i.multimedia.get(0).url}")
                    }

                    val adapter =
                        ArticlesRecyclerViewAdapter(response!!.body()!!.results, context)
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
                   pd!!.dismiss()
                   Log.e("onResponseFailPopilar","failr")
                   t.printStackTrace()
               }

               override fun onResponse(call: Call<NewsJsonBaseModel>, response: Response<NewsJsonBaseModel>) {
                    HAVE_RESPONSE = true
                   pd!!.dismiss()
                    for(i in response.body()!!.results){
                        Log.e("response","tittle:${i.title}|desc${i.abstract}|img:${i.media.get(0).`media-metadata`.get(0).url}")
                    }

                    val adapter = ArchiveArticlesRecyclerViewAdapter(
                        response.body()!!.results,
                        context
                    )
                    val layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

                    articlesRecyclerView.layoutManager = layoutManager
                    articlesRecyclerView.adapter = adapter
               }

           })
        }

    }

}