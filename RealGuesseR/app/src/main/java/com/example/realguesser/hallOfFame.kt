package com.example.realguesser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class hallOfFame : AppCompatActivity() {
    private lateinit var winMSG: TextView

    private lateinit var nameField: EditText
    private lateinit var saveButton: Button
    private lateinit var winNumber: String
    private lateinit var numGuesses: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hall_of_fame)

        winNumber = intent.getStringExtra("SecretNumber") ?: "Unknown"
        numGuesses = intent.getStringExtra("guesses") ?: "Unknown"

        winMSG = findViewById(R.id.winMessge)

        nameField = findViewById(R.id.name)
        saveButton = findViewById(R.id.saveName)


        if (winNumber == "Unknown" && numGuesses == "Unknown") {
            winMSG.text = ""
        } else {
            winMSG.text = "Congratulations! $winNumber is the lucky number \nYou guessed it in $numGuesses attempts"
        }

        saveButton.setOnClickListener {
            // Define date and time format
            val dateFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
            val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

            // Get current date and time
            val currentDate = dateFormat.format(Date())
            val currentTime = timeFormat.format(Date())

            val playerName = nameField.text.toString()

            val entry = "$playerName - $currentDate at $currentTime\n"

            if (playerName == "") {
                Toast.makeText(applicationContext, "No name entered", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    // Open or create a file named "player_names.txt" in internal storage
                    openFileOutput("player_names.txt", MODE_APPEND).use { outputStream ->
                        outputStream.write((entry).toByteArray())
                    }
                    Toast.makeText(applicationContext, "Name saved", Toast.LENGTH_SHORT).show()


                    startActivity(
                        Intent(this,MainActivity::class.java)

                    )

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Failed to save name", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}