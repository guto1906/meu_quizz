package br.com.fatecpg.myquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    ArrayList<String> ListHistory = new ArrayList<String>();
    ArrayAdapter<String> AdapterHistory = null;
    QuizzSQLiteHelper dbHelper = null;
    SQLiteDatabase db = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try{

            dbHelper = new QuizzSQLiteHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();

            //insere no cursor o retorno do cmdo sql
            Cursor cursor = db.rawQuery("SELECT * FROM RESULTADOS", null);

            //move o cursor para o inicio
            cursor.moveToFirst();

        /*percorre o cursor inserindo campo a primeira posicao da linha no idperguntasAdapter
        e a segunda posicao da linha no perguntasAdapter*/
            while (!cursor.isAfterLast()){
                String datas = cursor.getString(1);
                String percent = cursor.getString(2);
                ListHistory.add(datas+" - "+percent+"%");
                cursor.moveToNext();
            }
            //fecha o cursor criado
            cursor.close();
            db.close();
            dbHelper.close();
        }catch (Exception ex){
            new AlertDialog.Builder(this).setMessage(ex.getMessage()).setPositiveButton("OK", null).show();}


        AdapterHistory = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, ListHistory);
        ListView insereHistorico = (ListView) findViewById(R.id.ListViewHistory);
        insereHistorico.setAdapter(AdapterHistory);



    }
}
