package com.example.henri.crud_imc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaVisivel = (ListView) findViewById(R.id.listaPessoas);
        registerForContextMenu(listaVisivel);

        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ActivityCadastro.class);
                startActivity(i);
            }
        });

        listaVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);

                Intent i = new Intent(MainActivity.this, ActivityCadastro.class);
                i.putExtra("pessoa-enviada", pessoaEnviada);
                startActivity(i);
            }
        });

        listaVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                pessoa = arrayAdapterPessoa.getItem(position);
                return false;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Deletar Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                long retornoDB;
                pessoaDao = new PessoaDao(MainActivity.this);
                retornoDB = pessoaDao.excluirPessoa(pessoa);
                pessoaDao.close();

                if(retornoDB == -1){
                    alert("Erro de Exclusão");
                }else{
                    alert("Registro Excluido com Sucesso");
                }
                populaLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public boolean onTouchEvent(MotionEvent touchevent){
        switch (touchevent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                if (x1 < x2){
                    Intent i = new Intent(MainActivity.this, ActivityCadastro.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
