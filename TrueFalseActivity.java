package com.developerrr.tippytoegame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.developerrr.tippytoegame.R;
import com.developerrr.tippytoegame.parcelables.ParcelabeModel;
import com.developerrr.tippytoegame.models.TrueFalseQuestion;

import java.util.ArrayList;
import java.util.List;

public class TrueFalseActivity extends AppCompatActivity {


    List<TrueFalseQuestion> questions;
    TextView questionText,clockText,questionNumText;
    Button btn_true,btn_false,btn_play;
    int currentQuestionIndex=0;
    private int correct=0,wrong=0;
    CountDownTimer timer;
    private int counter=0;
    boolean isTimeOn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_false);

        questions=new ArrayList<>();
        createQuestions();

        questionText=findViewById(R.id.questionTxt);
        clockText=findViewById(R.id.clock_text);
        btn_false=findViewById(R.id.button_false);
        btn_true=findViewById(R.id.button_true);
        btn_play=findViewById(R.id.button_play);
        questionNumText=findViewById(R.id.questionNum_tf);


        initialIzations();


    }

    private void initialIzations() {
        updateQuestion(currentQuestionIndex);
        questionNumText.setText(String.valueOf(currentQuestionIndex+1));

        transparentStatusAndNavigation();




        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isTimeOn=true;
                timer=new CountDownTimer(60000, 1000){
                    public void onTick(long millisUntilFinished){
                        if (counter<=9) {
                            clockText.setText("00:0" + String.valueOf(counter));
                        }else {
                            clockText.setText("00:" + String.valueOf(counter));
                        }
                        counter++;
                    }
                    public  void onFinish(){
                        showResultDialog();
                    }
                };
                timer.start();
            }
        });

        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTimeOn) {
                    if (questions.get(currentQuestionIndex).isTrue()) {
                        correct++;
                        currentQuestionIndex++;
                        updateQuestion(currentQuestionIndex);
                    }else {
                        wrong++;
                        currentQuestionIndex++;
                        updateQuestion(currentQuestionIndex);
                    }
                }else {
                    Toast.makeText(TrueFalseActivity.this, "Start Timer First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTimeOn) {
                    if (!questions.get(currentQuestionIndex).isTrue()) {
                        correct++;
                        currentQuestionIndex++;
                        updateQuestion(currentQuestionIndex);
                    }else {
                        wrong++;
                        currentQuestionIndex++;
                        updateQuestion(currentQuestionIndex);
                    }
                }else {
                    Toast.makeText(TrueFalseActivity.this, "Start Timer First", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        ParcelabeModel parcelabeModel=new ParcelabeModel(
                questionNumText.getText().toString()
                ,questionText.getText().toString()
                ,String.valueOf(counter),
                correct,
                wrong,
                currentQuestionIndex
        );

        outState.putParcelable("parcelable",parcelabeModel);




    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ParcelabeModel parcelabeModel=savedInstanceState.getParcelable("parcelable");

        counter=Integer.parseInt(parcelabeModel.time);
        questionText.setText(parcelabeModel.question);
        questionNumText.setText(parcelabeModel.qno);
        correct=parcelabeModel.correct;
        wrong=parcelabeModel.wrong;
        currentQuestionIndex=parcelabeModel.qnomber;

        isTimeOn=true;

        timer=new CountDownTimer(60000, 1000){
            public void onTick(long millisUntilFinished){
                if (counter<=9) {
                    clockText.setText("00:0" + String.valueOf(counter));
                }else {
                    clockText.setText("00:" + String.valueOf(counter));
                }
                counter++;
            }
            public  void onFinish(){
                showResultDialog();

            }
        };
        timer.start();




    }


    private void showResultDialog() {

        btn_false.setEnabled(false);
        btn_true.setEnabled(false);

        timer.cancel();

        clockText.setText("0.0");
        new AlertDialog.Builder(TrueFalseActivity.this,R.style.AlertDialogTheme)
                .setTitle("Time's Up")
                .setMessage("Your Time is Over Wanna see the results.?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new AlertDialog.Builder(TrueFalseActivity.this,R.style.AlertDialogTheme)
                                .setTitle("Results:")
                                .setMessage(
                                        "Total Questions: "+questions.size()
                                        +"\nCorrect Answers: "+correct+
                                        "\nWrong Answers: "+wrong+
                                        "\nTime Taken : "+counter+" Seconds"
                                )

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        counter=0;
                        timer.cancel();
                        isTimeOn=false;
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void updateQuestion(int index) {
        if(currentQuestionIndex==questions.size()){
            showResultDialog();
        }else if(correct==5){
            showResultDialog();
        }else {
            questionNumText.setText(String.valueOf(currentQuestionIndex+1));
            questionText.setText(questions.get(index).getQuestion());
        }
    }

    private void createQuestions() {

        TrueFalseQuestion question=new TrueFalseQuestion(
                "The Human Body has 202 bones.",
                false
        );
        TrueFalseQuestion question1=new TrueFalseQuestion(
                "Lightning Never Strikes The same place twice.",
                false
        );
        TrueFalseQuestion question2=new TrueFalseQuestion(
                "Humans Use Only 10% Of Their Brain.",
                false
        );
        TrueFalseQuestion question3=new TrueFalseQuestion(
                "Sharks Don't Get Cancer.",
                false
        );
        TrueFalseQuestion question4=new TrueFalseQuestion(
                "Bulls Become Angry At The Color Red.",
                false
        );


        TrueFalseQuestion question5=new TrueFalseQuestion(
                "Bats Are The Only Mammal That Can Actually Fly.",
                true
        );
        TrueFalseQuestion question6=new TrueFalseQuestion(
                "Elephants Can't Jump.",
                true
        );
        TrueFalseQuestion question7=new TrueFalseQuestion(
                "Cows don't actually have four stomachs the have one stomach with four compartments.",
                true
        );
        TrueFalseQuestion question8=new TrueFalseQuestion(
                "Polar bears have black skin",
                true
        );
        TrueFalseQuestion question9=new TrueFalseQuestion(
                "There are no muscles in your fingers.",
                true
        );

        questions.add(question);
        questions.add(question1);
        questions.add(question7);
        questions.add(question5);
        questions.add(question2);
        questions.add(question9);
        questions.add(question3);
        questions.add(question4);
        questions.add(question6);
        questions.add(question8);



    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}