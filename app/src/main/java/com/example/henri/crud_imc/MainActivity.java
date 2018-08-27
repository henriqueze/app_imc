package com.example.henri.crud_imc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.henri.crud_imc.dao.PessoaDao;
import com.example.henri.crud_imc.modelo.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listaVisivel;
    Button btnNovoCadastro;
    Pessoa pessoa;
    PessoaDao pessoaDao;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaVisivel = (ListView) findViewById(R.id.listaPessoas);
        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityCadastro.class);
                startActivity(i);
            }
        });
    }

    public void populaLista(){
        pessoaDao = new PessoaDao(MainActivity.this);

        arrayListPessoa = pessoaDao.selectAllPessoa();
        pessoaDao.close();

        if (listaVisivel != null){
            arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this,
                    android.R.layout.simple_list_item_1, arrayListPessoa);
            listaVisivel.setAdapter(arrayAdapterPessoa);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populaLista();
    }
}
