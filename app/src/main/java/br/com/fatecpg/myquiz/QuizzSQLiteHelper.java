package br.com.fatecpg.myquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by guto on 05/06/16.
 */
public class QuizzSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quizz.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE = "create table PERGUNTAS("
            +"ID INTEGER primary key autoincrement"
            +", PERGUNTA VARCHAR NOT NULL"
            +", RESPOSTA VARCHAR"
            +", ALTER1 VARCHAR"
            +", ALTER2 VARCHAR"
            +", ALTER3 VARCHAR"
            +", ALTER4 VARCHAR"
            +");";
    private static final String DATABASE_CREATE2 = "create table RESULTADOS(ID INTEGER primary key autoincrement, DATA VARCHAR, PERCENTUAL VARCHAR);";
    public QuizzSQLiteHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }



    //metodo que cria as tabelas PERGUNTAS E RESULTADOS na base de dados quizz.db
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE2);
    }

    //metodo que faz upgrade da base de dados quizz.db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS PERGUNTAS");
        db.execSQL("DROP TABLE IF EXISTS RESULTADOS");
        onCreate(db);
    }


}
