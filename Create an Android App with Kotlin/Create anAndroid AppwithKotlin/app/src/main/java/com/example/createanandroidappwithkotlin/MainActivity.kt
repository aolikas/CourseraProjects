package com.example.createanandroidappwithkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var answer = 0
    var isGameOver = false
    var numOfAttempts = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startOver()
    }

    fun generateAnswer() {

        answer = Random.nextInt(1,25)

    }

    fun startOver() {
        isGameOver = false
        generateAnswer()
        val tvAnswer = findViewById<TextView>(R.id.tv_answer)
        tvAnswer.text = "??"

        val submitButton = findViewById<Button>(R.id.btn_submit)
        submitButton.isEnabled = true

        val etGuess = findViewById<EditText>(R.id.et_guess_number)
        etGuess.text.clear()
    }

    fun btnStartOverTapped(view: View) {
        startOver()
    }

    fun btnSubmitTapped(view: View) {

        val guess = getUsersGuess() ?: -999

        if(guess !in 1 .. 25) {
           Toast.makeText(this,"Guess must be 1 to 25", Toast.LENGTH_SHORT).show()
            return
        }
       var msg = ""
        numOfAttempts ++

        if (guess == answer){
            msg = "Correct! Guesses: $numOfAttempts"
            isGameOver = true
            val tvAnswer = findViewById<TextView>(R.id.tv_answer)
            tvAnswer.text = answer.toString()
            val submitButton = findViewById<Button>(R.id.btn_submit)
            submitButton.isEnabled = false
        } else {
            msg = if(guess < answer) "Too low" else "Too high"
        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()


    }

    fun getUsersGuess() : Int?{
        val etGuess = findViewById<EditText>(R.id.et_guess_number)
        val userGuess = etGuess.text.toString()

        var guessInt = 0

        try {
            guessInt = userGuess.toInt()
        } catch (e: Exception) {
            return null
        }

        return guessInt


    }


}