package com.example.utsapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.utsapp.R
import com.example.utsapp.model.Article
import com.example.utsapp.ui.activity.ArticleDetailActivity
import com.google.android.material.imageview.ShapeableImageView

class ArticleAdapter(
	private val items : List<Article>
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

	class ArticleViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
		val image : ShapeableImageView? = itemView.findViewById(R.id.ArticleImage)
		val title : TextView? = itemView.findViewById(R.id.ArticleTitle)
		val content : TextView? = itemView.findViewById(R.id.ArticleContent)
	}

	override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ArticleViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
		return ArticleViewHolder(view)
	}

	override fun onBindViewHolder(holder : ArticleViewHolder, position : Int) {
		val article = items[position]

		holder.image?.setImageResource(article.articleImage)
		holder.title?.text = article.title
		holder.content?.text = article.content

		// Saat item diklik → buka halaman detail → tampilkan data
		holder.itemView.setOnClickListener {
			val context = holder.itemView.context
			val intent = Intent(context, ArticleDetailActivity::class.java).apply {
				putExtra("articleImage", article.articleImage)
				putExtra("title", article.title)
				putExtra("content", article.content)
				putExtra("uploadTime", article.uploadTime)
				putExtra("readingTime", article.readingTime)
				putExtra("author", article.author)
				putExtra("authorImage", article.authorImage)
				putExtra("loveCount", article.loveCount)
				putExtra("commentCount", article.commentCount)
				putExtra("sendCount", article.sendCount)
				putExtra("bookmarkCount", article.bookmarkCount)
			}
			context.startActivity(intent)
		}
	}

	override fun getItemCount() : Int = items.size
}
