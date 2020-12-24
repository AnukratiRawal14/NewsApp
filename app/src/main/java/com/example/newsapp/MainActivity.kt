package com.example.newsapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.littlemango.stacklayoutmanager.StackLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var adapter:Adapter
    private var articles = mutableListOf<Article>()
    var pageNo = 1
    var totalResult = 1
    val TAG ="MainActivity"
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())
        mInterstitialAd.adListener = object: AdListener(){
            override fun onAdClosed() {
               super.onAdClosed()
                mInterstitialAd.loadAd(AdRequest.Builder().build())
            }
        }

        adapter = Adapter(this@MainActivity,articles)
        listNews.adapter =  adapter
      //  listNews.layoutManager = LinearLayoutManager(this@MainActivity)

        val layoutManager = StackLayoutManager(StackLayoutManager.ScrollOrientation.LEFT_TO_RIGHT)
        layoutManager.setPagerMode(true)
        layoutManager.setPagerFlingVelocity(3000)
        layoutManager.setItemChangedListener(object:StackLayoutManager.ItemChangedListener{
            override fun onItemChanged(position: Int) {
                container.setBackgroundColor(Color.parseColor("#605052"))
                Log.d(TAG, "First Visible Item - ${layoutManager.getFirstVisibleItemPosition()}")
                Log.d(TAG, "Total Count -${layoutManager.itemCount}")

                if(totalResult > layoutManager.itemCount && layoutManager.getFirstVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    //next page data
                    pageNo++
                    getNews()
                }
                if(position % 5 ==0){
                     if (mInterstitialAd.isLoaded) {
                           mInterstitialAd.show()
                     }
                }
            }
        })
        listNews.layoutManager = layoutManager
        getNews()
    }

    private fun getNews(){
        Log.d(TAG,"Request sent for $pageNo")
        val news = NewsService.newsInstances.getHeadlines("in",pageNo)
        news.enqueue(object :retrofit2.Callback<News>{
            override fun onFailure(call: Call<News>, t: Throwable) {
              Log.d("Anukratidone","Error",t)
            }

            override fun onResponse(call: Call<News>, response: Response<News>) {
               val news = response.body()
                if(news!= null){
                    totalResult = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}