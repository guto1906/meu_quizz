package br.com.fatecpg.myquiz;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class InsertAnswerActivity extends AppCompatActivity {
    QuizzSQLiteHelper dbHelper = null;
    SQLiteDatabase db = null;
    Integer cod;
    String perg;
    String resp;
    String alter1;
    String alter2;
    String alter3;
    String alter4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_answer);

        Intent insereresp = getIntent();
        cod = insereresp.getIntExtra("cod", 0);
        perg = insereresp.getStringExtra("pergunta");
        resp = insereresp.getStringExtra("resposta");
        alter1 = insereresp.getStringExtra("alter1");
        alter2 = insereresp.getStringExtra("alter2");
        alter3 = insereresp.getStringExtra("alter3");
        alter4 = insereresp.getStringExtra("alter4");

        EditText pergunta = (EditText) findViewById(R.id.EditQuestion);
        pergunta.setText(perg);

        EditText resposta = (EditText) findViewById(R.id.EditTextAnswer);
        resposta.setText(resp);

        EditText a = (EditText) findViewById(R.id.EditTextAltern1);
        a.setText(alter1);

        EditText b = (EditText) findViewById(R.id.EditTextAltern2);
        b.setText(alter2);

        EditText c = (EditText) findViewById(R.id.EditTextAltern3);
        c.setText(alter3);

        EditText d = (EditText) findViewById(R.id.EditTextAltern4);
        d.setText(alter4);
    }

    public void gravar(View view){
        try{
            //cria a intente insert question
            Intent insertquestion = new Intent(InsertAnswerActivity.this, InsertQuestionActivity.class);

            //pegar os valore dos campos
            EditText pergunta = (EditText) findViewById(R.id.EditQuestion);
            String p = pergunta.getText().toString();

            EditText resposta = (EditText) findViewById(R.id.EditTextAnswer);
            String r = resposta.getText().toString();

            EditText a = (EditText) findViewById(R.id.EditTextAltern1);
            String r1 = a.getText().toString();

            EditText b = (EditText) findViewById(R.id.EditTextAltern2);
            String r2 = b.getText().toString();

            EditText c = (EditText) findViewById(R.id.EditTextAltern3);
            String r3 = c.getText().toString();

            EditText d = (EditText) findViewById(R.id.EditTextAltern4);
            String r4 = d.getText().toString();





            dbHelper = new QuizzSQLiteHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();
            db.execSQL("UPDATE PERGUNTAS SET"
                    +" PERGUNTA = '"+p+"'"
                    +", RESPOSTA = '"+r+"'"
                    +", ALTER1 = '"+r1+"'"
                    +", ALTER2 = '"+r2+"'"
                    +", ALTER3 = '"+r3+"'"
                    +", ALTER4 = '"+r4+"'"
                    +" WHERE ID = "+cod+";");
            db.close();
            dbHelper.close();
            startActivity(insertquestion);
            finish();

        } catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();
        }//fecha o catch
    }//fecha o gravar


    //função de exclusao de linha
    public void excluir(View view){
        try{
            Intent insertquestion = new Intent(InsertAnswerActivity.this, InsertQuestionActivity.class);
            dbHelper = new QuizzSQLiteHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();
            db.execSQL("DELETE FROM PERGUNTAS WHERE ID = "+cod+";");
            db.close();
            dbHelper.close();
            startActivity(insertquestion);
            finish();

        } catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();
        }//fecha o catch
    }//fecha exclusao

    //retorna a actitvity anterior for pressionado o botao voltar
    @Override
    public void onBackPressed() {
        Intent insertquestion = new Intent(InsertAnswerActivity.this, InsertQuestionActivity.class);
        startActivity(insertquestion);
        finish();
    }
}
