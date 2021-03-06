package br.com.fatecpg.myquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionImparActivity extends AppCompatActivity {
    QuizzSQLiteHelper dbHelper = null;
    SQLiteDatabase db = null;
    ArrayList<String> ListRespostas= new ArrayList<String>();
    ArrayList<Integer> ListidFinal = new ArrayList<Integer>();
    String pergunta;
    String certa;
    Integer nr;
    Integer pontos = 0;
    Integer Total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_impar);

        Intent playimpar = getIntent();
        nr = playimpar.getIntExtra("n",0);
        Total = playimpar.getIntExtra("T", 0);
        pontos = playimpar.getIntExtra("pt", 0);
        ListidFinal = playimpar.getIntegerArrayListExtra("ListaId");
        dbHelper = new QuizzSQLiteHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PERGUNTAS WHERE ID = "+ListidFinal.get(0)+";", null);
        cursor.moveToFirst();
        pergunta = cursor.getString(1);
        certa = cursor.getString(2);
        ListRespostas.add(cursor.getString(2));
        ListRespostas.add(cursor.getString(3));
        ListRespostas.add(cursor.getString(4));
        ListRespostas.add(cursor.getString(5));
        ListRespostas.add(cursor.getString(6));

        //fecha o cursor criado
        cursor.close();
        //fecha a base de dados
        db.close();
        //fecha o banco
        dbHelper.close();
        //Embaralha a lista
        Collections.shuffle(ListRespostas);
        TextView questao = (TextView) findViewById(R.id.TVpergImpar);
        questao.setText(pergunta);

        ListView LVRespostas = (ListView) findViewById(R.id.lisRespImpar);
        ArrayAdapter<String> RespostasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, ListRespostas);
        LVRespostas.setAdapter(RespostasAdapter);

        //new AlertDialog.Builder(this).setMessage("Pontos "+pontos).setPositiveButton("Ok", null).show();
        //new AlertDialog.Builder(this).setMessage("Qestoes "+nr).setPositiveButton("Ok", null).show();

        ///////////////////////////////////////////////////

        //metodo para executar ao clicar em um item da lista
        LVRespostas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pegar o item da lista de chaves pela posicao da pergunta
                String RespostaSelecionada = ListRespostas.get(position);
                if(RespostaSelecionada.equals(certa)){
                    pontos = pontos + 1;
                }
                //reduz o numero de questoes
                nr = nr -1;
                Total = Total + 1;
                if(nr.equals(0)){
                    Intent resultado = new Intent(QuestionImparActivity.this,ResultActivity.class);
                    resultado.putExtra("pt", pontos);
                    resultado.putExtra("T", Total);
                    startActivity(resultado);
                    finish();

                } else {
                //excluir o primeiro item da lista de chaves
                ListidFinal.remove(0);
                Intent playpar = new Intent(QuestionImparActivity.this,QuestionParActivity.class);
                playpar.putExtra("n", nr);
                playpar.putExtra("pt", pontos);
                playpar.putExtra("T", Total);
                playpar.putIntegerArrayListExtra("ListaId", ListidFinal);
                startActivity(playpar);
                finish();}


            }// fim do onItemClick
        });


        //////////////////////////////////////////////////////



    }
}
