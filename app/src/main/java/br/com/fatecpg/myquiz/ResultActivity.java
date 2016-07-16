package br.com.fatecpg.myquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
    Integer pontos = 0;
    Integer totalquest = 0;
    QuizzSQLiteHelper dbHelper = null;
    SQLiteDatabase db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent resultado = getIntent();
        pontos = resultado.getIntExtra("pt", 0);
        totalquest = resultado.getIntExtra("T", 0);
        float a = Float.parseFloat(pontos.toString());
        float b = Float.parseFloat(totalquest.toString());
        float r = (a/b)*100;
        Integer percent = Math.round(r);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String data =  dateFormat.format(date);

        try{

        dbHelper = new QuizzSQLiteHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        db.execSQL("INSERT INTO RESULTADOS (DATA, PERCENTUAL) VALUES ("
                    +"'"+data+"'"
                    +", '"+percent.toString()+"');");
        db.close();
        dbHelper.close();
        }catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();}



        TextView pontuacao = (TextView)findViewById(R.id.TextViewNrHints);
        TextView percentual = (TextView)findViewById(R.id.TextViewPercHints);
        TextView dt = (TextView)findViewById(R.id.TextViewDate);
        pontuacao.setText(pontos.toString());
        percentual.setText(percent.toString()+"%");
        dt.setText(data);

        //finish();
    }
}
