package com.example.realguesser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    //initializing
    private val  random = Random(System.currentTimeMillis())
    private var  secretNumber: Int = 0
    private var guesses: Int = 0
    private var attempts: Int = 0
    private var gameStage: Int = 1

    private lateinit var guessTextField: EditText
    private lateinit var resultLabel: TextView
    private lateinit var checkButton: Button
    private lateinit var playAgainButton: Button

    private lateinit var lives: TextView
    private lateinit var stages: TextView
    private lateinit var nextStage: Button
    private lateinit var popMessage: TextView
    private lateinit var fameLabel: TextView
    private lateinit var winnerNames: TextView

    private lateinit var winner: Button

    private lateinit var tools: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle("Guessing Game")
        // initialise UI Components

        //Setting the UI icons to dark mode so they can be visible.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        guessTextField = findViewById(R.id.inputNumber)
        resultLabel = findViewById(R.id.resultLabel)
        checkButton = findViewById(R.id.checkButton)
        playAgainButton = findViewById(R.id.playAgain)

        lives = findViewById(R.id.lifeLine)
        stages = findViewById(R.id.stageGage)
        nextStage = findViewById(R.id.contButton)
        nextStage.visibility = Button.GONE
        popMessage = findViewById(R.id.welcome)

        fameLabel = findViewById(R.id.fameLabel)
        winnerNames = findViewById(R.id.winNames)

        winner = findViewById(R.id.win)
        winner.visibility = Button.GONE

        tools = findViewById(R.id.settings)

        //Winner button
        winner.setOnClickListener {
            startActivity(
                Intent(this,hallOfFame::class.java)
            )
        }

        //Toolbox for controls
        tools.setOnClickListener {
            startActivity(
                Intent(this,Kontrol::class.java)
            )
        }

        generateSecretNumber(1)
        attempta()
        stageView()

        //read winnerNames
        readNames()

        checkButton.setOnClickListener {
            //checking the entered number
            check()
        }

        //Play again button resets the game
        playAgainButton.setOnClickListener {
            popMessage.text = "ENTER A NUMBER BETWEEN 1 AND 100"
            resetGame()
            stageView()
        }

        //Next stage loads new components
        nextStage.setOnClickListener {
            resultLabel.text = ""
            nextStage.visibility = Button.GONE
            stageView()
            generateSecretNumber(gameStage)
            attempta()

            //Guess field && button Re-appear
            guessTextField.visibility = TextView.VISIBLE
            checkButton.visibility = Button.VISIBLE

        }
    }

    //Function to check the guessed number
    private fun check() {
        //convert entered number
        val playnumber = guessTextField.text.toString().toIntOrNull()

        if (playnumber != null) {
            if ( playnumber == secretNumber) {
                if (gameStage == 5 && attempts > 0) {
                    //Clears
                    guessTextField.text.clear() //Clear field
                    guessTextField.visibility = TextView.GONE
                    checkButton.visibility = Button.GONE
                    resultLabel.text = "Congragulations! You have won the game! \nPlease enter your name for the hall of fame."
                    winner.visibility = Button.VISIBLE
                } else {
                    //NORMAL WIN
                    gameStage += 1
                    guesses += 1
                    resultLabel.text = "Congragulations! $secretNumber is the lucky number \nYou've guessed it in $guesses attempts." + texter(gameStage)
                    nextStage.visibility = Button.VISIBLE

                    //Clears
                    guessTextField.text.clear() //Clear field
                    guessTextField.visibility = TextView.GONE
                    checkButton.visibility = Button.GONE
                }

            } else {
                if (playnumber < secretNumber) {
                    //Too low
                    resultLabel.text = "$playnumber is too low. Try again"
                    attempts -= 1
                    guesses += 1
                    guessTextField.text.clear() //Clear field
                } else {
                    //Too high
                    resultLabel.text = "$playnumber is too high. Try again"
                    attempts -= 1
                    guesses += 1
                    guessTextField.text.clear() //Clear field
                }
            }
        } else {
            resultLabel.text = "Error! Please enter a valid number." //Error Checker
        }

        //Check attempts
        when (attempts) {
            0 -> {
                //Game Over
                resultLabel.text = "Game Over! \n$secretNumber was the lucky number. Better luck next time."
                //Controls
                //Clears
                playAgainButton.visibility = Button.VISIBLE
                guessTextField.text.clear() //Clear field
                guessTextField.visibility = TextView.GONE
                checkButton.visibility = Button.GONE
            }
        }
        attempta() //shows how many attempts you have left
    }

    //Method to show which number range you need to guess
    private fun texter(stage: Int): String {
        return when (stage) {
            1 -> "\nIn stage $stage you have to guess a number between 1 and 100"
            2 -> "\nIn stage $stage you have to guess a number between 1 and 200"
            3 -> "\nIn stage $stage you have to guess a number between 1 and 300"
            4 -> "\nIn stage $stage you have to guess a number between 1 and 400"
            5 -> "\nIn stage $stage you have to guess a number between 1 and 500"
            else -> "\nStage $stage is not valid. Please choose a valid stage."
        }
    }

    //Method to generate a secret number
    private fun generateSecretNumber(gameStage: Int) {
        when (gameStage) {
            1-> {
                secretNumber = random.nextInt(1, 101)
                //secretNumber = 1
                guesses = 0
                attempts = 5
                popMessage.text = "ENTER A NUMBER BETWEEN 1 AND 100"
            }
            2-> {
                secretNumber = random.nextInt(1, 201)
                //secretNumber = 10
                guesses = 0
                attempts = 6
                popMessage.text = "ENTER A NUMBER BETWEEN 1 AND 200"
            }
            3 -> {
                secretNumber = random.nextInt(1, 301)
                //secretNumber = 15
                guesses = 0
                attempts = 8
                popMessage.text = "ENTER A NUMBER BETWEEN 1 AND 300"
            }
            4 -> {
                secretNumber = random.nextInt(1, 401)
                //secretNumber = 20
                guesses = 0
                attempts = 10
                popMessage.text = "ENTER A NUMBER BETWEEN 1 AND 400"
            }
            5-> {
                secretNumber = random.nextInt(1, 501)
                //secretNumber = 25
                guesses = 0
                attempts = 12
                popMessage.text = "ENTER A NUMBER BETWEEN 1 AND 500"
            }
        }
    }

    //Method to reset the game
    private fun resetGame( ) {
        generateSecretNumber(1)
        guessTextField.text.clear()
        resultLabel.text = ""
        guesses = 0
        gameStage = 1

        playAgainButton.visibility = Button.GONE
        checkButton.visibility = Button.VISIBLE
        guessTextField.visibility = TextView.VISIBLE

        attempta() //shows how many attempts you have left
    }

    //Method to show what stage the game is on
    private fun stageView() {
        stages.setText("STAGE: $gameStage")
    }

    //Read names on load :
    private fun readNames() {
        try {
            // Open the file for reading
            openFileInput("player_names.txt").bufferedReader().use { reader ->
                // Read the entire file and set it to the TextView
                val names = reader.readText()

                if(names == "") {
                    fameLabel.visibility = TextView.GONE
                } else {
                    fameLabel.visibility = TextView.VISIBLE
                    winnerNames.text = names
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            winnerNames.text = "No names found."
        }
    }

    //tracks the attempts of the player
    private fun attempta() {
        lives.setText("You have $attempts attempts left.")
    }

}
