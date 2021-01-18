package com.example.catatanhariansaya

import PRAdapter
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = FirebaseDatabase.getInstance()

        var  myRef : DatabaseReference? = database.getReference("homework")

        // Read Data
        myRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // looping ketika mengambil data
                // merah ? coba tambahkan ()
                val dataArray = ArrayList<PR>()
                for (i in dataSnapshot.children){
                    val data = i.getValue(PR::class.java)
                    data?.key = i.key
                    data?.let { dataArray.add(it) }
                }
                findViewById<RecyclerView>(R.id.rvListNotes).adapter = PRAdapter(dataArray, object : PRAdapter.OnClick {
                    override fun edit(PR: PR?) {
                        val intent = Intent(this@MainActivity, FormPRActivity::class.java)
                        intent.putExtra("note", PR)
                        startActivity(intent)
                    }

                    override fun delete(key: String?) {
                        AlertDialog.Builder(this@MainActivity).apply {
                            setTitle("Hapus ?")
                            setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                                myRef?.child(key.toString())?.removeValue()
//                                Toast.makeText(this@MainActivity, key, Toast.LENGTH_SHORT).show()
                            }
                            setNegativeButton("Tidak", { dialogInterface: DialogInterface, i: Int -> })
                        }.show()
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("tag", "Failed to read value.", error.toException())
            }
        })

        findViewById<Button>(R.id.btAddNote).setOnClickListener {
            startActivity(Intent(this@MainActivity, FormPRActivity::class.java))
        }
    }
}