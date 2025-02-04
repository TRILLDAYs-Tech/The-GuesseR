package com.example.realguesser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Kontrol : AppCompatActivity() {

    private lateinit var controlButton: Button
    private lateinit var clearButton: Button
    private lateinit var openButton: Button
    private lateinit var passwordField: EditText

    private var pass = "A>B<SRb_"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kontrol)

        controlButton = findViewById(R.id.controls)
        openButton = findViewById(R.id.open)
        passwordField = findViewById(R.id.password)
        clearButton = findViewById(R.id.clearNames)

        openButton.visibility = Button.GONE
        passwordField.visibility = TextView.GONE
        clearButton.visibility = Button.GONE

        controlButton.setOnClickListener {
            controlButton.visibility = Button.GONE
            openButton.visibility = Button.VISIBLE
            passwordField.visibility = TextView.VISIBLE
        }

        openButton.setOnClickListener {
            val code = passwordField.text.toString()

            if (code != ""){
                if (code == pass) {
                    controlButton.visibility = Button.GONE
                    openButton.visibility = Button.GONE
                    passwordField.visibility = TextView.GONE
                    clearButton.visibility = Button.VISIBLE
                }  else {
                    Toast.makeText(applicationContext, "ERRoR! Incorrect Password", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "ERRoR! Please enter password", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            try {
                // Open the file in MODE_PRIVATE to overwrite its contents with an empty string
                openFileOutput("player_names.txt", MODE_PRIVATE).use { outputStream ->
                    outputStream.write("".toByteArray())  // Writing an empty string
                }
                Toast.makeText(applicationContext, "Names deleted", Toast.LENGTH_SHORT).show()

                // Restarting the activity to refresh the view
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Optional: finish the current activity
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "Failed to delete names", Toast.LENGTH_SHORT).show()
            }
        }

    }
}