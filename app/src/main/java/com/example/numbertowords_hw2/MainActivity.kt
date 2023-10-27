package com.example.numbertowords_hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numberInput: EditText = findViewById(R.id.numberInput)
        val translateButton: Button = findViewById(R.id.translateButton)
        val translationResult: TextView = findViewById(R.id.translationResult)

        translateButton.setOnClickListener {
            val inputText = numberInput.text.toString()
            if (inputText.isNotEmpty()) {
                val inputNumber = inputText.toIntOrNull()
                if (inputNumber != null && inputNumber in 1..1000) {
                    // Calls numToWords() function with inputNumber
                    val translation = numToWords(inputNumber)
                    translationResult.text = translation
                } else {
                    translationResult.text = "Please enter a valid number between 1 and 1000."
                }
            } else {
                translationResult.text = "Please enter a number."
            }
        }
    }

    private fun numToWords(inputNum: Int): String {
        val hundred: String = hundreds(inputNum / 100, Constants.BASE_TWENTY.last())
        val subHundred: String = subHundreds(inputNum % 100)

        return hundred.dropLast(1) +
                if (inputNum % 100 == 0)
                    Constants.ENDING
                else getSymbol(
                    inputNum,
                    100,
                    Constants.SPACE
                ) + subHundred
    }

    fun subHundreds(inputNum: Int): String {
        val baseTwenty = Constants.BASE_TWENTY
        val oneToNineteen = Constants.ONE_TO_NINETEEN
        val ending = Constants.ENDING
        val unifier = Constants.UNIFIER

        return baseTwenty[inputNum / 20] +
                if (inputNum % 20 == 0) ending
                else getSymbol(inputNum, 20, unifier) + oneToNineteen[inputNum % 20]
    }

    fun hundreds(index: Int, oneHundred: String): String {
        val oneToNineteen = Constants.ONE_TO_NINETEEN
        val space = Constants.SPACE

        return when (index) {
            0 -> space
            1 -> oneHundred
            else -> {
                var num: String = oneToNineteen[index]
                num = if (num.last() == 'ა') num else num.dropLast(1)
                num + oneHundred
            }
        }
    }

    fun getSymbol(inputNum: Int, limit: Int, symbol: String) = if (inputNum > limit) symbol else ""
}
