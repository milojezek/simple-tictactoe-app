package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val buttons = Array(3) { mutableListOf<Button>() }
    private var player1Turn = true
    private var roundCount = 0

    private var player1Score = 0
    private var player2Score = 0

    private var player1Squares = mutableListOf<String>()
    private var player2Squares = mutableListOf<String>()

    private lateinit var textViewPLayer1: TextView
    private lateinit var textViewPLayer2: TextView

    private val winningCombinations = arrayOf(
        arrayOf("00", "01", "02"),
        arrayOf("10", "11", "12"),
        arrayOf("20", "21", "22"),
        arrayOf("00", "11", "22"),
        arrayOf("02", "11", "20"),
        arrayOf("00", "10", "20"),
        arrayOf("01", "11", "21"),
        arrayOf("02", "12", "22")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            roundCount = savedInstanceState.getInt("roundCount")
            player1Score = savedInstanceState.getInt("player1Score")
            player2Score = savedInstanceState.getInt("player2Score")
            player1Turn = savedInstanceState.getBoolean("player1Turn")
        }

        setContentView(R.layout.activity_main)

        textViewPLayer1 = findViewById(R.id.text_view_player1)
        textViewPLayer2 = findViewById(R.id.text_view_player2)

        createBoard()

        val resetButton: Button = findViewById(R.id.button_reset)
        resetButton.setOnClickListener {
            resetBoard()
        }
    }

    private fun resetBoard(p1Score: Int = 0, p2Score: Int = 0, turn: Int = 0) {
        for (line in buttons) {
            for (button in line) {
                button.text = ""
            }
        }

        player1Turn = true
        roundCount = turn
        player1Score = p1Score
        player2Score = p2Score
        player1Squares.clear()
        player2Squares.clear()
        updateScore()
    }

    private fun onGameButtonClicked(button: Button, line: String, column: String) {
        if (button.text.isEmpty()) {
            if (player1Turn) {
                button.text = "X"
                player1Squares.add("$line$column")
                player1Turn = false
            } else {
                button.text = "O"
                player2Squares.add("$line$column")
                player1Turn = true
            }
        } else {
            Toast.makeText(this, "This square is already taken!", Toast.LENGTH_SHORT )
                .show()
        }

        checkBoard()

    }

    private fun checkBoard() {
        for (combination in winningCombinations) {
            if (combination.all { it in player1Squares }) {
                player1Score++
                Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show()
                resetBoard(player1Score, player2Score, roundCount)
            } else if (combination.all { it in player2Squares }) {
                player2Score++
                Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show()
                resetBoard(player1Score, player2Score, roundCount)
            } else if (player1Squares.size + player2Squares.size == 9) {
                Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show()
                resetBoard(player1Score, player2Score, roundCount)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("roundCount", roundCount)
        outState.putInt("player1Score", player1Score)
        outState.putInt("player2Score", player2Score)
        outState.putBoolean("player1Turn", player1Turn)
    }


    override fun onStart() {
        super.onStart()
        updateScore()
    }

    private fun updateScore() {
        textViewPLayer1.text = "Player 1: $player1Score"
        textViewPLayer2.text = "Player 2: $player2Score"
    }

    private fun createBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i].add(findViewById(resID))
                buttons[i][j].setOnClickListener {
                    onGameButtonClicked(buttons[i][j], i.toString(), j.toString())
                }
            }
        }
    }
}
