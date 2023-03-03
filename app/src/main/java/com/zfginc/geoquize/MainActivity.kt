package com.zfginc.geoquize

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var true_button:Button
    private lateinit var false_button:Button
    private lateinit var next_button:Button
    private lateinit var questionTextView: TextView

    private lateinit var toast:Toast

    private val questionBank = listOf(
        Question(R.string.question_0, true),
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false),
        Question(R.string.question_5, false),
    )

    private var currentindex = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        true_button = findViewById(R.id.true_button);
        false_button = findViewById(R.id.false_button);
        next_button = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text);

        true_button.setOnClickListener(){
            showToast(R.string.currect_toast)
        }

        false_button.setOnClickListener(){
            showToast(R.string.incurrect_toast)
        }

        next_button.setOnClickListener(){
            showToast(R.string.next_toast)
            updateQuestion()
        }

        val questionTextResId = questionBank[currentindex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun showToast(text: Int){
        toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show()
    }

    private fun updateQuestion(){
        currentindex = (currentindex+1) % questionBank.size;
        val questionTextResId = questionBank[currentindex].textResId
        questionTextView.setText(questionTextResId)
    }
}