package com.example.utsapp.ui.fragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.utsapp.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import java.util.Calendar

class BiodataFragment : Fragment() {

	// Layout mode
	private lateinit var layoutViewMode : LinearLayout
	private lateinit var layoutEditMode : LinearLayout
	private lateinit var cardEditMode : MaterialCardView

	// Button / action
	private lateinit var btnEdit : TextView
	private lateinit var btnSave : Button

	// Profile Picture
	private lateinit var biodataImage : ShapeableImageView
	private lateinit var btnEditImage : ImageView

	// Data model sederhana (in-memory)
	private var biodata = mutableMapOf(
		"Full Name" to "Muhammad Rizky Akbar",
		"Gender" to "Laki-laki",
		"Birthday Date" to "14/12/2004",
		"E-mail Address" to "muhammad.rizky15@mhs.itenas.ac.id",
		"Phone Number" to "+62 896 3957 9254",
		"Home Address" to "Jl. Melati No. 45, Bandung",
		"University Name" to "Institut Teknologi Nasional Bandung (ITENAS)",
		"Major Name" to "Informatika",
		"NRP" to "15-2022-166",
		"Course Name" to "IFB-355 Pemrograman Mobile",
	)

	// Activity result launcher untuk memilih gambar
	private val pickImageLauncher = registerForActivityResult(
		ActivityResultContracts.GetContent()
	) { uri : Uri? ->
		uri?.let {
			biodataImage.setImageURI(it)
		}
	}

	override fun onCreateView(
		inflater : LayoutInflater, container : ViewGroup?, savedInstanceState : Bundle?
	) : View {
		val view = inflater.inflate(R.layout.biodata_layout, container, false)

		// Binding view
		layoutViewMode = view.findViewById(R.id.layoutViewMode)
		layoutEditMode = view.findViewById(R.id.layoutEditMode)
		cardEditMode = view.findViewById(R.id.CardEditMode)
		btnEdit = view.findViewById(R.id.editAction)
		btnSave = view.findViewById(R.id.saveAction)
		biodataImage = view.findViewById(R.id.BiodataImage)
		btnEditImage = view.findViewById(R.id.BiodataEditImage)

		// Tampilkan data awal di view mode
		updateViewMode(view)
		setupEditMode(view)

		// Listeners
		btnEdit.setOnClickListener { showEditMode(view) }
		btnSave.setOnClickListener { saveData(view) }

		// Aksi ketika tombol edit foto ditekan
		btnEditImage.setOnClickListener {
			pickImageLauncher.launch("image/*")
		}

		return view
	}

	// Mapping view mode
	private val viewFields : Map<String, Int> = mapOf(
		"Full Name" to R.id.BiodataFullName,
		"Gender" to R.id.BiodataGender,
		"Birthday Date" to R.id.BiodataBirthdayDate,
		"E-mail Address" to R.id.BiodataEmailAddress,
		"Phone Number" to R.id.BiodataPhoneNumber,
		"Home Address" to R.id.BiodataHomeAddress,
		"University Name" to R.id.BiodataunivName,
		"Major Name" to R.id.BiodataMajorName,
		"NRP" to R.id.BiodataNRP,
		"Course Name" to R.id.BiodataCourseName,
	)

	private fun updateViewMode(root : View) {
		viewFields.forEach { (key, viewId) ->
			root.findViewById<TextView>(viewId)?.text = biodata[key] ?: ""
		}
	}

	// Mapping edit mode
	private val editFieldIds : Map<String, Int> = mapOf(
		"Full Name" to R.id.FieldFullName,
		"Birthday Date" to R.id.FieldBirthdayDate,
		"E-mail Address" to R.id.FieldEmailAddress,
		"Phone Number" to R.id.FieldPhoneNumber,
		"Home Address" to R.id.FieldHomeAddress,
	)

	@SuppressLint("DefaultLocale")
	private fun setupEditMode(root : View) {
		val genderSpinner = root.findViewById<Spinner>(R.id.dropdownGender)
		val genderOptions = arrayOf("Laki-laki", "Perempuan")
		val genderAdapter = ArrayAdapter(
			requireContext(), R.layout.spinner_item, genderOptions
		)
		genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		genderSpinner.adapter = genderAdapter

		val currentGender = biodata["Gender"]
		val selectedIndex = genderOptions.indexOf(currentGender)
		if (selectedIndex >= 0) {
			genderSpinner.setSelection(selectedIndex)
		}

		val fieldBirthdayDate = root.findViewById<EditText>(R.id.FieldBirthdayDate)
		fieldBirthdayDate.setOnClickListener {
			val c = Calendar.getInstance()
			val dpd = DatePickerDialog(requireContext(), { _, y, m, d ->
				fieldBirthdayDate.setText(String.format("%02d/%02d/%04d", d, m + 1, y))
			}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
			dpd.show()
		}
	}

	private fun showEditMode(root : View) {
		layoutViewMode.visibility = View.GONE
		layoutEditMode.visibility = View.VISIBLE

		val params = cardEditMode.layoutParams as ViewGroup.MarginLayoutParams
		val scale = resources.displayMetrics.density
		params.bottomMargin = (12 * scale + 0.5f).toInt()
		cardEditMode.layoutParams = params

		editFieldIds.forEach { (key, editId) ->
			val field = root.findViewById<EditText?>(editId)
			field?.setText(biodata[key] ?: "")
		}

		// Tambahkan untuk Spinner Gender
		val genderSpinner = root.findViewById<Spinner>(R.id.dropdownGender)
		val genderOptions = arrayOf("Laki-laki", "Perempuan")
		val currentGender = biodata["Gender"]
		val selectedIndex = genderOptions.indexOf(currentGender)
		if (selectedIndex >= 0) {
			genderSpinner.setSelection(selectedIndex)
		}
	}

	private fun saveData(root : View) {
		val fullName = root.findViewById<EditText>(R.id.FieldFullName).text.toString().trim()
		val genderSpinner = root.findViewById<Spinner>(R.id.dropdownGender)
		val gender = genderSpinner.selectedItem.toString()
		val birthdayDate = root.findViewById<EditText>(R.id.FieldBirthdayDate).text.toString().trim()
		val emailAddress = root.findViewById<EditText>(R.id.FieldEmailAddress).text.toString().trim()
		val phoneNumber = root.findViewById<EditText>(R.id.FieldPhoneNumber).text.toString().trim()
		val homeAddress = root.findViewById<EditText>(R.id.FieldHomeAddress).text.toString().trim()

		if (fullName.isEmpty() || gender.isEmpty() || birthdayDate.isEmpty() || emailAddress.isEmpty() || phoneNumber.isEmpty() || homeAddress.isEmpty()) {
			Toast.makeText(requireContext(), "Please fill in all available fields!", Toast.LENGTH_SHORT)
				.show()
			return
		}

		biodata["Full Name"] = fullName
		biodata["Gender"] = gender
		biodata["Birthday Date"] = birthdayDate
		biodata["E-mail Address"] = emailAddress
		biodata["Phone Number"] = phoneNumber
		biodata["Home Address"] = homeAddress
		Toast.makeText(requireContext(), "Data saved successfully!", Toast.LENGTH_SHORT).show()

		updateViewMode(root)

		val params = cardEditMode.layoutParams as ViewGroup.MarginLayoutParams
		params.bottomMargin = 0
		cardEditMode.layoutParams = params

		layoutEditMode.visibility = View.GONE
		layoutViewMode.visibility = View.VISIBLE
	}
}