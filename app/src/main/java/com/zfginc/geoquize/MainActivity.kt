package com.zfginc.geoquize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var true_button:Button;
    private lateinit var false_button:Button;

    private lateinit var next_button:ImageButton;
    private lateinit var previos_button:ImageButton;

    private lateinit var questionTextView: TextView;
    private lateinit var toast:Toast

    private var currentindex = 0;
    private val questionBank = listOf(
        Question(R.string.question_0, true),
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false),
        Question(R.string.question_5, false),
    );

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        true_button = findViewById(R.id.true_button);
        false_button = findViewById(R.id.false_button);
        next_button = findViewById(R.id.next_button);
        previos_button = findViewById(R.id.previos_button);
        questionTextView = findViewById(R.id.question_text);

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
            showToast(R.string.next_toast)
            nextQuestion();
        }

        val questionTextResId = questionBank[currentindex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun trueButton(){
        if(questionBank[currentindex].answer)
            showToast(R.string.currect_toast);
        else
            showToast(R.string.incurrect_toast);

        writeAnswer(true);
        nextQuestion();
    }
    private fun falseButton() {
        if(!questionBank[currentindex].answer)
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
        currentindex++;
        if(currentindex == questionBank.size) currentindex = questionBank.size - 1;

        val questionTextResId = questionBank[currentindex].textResId;
        questionTextView.setText(questionTextResId);

        checkAndLockButtons();
        checkAndShowAnswered();
    }
    private fun previosQuestion(){
        currentindex--;
        if(currentindex < 0) currentindex = 0;

        val questionTextResId = questionBank[currentindex].textResId;
        questionTextView.setText(questionTextResId);

        checkAndLockButtons();
    }

    private fun writeAnswer(answer : Boolean){
        questionBank[currentindex].isAnswered = answer;
    }
    private fun checkAndLockButtons(){
        if (questionBank[currentindex].isAnswered != null){
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

    private fun isAllAnswered() : Boolean {
        for(quest in questionBank){
            if(quest.isAnswered == null) return false;
        }
        return true;
    }
    private fun countTrueAnswered() : Int {
        var count : Int = 0;
        for(quest in questionBank){
            if(quest.isAnswered == quest.answer) count++;
        }

        return count;
    }
    private fun checkAndShowAnswered(){
        if(isAllAnswered()) {
            var count : Int = countTrueAnswered();
            var max : Int = questionBank.size;
            var procent: Double = ((count.toDouble() / max) * 10000).roundToInt() / 100.0;

            showToast("Вы ответили верно: " + count + "/" + max + "  -  " + procent + "%")
        }
    }
}