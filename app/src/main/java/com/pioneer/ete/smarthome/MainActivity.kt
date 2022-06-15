package com.pioneer.ete.smarthome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.pioneer.ete.smarthome.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        binding.swLamp.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                database.updateChildren(mapOf("led" to 1))
            } else {
                database.updateChildren(mapOf("led" to 0))
            }
        }

        binding.swSunshades.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                database.updateChildren(mapOf("sunshades" to 1))
            } else {
                database.updateChildren(mapOf("sunshades" to 0))
            }
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                binding.tvTemp.text = (dataSnapshot.value as Map<*, *>)["temp"].toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
    }
}