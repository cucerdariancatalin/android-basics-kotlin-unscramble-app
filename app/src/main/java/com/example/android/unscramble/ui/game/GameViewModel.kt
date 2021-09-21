package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

const val TAG = "GameFragment"

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {

    // List of words used in the game
    private val wordsList = mutableListOf<String>()

    private lateinit var currentWord: String

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    init {
        Log.d(TAG, "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "GameViewModel destroyed!")
    }

    /**
     * Updates currentWord and currentScrambledWord with the next word.
     */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()

        while (tempWord.contentEquals(currentWord.toCharArray())) {
            tempWord.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /**
     * Returns true if the current word count is less than MAX_NO_OF_WORDS
     */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    /**
     * Increases the game score if the player's word is correct.
     */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /**
     * Returns true if the player word is correct.
     * Increases the score accordingly.
     */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /**
     * Re-initializes the game data to restart the game.
     */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}
