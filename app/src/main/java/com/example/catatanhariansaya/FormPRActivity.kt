package com.example.catatanhariansaya

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class FormPRActivity : AppCompatActivity() {
    var PR : PR? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_note)

        val data = intent.getSerializableExtra("pr")
        var edit = true

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("homework")

        if (data != null) {
            PR = data as PR
            findViewById<EditText>(R.id.etPRNameEdit).setText(PR?.title)
            findViewById<EditText>(R.id.etPRDescriptionEdit).setText(PR?.description)

            findViewById<Button>(R.id.btActForm).setText("Edit")
        } else {
            findViewById<Button>(R.id.btActForm).setText("Tambah")
            edit = false
        }

        findViewById<Button>(R.id.btActForm).setOnClickListener {
            if (edit) {
                val changeData = HashMap<String, Any>()
                changeData.put("title", findViewById<EditText>(R.id.etPRNameEdit).text.toString())
                changeData.put("description", findViewById<EditText>(R.id.etPRDescriptionEdit).text.toString())

                myRef.child(PR?.key.toString()).updateChildren(changeData)
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val key = myRef.push().key

                val newNote = PR()
                newNote.title = findViewById<EditText>(R.id.etPRNameEdit).text.toString()
                newNote.description = findViewById<EditText>(R.id.etPRDescriptionEdit).text.toString()

                myRef.child(key.toString()).setValue(newNote)
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
    }
}