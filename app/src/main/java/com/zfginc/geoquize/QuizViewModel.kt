package com.zfginc.geoquize

import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel";
class QuizViewModel: ViewModel() {

    var currentIndex = 0;

    private val questionBank = listOf(
        Question(R.string.question_0, true),
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, false),
        Question(R.string.question_4, true),
        Question(R.string.question_5, false),
        Question(R.string.question_6, true),
        Question(R.string.question_7, false),
    );

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionAnswered: Boolean?
        get() = questionBank[currentIndex].isAnswered;

    val isAnswered: Boolean
        get() = questionBank[currentIndex].isAnswered != null;

    val countQuestions: Int
        get() = questionBank.size;

    fun setAnswer(answer: Boolean){
        questionBank[currentIndex].isAnswered = answer;
    }

    fun moveToNext() {
        currentIndex++;
        if(currentIndex == questionBank.size) currentIndex = questionBank.size - 1;
    }

    fun moveToPrevios() {
        currentIndex--;
        if(currentIndex < 0) currentIndex = 0;
    }

    fun isAllAnswered() : Boolean {
        for(quest in questionBank){
            if(quest.isAnswered == null) return false;
        }
        return true;
    }

    fun countTrueAnswered() : Int {
        var count : Int = 0;
        for(quest in questionBank){
            if(quest.isAnswered == quest.answer) count++;
        }

        return count;
    }
}