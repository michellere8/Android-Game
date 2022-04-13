package com.developerrr.tippytoegame.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.developerrr.tippytoegame.R;
import com.developerrr.tippytoegame.models.AnsOptions;
import com.developerrr.tippytoegame.models.WhoSaidItQuestion;
import com.developerrr.tippytoegame.parcelables.ParcelabeModelLirics;
import com.developerrr.tippytoegame.parcelables.ParcelabeModelWhoSaidit;

import java.util.ArrayList;
import java.util.List;

public class WhoSaidItActivity extends AppCompatActivity implements View.OnClickListener {


    List<WhoSaidItQuestion> questions;
    TextView quistionNumText, questionText;
    Button option1, option2, option3;
    SwitchCompat hardSwitch;
    private int currentQuestionIndex = 0;
    private int correct = 0, wrong = 0;
    String answerText;
    private boolean isHard=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_said_it);

        questions = new ArrayList<>();

        questionText = findViewById(R.id.questionTxt_whosaid);
        quistionNumText = findViewById(R.id.questionNum_whosaid);
        option1 = findViewById(R.id.button_option1);
        option2 = findViewById(R.id.button_option2);
        option3 = findViewById(R.id.button_option3);
        hardSwitch = findViewById(R.id.isHardSwitch);

        transparentStatusAndNavigation();

        createQuestions();
        quistionNumText.setText(String.valueOf(currentQuestionIndex+1));

        updateQuestion(currentQuestionIndex);

        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);

        hardSwitch.setOnCheckedChangeListener((compoundButton, b) -> {
            isHard=b;
            createQuestions();
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        ParcelabeModelWhoSaidit parcelabeModel=new ParcelabeModelWhoSaidit(
                quistionNumText.getText().toString()
                ,questionText.getText().toString(),
                correct,
                wrong,
                currentQuestionIndex,
                option1.getText().toString(),
                option2.getText().toString(),
                option3.getText().toString(),
                String.valueOf(isHard)
        );

        outState.putParcelable("parcelable",parcelabeModel);




    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ParcelabeModelWhoSaidit parcelabeModel=savedInstanceState.getParcelable("parcelable");


        questionText.setText(parcelabeModel.question);
        quistionNumText.setText(parcelabeModel.qno);
        correct=parcelabeModel.correct;
        wrong=parcelabeModel.wrong;
        currentQuestionIndex=parcelabeModel.qnomber;
        option1.setText(parcelabeModel.opt1);
        option2.setText(parcelabeModel.opt2);
        option3.setText(parcelabeModel.opt3);
        isHard=Boolean.valueOf(parcelabeModel.isHard);
        hardSwitch.setChecked(isHard);


    }


    private void updateQuestion(int index) {
        if (currentQuestionIndex == questions.size()) {
            showResultDialog();
        } else {
            WhoSaidItQuestion curentQ=questions.get(index);
            option1.setText(curentQ.getAnswers().get(0).getAns());
            option2.setText(curentQ.getAnswers().get(1).getAns());
            option3.setText(curentQ.getAnswers().get(2).getAns());

            questionText.setText(questions.get(index).getQuestion());
            quistionNumText.setText(String.valueOf(currentQuestionIndex+1));
        }
    }

    private void showResultDialog() {


        new AlertDialog.Builder(WhoSaidItActivity.this, R.style.AlertDialogTheme)
                .setTitle("Test Over")
                .setMessage("Your Test is Over Wanna see the results.?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    new AlertDialog.Builder(WhoSaidItActivity.this, R.style.AlertDialogTheme)
                            .setTitle("Results:")
                            .setMessage(
                                    "Total Questions: " + questions.size()
                                            + "\nCorrect Answers: " + correct +
                                            "\nWrong Answers: " + wrong
                            )

                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    desableButtons();
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void desableButtons() {
        option1.setEnabled(false);
        option2.setEnabled(false);
        option3.setEnabled(false);
    }

    private void createQuestions() {

        questions.clear();
        currentQuestionIndex=0;
        correct=0;
        wrong=0;

        AnsOptions a = new AnsOptions("Batman", false);
        AnsOptions b = new AnsOptions("Superman", false);
        AnsOptions c = new AnsOptions("Buzz Lightyear", true);
        ArrayList<AnsOptions> options1 = new ArrayList<>();
        options1.add(a);
        options1.add(b);
        options1.add(c);
        WhoSaidItQuestion question1 = new WhoSaidItQuestion(
                "To infinity and beyond",
                options1, false
        );


        AnsOptions a2 = new AnsOptions("Batman", true);
        AnsOptions b2 = new AnsOptions("Superman", false);
        AnsOptions c2 = new AnsOptions("Hulk", false);

        ArrayList<AnsOptions> options2 = new ArrayList<>();
        options2.add(a2);
        options2.add(b2);
        options2.add(c2);
        WhoSaidItQuestion question2 = new WhoSaidItQuestion(
                "This City Needs Me.",
                options2, false
        );

        AnsOptions a3 = new AnsOptions("Rose", true);
        AnsOptions b3 = new AnsOptions("Uncle Ben", false);
        AnsOptions c3 = new AnsOptions("Joker", false);

        ArrayList<AnsOptions> options3 = new ArrayList<>();
        options3.add(a3);
        options3.add(b3);
        options3.add(c3);
        WhoSaidItQuestion question3 = new WhoSaidItQuestion(
                "I will never let go, Jack",
                options3, false
        );


        AnsOptions a4 = new AnsOptions("Iron man", false);
        AnsOptions b4 = new AnsOptions("Hulk", true);
        AnsOptions c4 = new AnsOptions("Superman", false);

        ArrayList<AnsOptions> options4 = new ArrayList<>();
        options4.add(a4);
        options4.add(b4);
        options4.add(c4);
        WhoSaidItQuestion question4 = new WhoSaidItQuestion(
                "That's my secret Captain. I'm always angry",
                options4, false
        );


        AnsOptions a5 = new AnsOptions("Batman", false);
        AnsOptions b5 = new AnsOptions("Uncle Ben", false);
        AnsOptions c5 = new AnsOptions("Superman", true);

        ArrayList<AnsOptions> options5 = new ArrayList<>();
        options5.add(a5);
        options5.add(b5);
        options5.add(c5);
        WhoSaidItQuestion question5 = new WhoSaidItQuestion(
                "There's a superhero in all of us. we just need the courage to put on the cap",
                options5, false
        );

        AnsOptions a6 = new AnsOptions("Nicola Tesla", false);
        AnsOptions b6 = new AnsOptions("Uncle Ben", true);
        AnsOptions c6 = new AnsOptions("Joker", false);

        ArrayList<AnsOptions> options6 = new ArrayList<>();
        options6.add(a6);
        options6.add(b6);
        options6.add(c6);
        WhoSaidItQuestion question6 = new WhoSaidItQuestion(
                "With great power comes great responsibility",
                options6, false
        );


        AnsOptions a7 = new AnsOptions("Buzz Lightyear", false);
        AnsOptions b7 = new AnsOptions("Ghostbusters", false);
        AnsOptions c7 = new AnsOptions("Joker", true);

        ArrayList<AnsOptions> options7 = new ArrayList<>();
        options7.add(a7);
        options7.add(b7);
        options7.add(c7);
        WhoSaidItQuestion question7 = new WhoSaidItQuestion(
                "Now, Lets put a smile on that face.",
                options7, false
        );

        ///now start hard questions

        AnsOptions a8 = new AnsOptions("Pippin", false);
        AnsOptions b8 = new AnsOptions("Ghostbusters", false);
        AnsOptions c8 = new AnsOptions("Peik Lin Goh", true);

        ArrayList<AnsOptions> options8 = new ArrayList<>();
        options8.add(a8);
        options8.add(b8);
        options8.add(c8);
        WhoSaidItQuestion question8 = new WhoSaidItQuestion(
                "These people are so posh and snobby, they're snoshy.",
                options8, true
        );

        AnsOptions a9 = new AnsOptions("Batman", false);
        AnsOptions b9 = new AnsOptions("Ghostbusters", false);
        AnsOptions c9 = new AnsOptions("Peter Clemenza", true);

        ArrayList<AnsOptions> options9 = new ArrayList<>();
        options9.add(a9);
        options9.add(b9);
        options9.add(c9);
        WhoSaidItQuestion question9 = new WhoSaidItQuestion(
                "Leave the gun. Take the cannoli.",
                options9, true
        );

        AnsOptions a10 = new AnsOptions("Buzz Lightyear", false);
        AnsOptions b10 = new AnsOptions("Jack Sparrow", true);
        AnsOptions c10 = new AnsOptions("Joker", false);

        ArrayList<AnsOptions> options10 = new ArrayList<>();
        options10.add(a10);
        options10.add(b10);
        options10.add(c10);
        WhoSaidItQuestion question10 = new WhoSaidItQuestion(
                "Now, bring me that horizon.",
                options10, true
        );

        AnsOptions a11 = new AnsOptions("Pippin", true);
        AnsOptions b11 = new AnsOptions("Ghostbusters", false);
        AnsOptions c11 = new AnsOptions("Lorenzo Anello", false);

        ArrayList<AnsOptions> options11 = new ArrayList<>();
        options11.add(a11);
        options11.add(b11);
        options11.add(c11);
        WhoSaidItQuestion question11 = new WhoSaidItQuestion(
                "The closer we are to danger, the further we are from harm.",
                options11, true
        );

        AnsOptions a12 = new AnsOptions("Buzz Lightyear", false);
        AnsOptions b12 = new AnsOptions("Lorenzo Anello", true);
        AnsOptions c12 = new AnsOptions("Peik Lin Goh", false);

        ArrayList<AnsOptions> options12 = new ArrayList<>();
        options12.add(a12);
        options12.add(b12);
        options12.add(c12);
        WhoSaidItQuestion question12 = new WhoSaidItQuestion(
                "The saddest thing is life is wasted talent.",
                options12, true
        );

        AnsOptions a13 = new AnsOptions("Ghostbusters", true);
        AnsOptions b13 = new AnsOptions("Lorenzo Anello", false);
        AnsOptions c13 = new AnsOptions("Jack Sparrow", false);

        ArrayList<AnsOptions> options13 = new ArrayList<>();
        options13.add(a13);
        options13.add(b13);
        options13.add(c13);
        WhoSaidItQuestion question13 = new WhoSaidItQuestion(
                "We came. we saw. we kicked its ass.",
                options13, true
        );

        AnsOptions a14 = new AnsOptions("Buzz Lightyear", false);
        AnsOptions b14 = new AnsOptions("Ghostbusters", false);
        AnsOptions c14 = new AnsOptions("Rocky Balboa", true);

        ArrayList<AnsOptions> options14 = new ArrayList<>();
        options14.add(a14);
        options14.add(b14);
        options14.add(c14);
        WhoSaidItQuestion question14 = new WhoSaidItQuestion(
                "yeah, to you it's Thanksgiving; to me it's Thursday.",
                options14, true
        );

        if(isHard){

            questions.add(question8);
            questions.add(question9);
            questions.add(question10);
            questions.add(question11);
            questions.add(question12);
            questions.add(question13);
            questions.add(question14);
        }else {
            questions.add(question1);
            questions.add(question2);
            questions.add(question3);
            questions.add(question4);
            questions.add(question5);
            questions.add(question6);
            questions.add(question7);
        }




    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_option1:
                answerText = option1.getText().toString().trim();
                String currectAns = getCorrectAns(questions.get(currentQuestionIndex).getAnswers());
                if (answerText.equalsIgnoreCase(currectAns)) {
                    correct++;
                } else {
                    wrong++;
                }
                currentQuestionIndex++;
                updateQuestion(currentQuestionIndex);
                break;
            case R.id.button_option2:
                answerText = option2.getText().toString().trim();
                String currectAns2 = getCorrectAns(questions.get(currentQuestionIndex).getAnswers());
                if (answerText.equalsIgnoreCase(currectAns2)) {
                    correct++;
                } else {
                    wrong++;
                }
                currentQuestionIndex++;
                updateQuestion(currentQuestionIndex);
                break;
            case R.id.button_option3:
                answerText = option3.getText().toString().trim();
                String currectAns3 = getCorrectAns(questions.get(currentQuestionIndex).getAnswers());
                if (answerText.equalsIgnoreCase(currectAns3)) {
                    correct++;
                } else {
                    wrong++;
                }
                currentQuestionIndex++;
                updateQuestion(currentQuestionIndex);
        }
    }

    private String getCorrectAns(ArrayList<AnsOptions> answers) {
        String ans = "";
        for (AnsOptions a : answers) {
            if (a.isCorrect()) {
                ans = a.getAns();
            }
        }

        return ans;
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

