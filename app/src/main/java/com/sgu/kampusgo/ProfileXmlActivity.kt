package com.sgu.kampusgo

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.ComponentActivity

class ProfileXmlActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val spMajor = findViewById<Spinner>(R.id.spMajor)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val ok = etName.text.toString().isNotBlank() &&
                        etEmail.text.toString().contains("@")
                btnSave.isEnabled = ok

                val email = R.id.etEmail.toString()
                etEmail.error = if (email.isNotEmpty() && !email.contains("@")) {
                    "Email needs @"
                } else {
                    null
                }
            }
        }
        btnSave.setOnClickListener {
            val major = spMajor.selectedItem.toString()
            Toast.makeText(this, "Saved ($major)", Toast.LENGTH_SHORT).show()
        }
        etName.addTextChangedListener(watcher)
        etEmail.addTextChangedListener(watcher)
        ArrayAdapter.createFromResource(
            this,
            R.array.majors,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spMajor.adapter = adapter
        }
    }
}