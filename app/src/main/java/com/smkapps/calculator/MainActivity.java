package com.smkapps.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView screen_tv;

    private String enteredNumbers = "";
    private String buffer = "";
    private String currentOperator;

    private Boolean firstNumberEntered = false;
    private boolean isResultGot = false;

    private double numberOne;
    private double numberTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screen_tv = (TextView) findViewById(R.id.screen);

    }

    public void onNumberClick(View view) {
        Button b = (Button) view;
        if (isResultGot) enteredNumbers = "";

        String value = b.getText().toString();
        if (enteredNumbers.equals("") && value.equals(".")) {
            enteredNumbers = "0.";
        }
        if (!(enteredNumbers.contains(".") && value.equals(".")))
            enteredNumbers += value;

        screen_tv.setText(buffer + enteredNumbers);

        isResultGot = false;
    }

    public void onReset(View view) {
        enteredNumbers = "";
        buffer = "";
        numberOne = 0;
        screen_tv.setText("0");
    }

    public void changePlusOrMinus(View view) {

        if (!enteredNumbers.equals("0") && !enteredNumbers.equals("")) {

            if ((enteredNumbers.charAt(0) != '-')) {
                enteredNumbers = "-" + enteredNumbers;

            } else {
                enteredNumbers = enteredNumbers.substring(1);
            }
            screen_tv.setText(buffer + enteredNumbers);
        }
        if (enteredNumbers.isEmpty() && !buffer.isEmpty()) {
            if ((buffer.charAt(0) != '-')) {
                buffer = "-" + buffer;
            } else {
                buffer = buffer.substring(1);
            }
            numberOne *= -1;
            screen_tv.setText(buffer + enteredNumbers);
        }


    }

    public void onPercentClick(View view) {

        if (firstNumberEntered) {

            if (enteredNumbers.isEmpty()) {
                numberTwo = 0;
            } else {
                numberTwo = Double.valueOf(enteredNumbers);
            }
            numberTwo = numberOne * numberTwo / 100;

            if (numberTwo % 1 == 0) {
                enteredNumbers = (int) numberTwo + "";
            } else enteredNumbers = numberTwo + "";

            screen_tv.setText(buffer + enteredNumbers);

            isResultGot = true;
        }

    }

    public void onOperatorClick(View view) {
        Button b = (Button) view;

        if (!firstNumberEntered) {
            if (enteredNumbers.isEmpty()) {
                numberOne = 0;
            } else {
                numberOne = Double.valueOf(enteredNumbers);
            }
            currentOperator = b.getText().toString();
            setBuffer();
            enteredNumbers = "";
            firstNumberEntered = true;

        } else {
            if (enteredNumbers.isEmpty()) {
                currentOperator = b.getText().toString();
                setBuffer();
            } else {
                numberTwo = Double.valueOf(enteredNumbers);
                try {
                    numberOne = calculate();
                    currentOperator = b.getText().toString();
                    setBuffer();
                    enteredNumbers = "";

                } catch (Exception e) {
                    e.printStackTrace();
                    screen_tv.setText("Err");
                    enteredNumbers = "";
                    firstNumberEntered = false;
                    buffer = "";
                    isResultGot = true;
                }
            }
        }

        enteredNumbers = "";
    }

    private void setBuffer() {
        if (numberOne % 1 == 0) {
            buffer = ((int) numberOne + "\n" + currentOperator + "\n");
        } else {
            buffer = (numberOne + "\n" + currentOperator + "\n");
        }
        screen_tv.setText(buffer);
    }


    public void onEqualsClick(View view) {
        if (firstNumberEntered) {
            if (enteredNumbers.isEmpty()) numberTwo = 0;
            else
                numberTwo = Double.valueOf(enteredNumbers);
            try {
                numberOne = calculate();
                if (numberOne % 1 == 0) {
                    screen_tv.setText((int) numberOne + "");
                } else {
                    screen_tv.setText(numberOne + "");
                }

            } catch (Exception e) {
                e.printStackTrace();
                screen_tv.setText("Err");
                numberOne = 0;
            }


            enteredNumbers = numberOne + "";
            if (numberOne % 1 == 0) enteredNumbers = (int) numberOne + "";
            firstNumberEntered = false;
            buffer = "";

        }
        isResultGot = true;
    }


    private double calculate() throws Exception {
        double temp = 0;
        switch (currentOperator) {
            case "+":
                temp = numberOne + numberTwo;
                break;
            case "-":
                temp = numberOne - numberTwo;
                break;
            case "*":
                temp = numberOne * numberTwo;
                break;
            case "/":
                if (numberTwo != 0)
                    temp = numberOne / numberTwo;
                else
                    throw new Exception();

                break;


        }
        return temp;
    }
}
