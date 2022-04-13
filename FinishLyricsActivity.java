package com.developerrr.tippytoegame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.developerrr.tippytoegame.R;
import com.developerrr.tippytoegame.models.LiricsQuestion;
import com.developerrr.tippytoegame.parcelables.ParcelabeModel;
import com.developerrr.tippytoegame.parcelables.ParcelabeModelLirics;

import java.util.ArrayList;
import java.util.List;

public class FinishLyricsActivity extends AppCompatActivity {

    TextView questionText,questionNumberText;
    EditText editText;
    Button submitBtn;

    List<LiricsQuestion> questions;
    private int currentQuestionIndex=0;
    private int correct=0,wrong=0;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_lyrics);

        questions=new ArrayList<>();

        questionText=findViewById(R.id.questionTxt_lirics);
        editText=findViewById(R.id.editText_lirics);
        submitBtn=findViewById(R.id.button_submit_lirics);
        questionNumberText=findViewById(R.id.questionNum_lirics);
        questionNumberText.setText(String.valueOf(currentQuestionIndex+1));

        mp = MediaPlayer.create(this, R.raw.loose);

        transparentStatusAndNavigation();

        createQuestions();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (questions.get(currentQuestionIndex).getAnswer().equalsIgnoreCase(editText.getText().toString().trim())) {
                    correct++;
                    currentQuestionIndex++;
                    updateQuestion(currentQuestionIndex);
                }else {
                    wrong++;
                    currentQuestionIndex++;
                    updateQuestion(currentQuestionIndex);
                }
            }
        });

        updateQuestion(currentQuestionIndex);


    }

    private void updateQuestion(int index) {
        if(currentQuestionIndex==questions.size()){
            showResultDialog();
        }else if(wrong==5){
            showResultDialog();
        }
        else {
            questionText.setText(questions.get(index).getQuestion());
            questionNumberText.setText(String.valueOf(currentQuestionIndex+1));

            editText.setText("");
        }
    }

    private void showResultDialog() {

        submitBtn.setEnabled(false);

        String text;
        if (correct>=5){
            text="You Are Pass";
        }else {
            text="You Loose";
        }

        mp.start();
        new AlertDialog.Builder(FinishLyricsActivity.this,R.style.AlertDialogTheme)
                .setTitle("Results:")
                .setMessage(
                        "Total Questions: "+questions.size()
                                +"\nCorrect Answers: "+correct+
                                "\nWrong Answers: "+wrong+
                                "\nResult: "+text
                )

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        ParcelabeModelLirics parcelabeModel=new ParcelabeModelLirics(
                questionNumberText.getText().toString()
                ,questionText.getText().toString(),
                correct,
                wrong,
                currentQuestionIndex
        );

        outState.putParcelable("parcelable",parcelabeModel);




    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ParcelabeModelLirics parcelabeModel=savedInstanceState.getParcelable("parcelable");


        questionText.setText(parcelabeModel.question);
        questionNumberText.setText(parcelabeModel.qno);
        correct=parcelabeModel.correct;
        wrong=parcelabeModel.wrong;
        currentQuestionIndex=parcelabeModel.qnomber;


    }


    private void createQuestions() {

        LiricsQuestion question=new LiricsQuestion(
                "Oops, I dit it again I played with your heart got lost in the....",
                "game"
        );
        LiricsQuestion question1=new LiricsQuestion(
                "Hello from the other side I must've called a thousand times To tell you ......",
                "I'm sorry"
        );
        LiricsQuestion question2=new LiricsQuestion(
                "You know you love me, I know you care just shout whenever and i'll be there you are my love, you are my heart And we will never, ever, ever....",
                "be apart"
        );
        LiricsQuestion question3=new LiricsQuestion(
                "....... when you call me senorita I wish I could pretend I didn't need ya",
                "I love it"
        );
        LiricsQuestion question4=new LiricsQuestion(
                "I'm beautiful in my way 'cause God makes no mistakes I'm on the right track, baby, I was born............",
                "this way"
        );
        LiricsQuestion question5=new LiricsQuestion(
                "And high up above, or down below When you're too in love to ...........",
                "to let it go"
        );
        LiricsQuestion question6=new LiricsQuestion(
                "Spend more time with my friends I ain't worried 'brout nothin' Plus, I met..............",
                "someone else"
        );
        LiricsQuestion question7=new LiricsQuestion(
                "And guess what? I'm having more fun and now that we're done I'm gonna...........",
                "sho you tonight"
        );
        LiricsQuestion question8=new LiricsQuestion(
                "Standin' up, keep me on the rise Lost control of myself, I'm compromised You're incriminating..........",
                "no disguise"
        );
        LiricsQuestion question9=new LiricsQuestion(
                "O Canada! Our home and native land! True patriot love in all of us command with glowing hearts we see thee rise the True North strong and.....",
                "free"
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