package com.example.myflagquiz

class Question(val flagName: String, val questionString: String, var answerList: ArrayList<String>, val correctAnswer: String) {
    public fun shuffleAnswerList () {
        answerList.shuffle()
    }

}