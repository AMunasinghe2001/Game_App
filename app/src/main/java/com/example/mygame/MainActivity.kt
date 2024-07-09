package com.example.mygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.content.Context

class MainActivity : AppCompatActivity(), GameTask {
    // Constants for SharedPreferences keys
    private val PREFS_NAME = "MyGamePrefs"
    private val HIGHEST_SCORE_KEY = "HighestScore"

    lateinit var rootLayout: LinearLayout
    lateinit var startBtn: Button
    lateinit var mGameView: GameView
    lateinit var score: TextView
    lateinit var highestScoreTextView: TextView
    var highestScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startBtn = findViewById(R.id.startBtn)
        rootLayout = findViewById(R.id.rootLayout)
        score = findViewById(R.id.score)
        highestScoreTextView = findViewById(R.id.highestScore)
        mGameView = GameView(this, this)

        // Initialize SharedPreferences
        val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        highestScore = prefs.getInt(HIGHEST_SCORE_KEY, 0)
        updateHighestScore(highestScore)

        startBtn.setOnClickListener {
            mGameView.setBackgroundResource(R.drawable.road)
            rootLayout.addView(mGameView)
            startBtn.visibility = View.GONE
            score.visibility = View.GONE
        }
    }

    override fun closeGame(mScore: Int) {
        score.text = "Score : $mScore"
        rootLayout.removeView(mGameView)
        startBtn.visibility = View.VISIBLE
        score.visibility = View.VISIBLE

        if (mScore > highestScore) {
            highestScore = mScore
            updateHighestScore(highestScore)

            // Save new highest score to SharedPreferences
            val prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putInt(HIGHEST_SCORE_KEY, highestScore).apply()
        }

        if (mScore > 0) {
            mGameView = GameView(this, this)
            startBtn.setOnClickListener {
                mGameView.setBackgroundResource(R.drawable.road)
                rootLayout.addView(mGameView)
                startBtn.visibility = View.GONE
                score.visibility = View.GONE
            }
        }
    }

    internal fun updateHighestScore(newScore: Int) {
        highestScoreTextView.text = "Highest Score: $newScore"
    }
}
