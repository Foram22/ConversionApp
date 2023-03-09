package com.foram.conversionapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.foram.conversionapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var sourceUnits =
        arrayOf("Select Source Unit", "Seconds", "Minutes", "Hours", "Days", "Weeks", "Months")
    private var destinationUnits = arrayOf(
        "Select Destination Unit",
        "Seconds",
        "Minutes",
        "Hours",
        "Days",
        "Weeks",
        "Months",
        "Years"
    )

    private lateinit var destinationAdapter: ArrayAdapter<String>

    var calculatedAns = 0.0
    var queValue = 0.0
    var sourceUnit = ""
    var destinationUnit = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set Source Unit Adapter to the Spinner
        val sourceAdapter = ArrayAdapter(this, R.layout.units_dropdown_menu, sourceUnits)
        binding.spinnerSourceUnit.adapter = sourceAdapter

        // Set Destination Unit Adapter to the Spinner
        destinationAdapter = ArrayAdapter(
            this@MainActivity,
            R.layout.units_dropdown_menu,
            destinationUnits
        )
        binding.spinnerDestinationUnit.adapter = destinationAdapter

        binding.spinnerSourceUnit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (p2) {
                        0 -> disableDestinationUnitSpinner()        // Destination Units Spinner will be disabled if nothing selected
                        1 -> {
                            val answerArray = mutableListOf(
                                0.0,
                                1.0,
                                0.01667,
                                0.000278,
                                0.00001157,
                                0.00000165,
                                0.00000038,
                                0.00000003
                            )
                            setDestinationAdapter(p2, answerArray)
                        }
                        2 -> {
                            val answerArray = mutableListOf(
                                0.0, 60.0, 1.0, 0.01667, 0.0006944, 0.0009921, 0.00002283, 0.0000019
                            )
                            setDestinationAdapter(p2, answerArray)
                        }
                        3 -> {
                            val answerArray = mutableListOf(
                                0.0, 3600.0, 60.0, 1.0, 0.041667, 0.005952, 0.001369, 0.0001141
                            )
                            setDestinationAdapter(p2, answerArray)
                        }
                        4 -> {
                            val answerArray = mutableListOf(
                                0.0, 86400.0, 1440.0, 24.0, 1.0, 0.142857, 0.328763, 0.002739
                            )
                            setDestinationAdapter(p2, answerArray)
                        }
                        5 -> {
                            val answerArray = mutableListOf(
                                0.0, 604800.0, 10080.0, 168.0, 7.0, 1.0, 0.23014, 0.019178
                            )
                            setDestinationAdapter(p2, answerArray)
                        }
                        6 -> {
                            val answerArray = mutableListOf(
                                0.0, 2629746.0, 43800.0, 730.0, 30.417, 4.345, 1.0, 0.0833
                            )
                            setDestinationAdapter(p2, answerArray)
                        }
                    }
                    sourceUnit = sourceUnits[p2]
                }
            }

        binding.btnCalculate.setOnClickListener {
            if (binding.etValue.text.isEmpty()) {   // If value isn't entered, shows an error message at edit text field
                binding.etValue.error = "Please enter any value."
            } else {
                // Exception Handling (If edit text value is invalid)
                try {
                    queValue = binding.etValue.text.toString().toDouble()
                    calculatedAns *= queValue   // Calculate the answer multiplying by entered value
                    binding.tvAnswer.visibility = View.VISIBLE      // Answer field will be visible after calculating it
                    binding.tvAnswer.text = "Answer:- $calculatedAns"
                } catch (_: java.lang.NumberFormatException) {
                    printToastMessage("Please enter valid value to convert from $sourceUnit to $destinationUnit")
                }
            }
        }

    }

    private fun setDestinationAdapter(index: Int, answerArray: MutableList<Double>) {
        val copiedUnits = destinationUnits.copyOf().toMutableList()
        copiedUnits.removeAt(index)
        answerArray.removeAt(index)

        // Set Destination Unit Adapter to the Spinner
        destinationAdapter = ArrayAdapter(this, R.layout.units_dropdown_menu, copiedUnits)
        binding.spinnerDestinationUnit.adapter = destinationAdapter

        enableDestinationUnitSpinner()   // Now Source Unit is selected from the spinner so Destination Spinner will be enabled

        binding.spinnerDestinationUnit.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when (p2) {
                        0 -> {
                            binding.etValue.visibility = View.GONE
                            binding.btnCalculate.visibility = View.GONE
                        }
                        else -> {
                            // When Destination Unit is selected, textfield for value and calculate button is visible
                            binding.etValue.visibility = View.VISIBLE
                            binding.btnCalculate.visibility = View.VISIBLE
                            calculatedAns = answerArray[p2]
                            println("Answer: $calculatedAns")
                        }
                    }
                    destinationUnit = copiedUnits[p2]
                }
            }

    }

    private fun disableDestinationUnitSpinner() {
        binding.spinnerDestinationUnit.isEnabled = false
        binding.spinnerDestinationUnit.isClickable = false
    }

    private fun enableDestinationUnitSpinner() {
        binding.spinnerDestinationUnit.isEnabled = true
        binding.spinnerDestinationUnit.isClickable = true
    }

    private fun printToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}