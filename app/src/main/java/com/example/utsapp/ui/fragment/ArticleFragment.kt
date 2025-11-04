package com.example.utsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utsapp.R
import com.example.utsapp.adapter.ArticleAdapter
import com.example.utsapp.model.Article
import org.json.JSONArray

class ArticleFragment : Fragment() {

	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?
	) : View? {
		val view = inflater.inflate(R.layout.article_layout, container, false)

		// Setup RecyclerView
		val recyclerView = view.findViewById<RecyclerView>(R.id.articleRecycler)
		recyclerView.layoutManager = LinearLayoutManager(requireContext())

		val listArticle = loadFromAssets()
		val adapter = ArticleAdapter(listArticle)
		recyclerView.adapter = adapter

		return view
	}

	private fun loadFromAssets() : List<Article> {
		val jsonString =
			requireContext().assets.open("articles.json").bufferedReader().use { it.readText() }

		// Gunakan org.json untuk parsing sederhana
		val jsonArray = JSONArray(jsonString)
		val articleList = mutableListOf<Article>()

		for (i in 0 until jsonArray.length()) {
			val item = jsonArray.getJSONObject(i)

			val imageName = item.getString("articleImage")
			val imageResId = resources.getIdentifier(imageName, "drawable", requireContext().packageName)

			val authorImage = item.getString("authorImage")
			val authorImageResId =
				resources.getIdentifier(authorImage, "drawable", requireContext().packageName)

			articleList.add(
				Article(
					imageResId,
					item.getString("title"),
					item.getString("content"),
					item.getString("uploadTime"),
					item.getString("readingTime"),
					item.getString("author"),
					authorImageResId,
					item.getString("loveCount"),
					item.getString("commentCount"),
					item.getString("sendCount"),
					item.getString("bookmarkCount"),

					)
			)
		}

		return articleList
	}
}