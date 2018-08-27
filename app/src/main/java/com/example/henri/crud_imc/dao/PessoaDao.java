package com.example.henri.crud_imc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.henri.crud_imc.modelo.Pessoa;

import java.util.ArrayList;

/**
 * Created by henri on 26/08/2018.
 */

public class PessoaDao extends SQLiteOpenHelper{

    private static final String NOME_BANCO = "DBPessoa.db";
    private static final int VERSION = 1;
    private static final String TABELA = "pessoa";
    private static final String ID = "id";
    private static final String NOME = "nome";
    private static final String DATANASCIMENTO = "dataNascimento";
    private static final String PESO = "peso";
    private static final String ALTURA = "altura";
    private static final String IMC = "imc";


    public PessoaDao(Context context) {
        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE "+TABELA+" ( " +
                " "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " "+NOME+" TEXT, " +
                " "+DATANASCIMENTO+" NUMERIC, " +
                " "+PESO+" INTEGER, " +
                " "+ALTURA+" REAL, "+
                " "+IMC+" REAL );";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IS EXISTS "+TABELA;
        db.execSQL(sql);
        onCreate(db);

    }

    public long salvarPessoa(Pessoa p){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME, p.getNome());
        values.put(DATANASCIMENTO, p.getDataNascimento());
        values.put(PESO, p.getPeso());
        values.put(ALTURA, p.getAltura());
        values.put(IMC, p.getImc());


        retornoDB = getWritableDatabase().insert(TABELA, null, values);

        return retornoDB;
    }

    public ArrayList<Pessoa> selectAllPessoa(){
        String[] coluns = {ID, NOME, DATANASCIMENTO, PESO, ALTURA, IMC};

        Cursor cursor = getWritableDatabase().query(TABELA, coluns, null, null, null, null, "nome", null);

        ArrayList<Pessoa> listPessoa = new ArrayList<Pessoa>();

        while (cursor.moveToNext()){
            Pessoa p = new Pessoa();

            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setDataNascimento(cursor.getString(2));
            p.setAltura(cursor.getDouble(3));
            p.setPeso(cursor.getInt(4));
            p.setImc(cursor.getDouble(5));

            listPessoa.add(p);
        }

        return listPessoa;
    }
}
