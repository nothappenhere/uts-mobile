package com.example.utsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utsapp.R
import com.example.utsapp.adapter.ContactAdapter
import com.example.utsapp.model.Contact
import org.json.JSONArray

class ContactFragment : Fragment() {

	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?
	) : View? {
		val view = inflater.inflate(R.layout.contact_layout, container, false)

		val recyclerView = view.findViewById<RecyclerView>(R.id.contactRecycler)
		recyclerView.layoutManager = LinearLayoutManager(requireContext())

		val listContact = loadFromAssets()
		val adapter = ContactAdapter(listContact)
		recyclerView.adapter = adapter

		return view
	}

	private fun loadFromAssets() : List<Contact> {
		val jsonString =
			requireContext().assets.open("contacts.json").bufferedReader().use { it.readText() }

		// Gunakan org.json untuk parsing sederhana
		val jsonArray = JSONArray(jsonString)
		val contactList = mutableListOf<Contact>()

		for (i in 0 until jsonArray.length()) {
			val item = jsonArray.getJSONObject(i)

			val imageName = item.getString("contactImage")
			val imageResId = resources.getIdentifier(imageName, "drawable", requireContext().packageName)

			val name = item.getString("name")
			val phoneNumber = item.getString("phoneNumber")

			contactList.add(
				Contact(imageResId, name, phoneNumber)
			)
		}

		return contactList
	}
}