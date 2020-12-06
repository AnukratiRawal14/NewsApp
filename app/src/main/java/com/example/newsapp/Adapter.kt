package com.example.newsapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(val context: Context,val article: List<Article>): RecyclerView.Adapter<Adapter.ArticleViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
       val view = LayoutInflater.from(context).inflate((R.layout.item_layout),parent,false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
       return article.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
      val article = article[position]
      holder.title.text = article.title
      holder.description.text = article.description
      Glide.with(context).load(article.urlToImage).into(holder.image)
      holder.itemView.setOnClickListener{
            Toast.makeText(context,article.title,Toast.LENGTH_SHORT).show()
            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("URL",article.url)
           context.startActivity(intent)
        }
    }

    class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        //store reference of image , title, description
        var image = itemView.findViewById<ImageView>(R.id.image)
        var title = itemView.findViewById<TextView>(R.id.title)
        var description = itemView.findViewById<TextView>(R.id.description)
    }

}