package br.com.fatecpg.myquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NrQuestionActivity extends AppCompatActivity {
    QuizzSQLiteHelper dbHelper = null;
    SQLiteDatabase db = null;
    //ArrayAdapter<Integer> idperguntasAdapter = null;
    ArrayList<Integer> ListidPerg = new ArrayList<Integer>();
    ArrayList<Integer> ListidFinal = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nr_question);
        } //fim do oncreate

    //metodo do botão inciar

    public void questionactivity(View view){
        //pega o valor do nr de questoes
        EditText nrQuestions = (EditText) findViewById(R.id.EditTextNrQuestion);
        Integer n = Integer.parseInt(nrQuestions.getText().toString());

        dbHelper = new QuizzSQLiteHelper(getApplicationContext());
        //pega a base de dados para leitura
        db = dbHelper.getReadableDatabase();
        //insere no cursor o retorno do cmdo sql
        Cursor cursor = db.rawQuery("SELECT * FROM PERGUNTAS", null);
        //move o cursor para o inicio
        cursor.moveToFirst();
        /*percorre o cursor inserindo campo a primeira posicao da linha no idperguntasAdapter
        e a segunda posicao da linha no perguntasAdapter*/

        while (!cursor.isAfterLast()){
             int chave = cursor.getInt(0);
             ListidPerg.add(chave);
             cursor.moveToNext();
        }
        //fecha o cursor criado
        cursor.close();
        //fecha a base de dados
        db.close();
        //fecha o banco
        dbHelper.close();

        //Embaralha a lista
        Collections.shuffle(ListidPerg);
        for (int i = 0; i < n; i++){
            ListidFinal.add(ListidPerg.get(i));
        }
        Integer pt = 0;
        Integer T = 0;

        //a intente e passa a list de idperguntas e nr de questoes para classe QuestionImparActivity
        Intent playimpar = new Intent(this,QuestionImparActivity.class);
        playimpar.putExtra("n", n);
        playimpar.putExtra("T", T);
        playimpar.putExtra("pt", pt);
        playimpar.putIntegerArrayListExtra("ListaId", ListidFinal);
        startActivity(playimpar);
        finish();


    }//fecha o botao iniciar
}
