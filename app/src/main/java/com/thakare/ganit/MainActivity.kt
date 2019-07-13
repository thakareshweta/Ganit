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
        listOf("Addition", "Subtraction", "Multiplication", "Division") //use other appropriate data structure here
    var number1 = 0;
    var number2 = 0;
    var expectedAnswer = 0;
    var startTime = System.currentTimeMillis();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        idRefresh.setVisibility(View.INVISIBLE);
        initScreen()
    }

    fun initScreen() {
        var randomOperationIndex = Random.nextInt(0, mathematicalOperationList.size - 1)
        var randomOperation = mathematicalOperationList.get(randomOperationIndex)
        var operatorSign = ""
        number1 = Random.nextInt(0, 50)
        number2 = Random.nextInt(0, 50)
        when (randomOperation) {
            "Addition" -> {
                expectedAnswer = number1 + number2;
                System.out.println("Random operation in switch is  Addition");
                operatorSign = "+"
            }

            "Subtraction" -> {

                System.out.println("Random operation in switch is  Subtraction")
                expectedAnswer = number1 - number2
                operatorSign = "-"
            }
            "Multiplication" -> {
                expectedAnswer = number1 * number2
                System.out.println("Random operation in switch is  Multi")
                operatorSign = "*"
            }
            "Division" -> {
                System.out.println("Random operation in switch is  Div")
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
        val seconds = timeTakenToAsnwer/1000;

        if(Integer.parseInt(answerByUser) == expectedAnswer){
            resultString = "Yaay!! you are a genius!!"
        } else {
            resultString = "No worries!! All you need is some more practice!"
        }
        resultString = resultString + " Time taken to solve the sum:  ${seconds} seconds"

        idResult.setText(resultString)
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

    fun hideKeyboard(view: View) {

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
