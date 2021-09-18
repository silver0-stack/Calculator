package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.UiModeManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public UiModeManager uiModeManager;
    public static final String MyPREFERENCES = "nightModePrefs";
    public static final String KEY_ISNIGHTMODE = "isNightMode";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public Switch toggleBtn; //다크모드 토글 버튼 변수 선언
    private String display = ""; //결과텍스트 String 변수 선언
    private EditText inputText; //입력텍스트 변수 선언
    private TextView displayText; //결과텍스트 변수 선언
    public Button clear; //모두 지우는 버튼 변수 선언
    public Button div;
    public Button multiply;
    public Button minus;
    public Button add;
    public Button zero;
    public Button one;
    public Button two;
    public Button three;
    public Button four;
    public Button five;
    public Button six;
    public Button seven;
    public Button eight;
    public Button nine;
    public Button equal;
    public ImageButton back; //뒤로가기 버튼 변수 선언
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        inputText = findViewById(R.id.input);
        displayText = findViewById(R.id.result);
        toggleBtn = findViewById(R.id.toggleBtn);
        clear = findViewById(R.id.clear);
        back = findViewById(R.id.back);
        div = findViewById(R.id.div);
        multiply = findViewById(R.id.multiply);
        minus = findViewById(R.id.minus);
        add = findViewById(R.id.add);
        equal = findViewById(R.id.equal);
        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);

        checkNightModeActivated();
        toggleBtn.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {//when switching to darkmode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightModeState(true);
            } else {//when switching to daymode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightModeState(false);
            }
            recreate();
        });

        back.setOnClickListener(view -> deleteNumber()); //back setOnClickListener
    }


    
    /*back 버튼 클릭시 호출되는 함수*/
    private void deleteNumber() {
        this.inputText.getText().delete(getInput().length() - 1, getInput().length());
    }

    /*숫자 클릭 시 호출되는 함수*/
    public void ClickNumber(View v) {
        Button button = (Button) v; //버튼 변수 선언
        display += button.getText();//버튼 텍스트를 결과값 String인 display에 누적
        appendToLast(display); //display를 appendToLast함
        display = "";//display 지우기
    }

    /*EditText인 inputText를 텍스트화(getText())해서 display를 추가*/
    private void appendToLast(String display) {
        this.inputText.getText().append(display);
    }

    /*연산자 출력 시 호출되는 함수*/
    public void ClickOperator(View v) {
        Button button = (Button) v;
        display += button.getText(); //연산자 텍스트를 display에 누적
        /*입력이 연산자로 끝난다면*/
        if (endsWithOperator()) {
            replace(display);
        }
        /*입력이 숫자로 끝난다면*/
        else {
            appendToLast(display);
        }

    }

    /*???*/
    private void replace(String display) {
        inputText.getText().replace(getInput().length() - 1, getInput().length(), display);
    }

    /*  + /- /* /÷ /x 로끝난다면  */
    private boolean endsWithOperator() {
        return getInput().endsWith("+") || getInput().endsWith("-") || getInput().endsWith("\u00F7") || getInput().endsWith("x");
    }

    /*EditText인 inputText를 String 값으로 가져오는 함수*/
    private String getInput() {
        return this.inputText.getText().toString();
    }

    private void saveNightModeState(boolean nightmode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_ISNIGHTMODE, nightmode);
        editor.apply();

    }

    public void checkNightModeActivated() {
        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {
            toggleBtn.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            toggleBtn.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //    /* = 클릭 시 호출되는 함수 */
//    public void equalResult(View v){
//        String input=getInput();
//
//        /*입력이 숫자로 끝났다면*/
//        if(!endsWithOperator()){
//            /*입력에 x가 포함된다면*/
//            if(input.contains("x")){
//                input=input.replaceAll("x","*"); //'*'로 모두 대체
//            }
//            /*입력에 ÷가 포함된다면*/
//            if(input.contains("\u00F7")){
//                input=input.replaceAll("\u00F7","/"); // '/'로 모두 대체
//            }
//
//            XPathExpression expression=new XPathExpressionBuilder(input).build();
//        }
//        else
//    }
//
//

    public void equalResult(View view) {
        String v1="", v2="";
        String op = "";
        if (!endsWithOperator()) {
            /*계산결과를 결과텍스트에 반영*/
            switch (op) {
                case "+":
                    displayText.setText(String.valueOf(Double.parseDouble(v1) + Double.parseDouble(v2)));
                case "-":
                    displayText.setText(String.valueOf(Double.parseDouble(v1) - Double.parseDouble(v2)));
                case "x":
                    displayText.setText(String.valueOf(Double.parseDouble(v1) * Double.parseDouble(v2)));
                case "\u00F7":
                    displayText.setText(String.valueOf(Double.parseDouble(v1) / Double.parseDouble(v2)));
                default:
            }
        } else displayText.setText(""); //입력이 연산자로 끝났다면 결과텍스트 비우기

    }

    /*C 버튼 클릭 시 호출되는 함수*/
    public void ClearButton(View view) {
        inputText.getText().clear(); //입력텍스트 초기화
        displayText.setText(""); //결과텍스트 지우기

    }
}

