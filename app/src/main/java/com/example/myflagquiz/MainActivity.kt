package com.example.myflagquiz

import android.app.Activity
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import kotlinx.android.synthetic.main.game_layout.*
import kotlinx.android.synthetic.main.game_navigator.*
import kotlinx.android.synthetic.main.game_navigator.view.*
import kotlinx.android.synthetic.main.question_layout.*
import kotlinx.android.synthetic.main.question_layout.imageQuestion
import kotlinx.android.synthetic.main.question_layout.questionTitle
import kotlinx.android.synthetic.main.victory_layout.*
import kotlinx.android.synthetic.main.welcome_layout.*

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

class MainActivity : AppCompatActivity() {

    var name: String = ""
    var welcomeVisible = true
    var gameVisible = false
    var score = 0
    var currentTry = 0
    var numberOfQuestions = 0
    var currentQuestion: Question? = null

    var questionList: ArrayList<Question> = ArrayList<Question>(4)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionOneQuestions = ArrayList<String>()
        questionOneQuestions.add("Serbia")
        questionOneQuestions.add("Hungary")
        questionOneQuestions.add("Italy")
        questionOneQuestions.add("Brazil")
        val question1 = Question("serbian", "Question One", questionOneQuestions, "Serbia")

        val questionTwoQuestions = ArrayList<String>()
        questionTwoQuestions.add("Czech")
        questionTwoQuestions.add("Hungary")
        questionTwoQuestions.add("Italy")
        questionTwoQuestions.add("Brazil")
        val question2 = Question("czech", "Question Two", questionTwoQuestions, "Czech")

        val questionThreeQuestions = ArrayList<String>()
        questionThreeQuestions.add("Philippines")
        questionThreeQuestions.add("Estonia")
        questionThreeQuestions.add("Uganda")
        questionThreeQuestions.add("Dominican")
        val question3 =
            Question("philippines", "Question Three", questionThreeQuestions, "Philippines")

        val questionFourQuestions = ArrayList<String>()
        questionFourQuestions.add("Russia")
        questionFourQuestions.add("Finland")
        questionFourQuestions.add("Norway")
        questionFourQuestions.add("Sweden")
        val question4 = Question("russian", "Question Four", questionFourQuestions, "Russia")

        questionList.add(question1)
        questionList.add(question2)
        questionList.add(question3)
        questionList.add(question4)

        numberOfQuestions = questionList.size
        scoreView.text = "$numberOfQuestions"
        currentScoreView.text = "$score"
    }

    fun startButton(view: View) {
        val text = plain_text_input.text.toString()
        currentTry = 0
        questionList.shuffle()
        currentScoreView.text = "$score"

        if (text.trim().isNotBlank() && welcomeVisible) {
            name = text
            welcomeVisibility()
            view.hideKeyboard()

            nextQuestion()

        }
    }

    private fun welcomeVisibility() {
        welcomeLayout.visibility = View.GONE
        welcomeVisible = false
        gameLayout.visibility = View.VISIBLE
        gameVisible = true
        victoryScreen.visibility = View.GONE
    }

    fun restartViewOnclick(view: View) {
        name = ""
        welcomeVisible = true
        gameVisible = false
        score = 0

        welcomeLayout.visibility = View.VISIBLE
        gameLayout.visibility = View.GONE
    }

    fun answerButton(view: View) {

        val button = view as Button
        val text = button.text as String
        if (text.toLowerCase() == currentQuestion?.correctAnswer?.toLowerCase()
            && currentTry < questionList.size
        ) {
            score += 1
            currentScoreView.text = "$score"
        }

        // setup next question per currentTry
        currentTry++
        if (currentTry < questionList.size) {
            nextQuestion(currentTry)
        }

        // present final screen if last question answered
        if (currentTry == questionList.size) {
            welcomeLayout.visibility = View.GONE
            gameLayout.visibility = View.GONE
            victoryScreen.visibility = View.VISIBLE
            gameLayout.currentScoreView.text = "$score"
            gameLayout.scoreView.text = "${questionList.size}"
            currentScoreView.text = "$score"
        }

    }

    fun nextQuestion(index: Int = 0) {
        var question = questionList[index]
        currentQuestion = question
        question.shuffleAnswerList()

        questionTitle.text = question.questionString

        imageQuestion.setImageResource(
            resources.getIdentifier(
                "${question.flagName}", "drawable",
                packageName
            )
        );
        answer1.text = question.answerList[0]
        answer2.text = question.answerList[1]
        answer3.text = question.answerList[2]
        answer4.text = question.answerList[3]
    }

}
