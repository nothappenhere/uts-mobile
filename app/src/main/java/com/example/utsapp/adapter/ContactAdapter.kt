package com.example.utsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.utsapp.R
import com.example.utsapp.model.Contact
import com.google.android.material.imageview.ShapeableImageView

class ContactAdapter(private val items : List<Contact>) :
	RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

	class ContactViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
		val image : ShapeableImageView = itemView.findViewById(R.id.ContactImage)
		val name : TextView = itemView.findViewById(R.id.ContactName)
		val phoneNumber : TextView = itemView.findViewById(R.id.ContactPhoneNumber)

		val callIcon : ImageView = itemView.findViewById(R.id.IconCall)
		val messageIcon : ImageView = itemView.findViewById(R.id.IconMessage)
	}

	override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ContactViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
		return ContactViewHolder(view)
	}

	override fun onBindViewHolder(holder : ContactViewHolder, position : Int) {
		val contact = items[position]

		holder.image.setImageResource(contact.contactImage)
		holder.name.text = contact.name
		holder.phoneNumber.text = contact.phoneNumber

		// Tombol Call
		holder.callIcon.setOnClickListener {
			Toast.makeText(
				holder.itemView.context, "The call feature will be available soon!", Toast.LENGTH_SHORT
			).show()
		}

		// Tombol Message
		holder.messageIcon.setOnClickListener {
			Toast.makeText(
				holder.itemView.context, "The message feature will be available soon!", Toast.LENGTH_SHORT
			).show()
		}
	}

	override fun getItemCount() : Int = items.size
}
