package com.zfginc.geoquize

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModelProviders
import kotlin.math.roundToInt

private const val TAG = "MainActivity";
private const val KEY_INDEX = "SAVE_QUIZ"

class MainActivity : AppCompatActivity() {
    private lateinit var true_button:Button;
    private lateinit var false_button:Button;

    private lateinit var next_button:ImageButton;
    private lateinit var previos_button:ImageButton;

    private lateinit var questionTextView: TextView;
    private lateinit var number_question: TextView;
    private lateinit var toast:Toast

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        true_button = findViewById(R.id.true_button);
        false_button = findViewById(R.id.false_button);
        next_button = findViewById(R.id.next_button);
        previos_button = findViewById(R.id.previos_button);
        questionTextView = findViewById(R.id.question_text);
        number_question = findViewById(R.id.number_question);

        true_button.setOnClickListener(){
            trueButton();
        }
        false_button.setOnClickListener(){
            falseButton();
        }

        next_button.setOnClickListener(){
            nextQuestion();
        }
        previos_button.setOnClickListener(){
            previosQuestion();
        }

        questionTextView.setOnClickListener(){
            nextQuestion();
        }

        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun trueButton(){
        if(quizViewModel.currentQuestionAnswer)
            showToast(R.string.currect_toast);
        else
            showToast(R.string.incurrect_toast);

        writeAnswer(true);
        nextQuestion();
    }
    private fun falseButton() {
        if(!quizViewModel.currentQuestionAnswer)
            showToast(R.string.currect_toast);
        else
            showToast(R.string.incurrect_toast);

        writeAnswer(false);
        nextQuestion();
    }

    private fun showToast(text: Int){
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }
    private fun showToast(text: String){
        toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    private fun nextQuestion(){
        quizViewModel.moveToNext();

        val questionTextResId = quizViewModel.currentQuestionText;
        questionTextView.setText(questionTextResId);
        number_question.setText((quizViewModel.currentIndex+1).toString())

        checkAndLockButtons();
        checkAndShowAnswered();
    }
    private fun previosQuestion(){
        quizViewModel.moveToPrevios();

        val questionTextResId = quizViewModel.currentQuestionText;
        questionTextView.setText(questionTextResId);
        number_question.setText((quizViewModel.currentIndex+1).toString())

        checkAndLockButtons();
    }

    private fun writeAnswer(answer : Boolean){
        quizViewModel.setAnswer(answer);
    }
    private fun checkAndLockButtons(){
        if (quizViewModel.isAnswered){
            true_button.isClickable = false;
            false_button.isClickable = false;

            true_button.isEnabled = false;
            false_button.isEnabled = false;
        }
        else{
            true_button.isClickable = true;
            false_button.isClickable = true;

            true_button.isEnabled = true;
            false_button.isEnabled = true;
        }
    }
    private fun checkAndShowAnswered(){
        if(quizViewModel.isAllAnswered()) {
            var count : Int = quizViewModel.countTrueAnswered();
            var max : Int = quizViewModel.countQuestions;
            var procent: Double = ((count.toDouble() / max) * 10000).roundToInt() / 100.0;
            var output: String = "Вы ответили верно: " + count + "/" + max + "  -  " + procent + "%";

            showToast(output);
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
}