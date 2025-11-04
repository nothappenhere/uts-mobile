package com.example.utsapp.ui.activity

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utsapp.R
import com.google.android.material.imageview.ShapeableImageView

class ArticleDetailActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.article_detail)

		// Inisialisasi komponen
		val image = findViewById<ShapeableImageView>(R.id.detailImage)
		val title = findViewById<TextView>(R.id.detailTitle)
		val content = findViewById<TextView>(R.id.detailContent)
		val authorName = findViewById<TextView>(R.id.detailAuthorName)
		val authorImage = findViewById<ShapeableImageView>(R.id.detailAuthorImage)
		val uploadTime = findViewById<TextView>(R.id.detailUploadTime)
		val readingTime = findViewById<TextView>(R.id.detailReadingTime)
		val love = findViewById<TextView>(R.id.detailLoveCount)
		val comment = findViewById<TextView>(R.id.detailCommentCount)
		val send = findViewById<TextView>(R.id.detailSendCount)
		val bookmark = findViewById<TextView>(R.id.detailBookmarkCount)

		val backIcon = findViewById<ImageView>(R.id.IconChevron)
		val downloadIcon = findViewById<ImageView>(R.id.IconDownload)
		val loveIcon = findViewById<ImageView>(R.id.IconLove)
		val commentIcon = findViewById<ImageView>(R.id.IconComment)
		val sendIcon = findViewById<ImageView>(R.id.IconSend)
		val bookmarkIcon = findViewById<ImageView>(R.id.IconBookmark)

		// Ambil data dari Intent
		val imageRes = intent.getIntExtra("articleImage", 0)
		val titleText = intent.getStringExtra("title")
		val contentText = intent.getStringExtra("content")
		val author = intent.getStringExtra("author")
		val authorImageRes = intent.getIntExtra("authorImage", 0)
		val time = intent.getStringExtra("uploadTime")
		val reading = intent.getStringExtra("readingTime")
		val loveCountText = intent.getStringExtra("loveCount")
		val commentCountText = intent.getStringExtra("commentCount")
		val sendCountText = intent.getStringExtra("sendCount")
		val bookmarkCountText = intent.getStringExtra("bookmarkCount")

		// Set data ke tampilan
		image.setImageResource(imageRes)
		title.text = titleText
		content.text = contentText
		authorName.text = author
		authorImage.setImageResource(authorImageRes)
		uploadTime.text = time
		readingTime.text = reading
		love.text = loveCountText
		comment.text = commentCountText
		send.text = sendCountText
		bookmark.text = bookmarkCountText

		// Justify text agar rata kiri-kanan (untuk Android O ke atas)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			content.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
		}

		// Tombol Back
		backIcon.setOnClickListener {
			finish()
		}

		// Tombol Dowload
		downloadIcon.setOnClickListener {
			Toast.makeText(this, "The download feature will be available soon!", Toast.LENGTH_SHORT)
				.show()
		} // Tombol Love
		loveIcon.setOnClickListener {
			Toast.makeText(this, "The like feature will be available soon!", Toast.LENGTH_SHORT).show()
		} // Tombol Comment
		commentIcon.setOnClickListener {
			Toast.makeText(this, "The comment feature will be available soon!", Toast.LENGTH_SHORT).show()
		} // Tombol Send
		sendIcon.setOnClickListener {
			Toast.makeText(this, "The send feature will be available soon!", Toast.LENGTH_SHORT).show()
		} // Tombol Bookmark
		bookmarkIcon.setOnClickListener {
			Toast.makeText(this, "The bookmark feature will be available soon!", Toast.LENGTH_SHORT)
				.show()
		}
	}
}