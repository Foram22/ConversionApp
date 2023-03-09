package com.foram.conversionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.foram.conversionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var sourceUnits =
        arrayOf("Select Source Unit", "seconds", "minutes", "hours", "days", "weeks", "months")
    private var destinationUnits = arrayOf(
        "Select Destination Unit",
        "seconds",
        "minutes",
        "hours",
        "days",
        "weeks",
        "months",
        "years"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sourceAdapter = ArrayAdapter(this, R.layout.units_dropdown_menu, sourceUnits)
        val destinationAdapter = ArrayAdapter(this, R.layout.units_dropdown_menu, destinationUnits)

        binding.spinnerSourceUnit.adapter = sourceAdapter
        binding.spinnerDestinationUnit.adapter = destinationAdapter


    }
}