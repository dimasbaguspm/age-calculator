package com.example.agecalculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    var resultText: TextView? = null
    var resetButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultText = findViewById(R.id.resultCalculate)
        resetButton = findViewById(R.id.resetButton)

        val myButton: Button = findViewById(R.id.pickDateButton)
        myButton.setOnClickListener {
            clickDatePicker()
        }
    }

    fun clickDatePicker( ) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val months = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener{
                    _view,
                    selectedYear,
                    month,
                    dayOfMonth -> onDatePickChange(selectedYear, month, dayOfMonth)},
            year, months, day)
            .show()
    }

     fun onDatePickChange(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
         val selectedDate = "$selectedDay/${selectedMonth+1}/$selectedYear"

         val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
         val formatedCurrentDate = dateFormat.parse(selectedDate)

         val selectedDateInMiliseconds = formatedCurrentDate.time / 60000

         val currentDate = dateFormat.parse(dateFormat.format(System.currentTimeMillis()))
         val currentDateInMiliseconds = currentDate.time / 60000

         val differenceInMinutes = currentDateInMiliseconds - selectedDateInMiliseconds

         if(differenceInMinutes >= 0) {
             resultText?.setText("You have lives in ${differenceInMinutes} minutes")
             resultText?.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15f)
         } else {
             resultText?.setText("You has cheating!")
             resultText?.setTextColor(getColor(R.color.red))
         }

        // Display
         resetButton?.isVisible = true
         resetButton?.setOnClickListener{onResetButtonClick()}
    }

    fun onResetButtonClick() {
        resetButton = findViewById(R.id.resetButton)

        resetButton?.isVisible = false

        resultText?.setText("")
        resultText?.setTextColor(R.color.white)
        resultText?.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
    }
}

