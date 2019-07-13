package com.thakare.ganit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var mathematicalOperationList =
        listOf("Addition", "Subtraction", "Multiplication", "Division") //use other appropriate data structure here
    var number1 = 0;
    var number2 = 0;
    var questionString = "";
    var expectedAnswer = 0;
    var startTime = System.currentTimeMillis();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idRefresh.setVisibility(View.INVISIBLE);
        initScreen()
        if(savedInstanceState!= null ) {

        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("questionnumber1", number1 )
        outState.putInt("questionnumber1", number2 )
        //outState.putInt("questionnumber1", number1 ) This is if the user has added any answer

    }

    fun initScreen() {
        var randomOperationIndex = Random.nextInt(0, mathematicalOperationList.size - 1)
        var randomOperation = mathematicalOperationList.get(randomOperationIndex)
        number1 = Random.nextInt(0, 50)
        number2 = Random.nextInt(0, 50)
        when (randomOperation) {
            "Addition" -> {
                expectedAnswer = number1 + number2;
                System.out.println("Random operation in switch is  Addition");
                questionString = "${number1} + ${number2} = ?"
            }

            "Subtraction" -> {

                System.out.println("Random operation in switch is  Subtraction")
                expectedAnswer = number1 - number2
                questionString = "${number1} - ${number2} = ?"
            }
            "Multiplication" -> {
                expectedAnswer = number1 * number2
                System.out.println("Random operation in switch is  Multi")
                questionString = "${number1} * ${number2} = ?"
            }
            "Division" -> {
                System.out.println("Random operation in switch is  Div")
                expectedAnswer = number1 / number2
                questionString = "${number1} / ${number2} = ?"
            }
            else -> print("Invalid mathematical operation");
        }

        idOperation.setText(randomOperation)
        idQuestion.setText(questionString)
        idAnswer.setText("")
        startTime = System.currentTimeMillis();
    }

    fun submitAnswer(view: View) {
        //idAnswer = Answer inputted by user
        // expected Answer
        var answerByUser = idAnswer.text.toString();
        if (answerByUser.equals("")) {
            Toast.makeText(this, "Please Enter answer before clicking submit", Toast.LENGTH_SHORT).show();
            return;
        }
        idRefresh.setVisibility(View.VISIBLE);
        idSubmit.setVisibility(View.INVISIBLE);
        var resultString = ""
        var endTime = System.currentTimeMillis();
        var timeTakenToAsnwer = endTime - startTime;
        val seconds = timeTakenToAsnwer/1000;



        if(Integer.parseInt(answerByUser) == expectedAnswer){
            resultString = "Yaay!! you are a genius!!"
        } else {
            resultString = "No worries!! All you need is some more practice!"
        }
        resultString = resultString + " Time taken to solve the sum:  ${seconds} seconds"

        idResult.setText(resultString)
        System.out.println("I am inside submit")
        System.out.println("THe answer submitted is : " + idAnswer.text);
        System.out.println("THe expected answer is  " + expectedAnswer);


    }

    fun refreshSlate(view: View) {
        recreate()
        initScreen()
        idResult.setVisibility(View.INVISIBLE)
        idRefresh.setVisibility(View.INVISIBLE);
        idSubmit.setVisibility(View.VISIBLE)
    }

    override fun recreate() {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            super.recreate()
        } else {
            startActivity(intent)
            finish()
        }
    }
}
