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

import java.util.ArrayList;

public class InsertQuestionActivity extends AppCompatActivity {
    QuizzSQLiteHelper dbHelper = null;
    SQLiteDatabase db = null;
    ArrayAdapter<String> perguntasAdapter = null;
    ArrayAdapter<Integer> idperguntasAdapter = null;



    //Metodo para inserir as perguntas e as chaves em seus respectivos ArrayAdapters
    protected void atualizarLista(){
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
            String pergunta = cursor.getString(1);
            idperguntasAdapter.add(chave);
            perguntasAdapter.add(pergunta);
            cursor.moveToNext();
        }
        //fecha o cursor criado
        cursor.close();
        //fecha a base de dados
        db.close();
        //fecha o banco
        dbHelper.close();
    } //fim do metodo atualiza

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_question);

        //1-cria um array list de strings para armazenar as perguntas e um para armazenar as chaves
        final ArrayList<String> perguntas = new ArrayList<String>();
        final ArrayList<Integer> chaves = new ArrayList<Integer>();

        //2-cria um ArrayAdapter com a lista de perguntas e um com as chaves
        perguntasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, perguntas);
        idperguntasAdapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_expandable_list_item_1, chaves);

        //3-pega a listView da insertQuestion pelo id
        ListView ListViewPerguntas = (ListView) findViewById(R.id.ListViewQuestion);


        //insere na listView os ArrayAdapter das perguntas
        ListViewPerguntas.setAdapter(perguntasAdapter);


        try{
            //Cria uma base de dados como os dados de contexto da aplicacao usando
            //o metodo criado na classe QuizzSQLiteHelp
            dbHelper = new QuizzSQLiteHelper(getApplicationContext());

            //chama o metodo atualizar
            atualizarLista();

            //trata os erros
        } catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("Erro_FuncAtlz", null).show();
        }

        //metodo para executar ao clicar em um item da lista
        ListViewPerguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    //cria a intent da InsertAnswerActivity
                    Intent insereresp = new Intent(InsertQuestionActivity.this, InsertAnswerActivity.class);

                    //pegar o item da lista de chaves pela posicao da pergunta
                    int nr = chaves.get(position);
                    db = dbHelper.getReadableDatabase();
                    //coloca a linha no cursor de acordo com a chave primaria
                    Cursor dados = db.rawQuery("SELECT * FROM PERGUNTAS WHERE ID = " + nr + ";", null);
                    //move o cursor para a primeira linha
                    dados.moveToFirst();
                    //insere os dados das colunas nas variaveis
                    int cod = dados.getInt(0);
                    String pergunta = dados.getString(1);
                    String resposta = dados.getString(2);
                    String alter1 = dados.getString(3);
                    String alter2 = dados.getString(4);
                    String alter3 = dados.getString(5);
                    String alter4 = dados.getString(6);

                    //fecha cursor base de dados e o banco
                    dados.close();
                    db.close();
                    dbHelper.close();

                    //insere as variares para a InsertAnswerActivity
                    insereresp.putExtra("cod", cod);
                    insereresp.putExtra("pergunta", pergunta);
                    insereresp.putExtra("resposta", resposta);
                    insereresp.putExtra("alter1", alter1);
                    insereresp.putExtra("alter2", alter2);
                    insereresp.putExtra("alter3", alter3);
                    insereresp.putExtra("alter4", alter4);

                    //starta a InsertAnswerActivity
                    startActivity(insereresp);
                    finish();
                } catch (Exception ex){
                    new AlertDialog.Builder(InsertQuestionActivity.this).setMessage(ex.getMessage()).setPositiveButton("Erro_noClick", null).show();
                } //fim do try cacth
            }// fim do onItemClick
        });

    }

    //Metodo para inserir a pergunta e a chave na listview
    public void addClick(View view){

        //pega o campo EditTex da pagina
        EditText EditTextQuestion = (EditText) findViewById(R.id.EditTextQuestion);

        //insere o conteudo em uma string
        String pergunta = EditTextQuestion.getText().toString();

        if(!pergunta.equals("")) {
            try {

                //abre o banco e recupera a base de dados para escrita
                db = dbHelper.getWritableDatabase();

                //executa o comando sql para inserir a nova pergunta no banco
                db.execSQL("INSERT INTO PERGUNTAS(PERGUNTA) VALUES('" + pergunta + "')");

                //coloca as linhas do banco no cursor
                Cursor cod = db.rawQuery("SELECT * FROM PERGUNTAS", null);

                //vaia ate a ultima linha do cursor
                cod.moveToPosition((cod.getCount()) - 1);

                //pega o valor da chave primaria na 1 posicao da ultima linha do cursor
                int c = cod.getInt(0);

                //fecha cursor a base e o banco
                cod.close();
                db.close();
                dbHelper.close();

                //limpa o conteudo da edit text
                EditTextQuestion.setText("");

                //adiciona ao final da listview a nova pergunta e a chave
                perguntasAdapter.add(pergunta);
                idperguntasAdapter.add(c);

            } catch (Exception ex) {
                new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("Erro_nova_perg", null).show();
            }
        } else {
            new AlertDialog.Builder(this).setMessage("O campo não pode estar vazio").setPositiveButton("OK", null).show();
        } //final do if
    }

}
