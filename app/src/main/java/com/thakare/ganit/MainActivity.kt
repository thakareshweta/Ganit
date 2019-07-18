package com.thakare.ganit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var mathematicalOperationList =
        listOf("Addition", "Subtraction") //use other appropriate data structure here
    var number1 = 0;
    var number2 = 0;
    var expectedAnswer = 0;
    var operatorSign = ""
    var randomOperation = ""
    var startTime = System.currentTimeMillis();
    var currLevel = 0 ;
    var currQuestion = 0 ;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idRefresh.setVisibility(View.INVISIBLE);

        if (savedInstanceState != null) {
            idNumber1.setText(savedInstanceState.getInt("questionnumber1").toString())
            idNumber2.setText(savedInstanceState.getInt("questionnumber2").toString())
            idOperation.setText(savedInstanceState.getString("operation"))
            idOperatorSign.setText(savedInstanceState.getString("operatorSign"))
            expectedAnswer = savedInstanceState.getInt("expectedAnswer")

        } else {
            initScreen()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("questionnumber1", number1)
        outState.putInt("questionnumber2", number2)
        outState.putInt("expectedAnswer", expectedAnswer)
        outState.putString("operation", randomOperation)
        outState.putString("operatorSign", operatorSign)
        super.onSaveInstanceState(outState)
    }

    fun initScreen() {
        number1 = Random.nextInt(2, 50)
        number2 = Random.nextInt(1, number1)
        var randomOperationIndex = Random.nextInt(0, mathematicalOperationList.size)
        randomOperation = mathematicalOperationList.get(randomOperationIndex)
        currQuestion += 1 ;
        when (randomOperation) {
            "Addition" -> {
                number1 = Random.nextInt(1, 50)
                number2 = Random.nextInt(1, 50)
                expectedAnswer = number1 + number2;
                operatorSign = "+"
            }

            "Subtraction" -> {
                expectedAnswer = number1 - number2
                operatorSign = "-"
            }
            "Multiplication" -> {
                expectedAnswer = number1 * number2
                operatorSign = "*"
            }
            "Division" -> {
                expectedAnswer = number1 / number2
                operatorSign = "/"
            }
            else -> print("Invalid mathematical operation");
        }


        idOperation.setText(randomOperation)
        idNumber1.setText(number1.toString())
        idNumber2.setText(number2.toString())
        idOperatorSign.setText(operatorSign)
        idAnswer.setText("")
        startTime = System.currentTimeMillis();
    }

    fun submitAnswer(view: View) {
        hideKeyboard(view)
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
        val seconds = timeTakenToAsnwer / 1000;
        
        if (Integer.parseInt(answerByUser) == expectedAnswer) {
            resultString = "Yaay!! you are a genius!!"
        } else {
            resultString = "No worries!! All you need is some more practice!, Correct Answer is ${expectedAnswer}. "
        }

        resultString = resultString + " Time taken to solve the sum:  ${seconds} seconds"
        if(currQuestion >= 10 ) {
            // Level Up
            currLevel += 1 ;
            currQuestion = 0 ;
            resultString  += " LEVEL UP to ${currLevel}"
        } else {
            resultString  += " LEVEL ${currLevel} Q ${currQuestion}"
        }

        idResult.setText(resultString)
    }

    fun refreshSlate(view: View) {
        recreate() // Commented since it caused flicker in the App
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

    fun hideKeyboard(view: View) {

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
