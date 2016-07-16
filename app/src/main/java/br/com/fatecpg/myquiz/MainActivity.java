package br.com.fatecpg.myquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void activitynrquestion(View view){
        Intent nrquestion = new Intent(this,NrQuestionActivity.class);
        startActivity(nrquestion);
    }
    public void activityinsertquestion(View view){
        Intent insertquestion = new Intent(this,InsertQuestionActivity.class);
        startActivity(insertquestion);
    }
    public void activityhistory(View view){
        Intent history = new Intent(this,HistoryActivity.class);
        startActivity(history);

    }



}































