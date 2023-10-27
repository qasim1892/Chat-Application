package com.example.har.finalproject;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RawRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends AppCompatActivity {

    Button chatbtn;
    Intent intent;
    ImageView left,right;

    TextView disease,detail;

    int num=0;

    String number = "0";

    private String[] title = {
            "Acute\nStress\nDisorder",
            "Agoraphobia",
            "Amnesia,\n Dissociative",
            "Anorexia\nNervosa",
            "Attention\nDeficit\nHyperactivity\nDisorder(ADHD)",
            "Bipolar\nDisorder",
            "Body\nDysmorphic\nDisorder",
            "Brief\nPsychotic\nDisorder",
            "Bulimia\nNervosa",
            "Conversion\nDisorder",
            "Cyclothymic\nDisorder",
            "Delusional\nDisorder",
            "Depersonalization\nDisorder",
            "Dissociative\nIdentity\nDisorder(DID)",
            "Dyspareunia",
            "Dysthymic\nDisorder",
            "Erectile\nDisorder\nMale",
            "Exhibitionism",
            "Fetishism",
            "Frotteurism",
            "Fugue,\nDissociative",
            "Gambling,\nPathological",
            "Gender\nIdentity\nDisorder",
            "Generalized\nAnxiety\nDisorder",
            "Hypoactive\nSexual\nDesire\nDisorder",
            "Hypochondriasis",
            "Impotence",
            "Intermittent\nExplosive\nDisorder",
            "Kleptomania",
            "Masochism,\nSexual",
            "Major\nDepressive\nDisorder",
            "Obsessive\nCompulsive\nDisorder",
            "Orgasmic\nDisorder,\nFemale",
            "Orgasmic\nDisorder,\nMale",
            "Pain\nDisorder",
            "Panic\nDisorder",
            "Pedophilia",
            "Phobias",
            "Posttraumatic\nStress\nDisorder",
            "Premature\nEjaculation",
            "Pyromania",
            "Sadism,\nSexual",
            "Schizophrenia",
            "Schizoaffective\nDisorder",
            "Schizophreniform",
            "Sexual\nArousal\nDisorder,\nFemale",
            "Sexual\nAversion\nDisorder",
            "Shared\nPsychotic\nDisorder",
            "Somatization\nDisorder",
            "Substance\nAbuse",
            "Substance\nDependence",
            "Transvestic\nFetishism",
            "Trichotillomania",
            "Vaginismus",
            "Voyeurism"
            };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatbtn = (Button) findViewById(R.id.chat_btn);

        disease = (TextView) findViewById(R.id.disease);
        detail = (TextView) findViewById(R.id.detail);

        left =(ImageView) findViewById(R.id.left);
        right =(ImageView) findViewById(R.id.right);

        disease.setText(""+title[num]);
        detail.setText(""+getResources().getStringArray(R.array.Details)[0]);


        chatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("number", number);
                startActivity(intent);
            }
        });


        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (num==0){
                    num=53;

                    number = String.valueOf(num);
                }
                else {
                    num--;
                    number = String.valueOf(num);
                }
                slideLeftToRight(v);

            }
        });


        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num==53){
                    num=0;
                    number = String.valueOf(num);
                }
                else {
                    num++;
                    number = String.valueOf(num);
                }

                slideRighttoLeft(v);
            }
        });

    }

    public void slideLeftToRight(View v) {
        disease.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.out_to_left));

        detail.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                disease.setText(""+title[num]);
                detail.setText(""+getResources().getStringArray(R.array.Details)[num]);
                detail.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
                disease.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.in_from_right));
            }
        }, 1000);

    }

    public void slideRighttoLeft(View v) {
        disease.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.out_to_right));

        detail.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                disease.setText(""+title[num]);
                detail.setText(""+getResources().getStringArray(R.array.Details)[num]);
                detail.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in));
                disease.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.in_from_left));
            }
        }, 1000);

    }

}
