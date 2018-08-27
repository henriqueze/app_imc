package com.example.henri.crud_imc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henri.crud_imc.dao.PessoaDao;
import com.example.henri.crud_imc.modelo.Pessoa;

import java.text.DecimalFormat;

public class ActivityCadastro extends AppCompatActivity {
    EditText edtNome, edtDataNascimento, edtPeso, edtAltura;
    TextView resultadoImc, descricaoImc;
    Button btnVariavel;

    Pessoa pessoa, altpessoa;
    PessoaDao pessoaDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        final float[] imc = new float[1];

        Intent i = getIntent();
        altpessoa = (Pessoa) i.getSerializableExtra("pessoa-enviada");
        pessoa = new Pessoa();
        pessoaDao = new PessoaDao(ActivityCadastro.this);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtDataNascimento = (EditText) findViewById(R.id.edtDataNascimento);
        edtPeso = (EditText) findViewById(R.id.edtPeso);
        edtAltura = (EditText) findViewById(R.id.edtAltura);
        resultadoImc = (TextView) findViewById(R.id.ResultadoImc);
        descricaoImc = (TextView) findViewById(R.id.DescricaoImc);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if (altpessoa != null){
            btnVariavel.setText("Alterar");
        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int peso = Integer.parseInt(edtPeso.getText().toString());
                float altura = Float.parseFloat(edtAltura.getText().toString());

                 imc[0] = peso / (altura * altura);
                if(imc[0] < 19){
                    //abaixo
                    resultadoImc.setText(imc[0]+"");
                    descricaoImc.setText("Abaixo do Peso");

                }
                else if(imc[0] < 25){
                    //ok
                    resultadoImc.setText(imc[0]+"");
                    descricaoImc.setText("Peso Adequado");
                }
                else if( imc[0] < 30){
                    //sobrepeso
                    resultadoImc.setText(imc[0]+"");
                    descricaoImc.setText("Sobrepeso");
                }else {
                    //obesidade
                    resultadoImc.setText(imc[0]+"");
                    descricaoImc.setText("Obesidade");
                }

                pessoa.setNome(edtNome.getText().toString());
                pessoa.setDataNascimento(edtDataNascimento.getText().toString());
                pessoa.setAltura(Float.parseFloat(edtAltura.getText().toString()));
                pessoa.setPeso(Integer.parseInt(edtPeso.getText().toString()));
                pessoa.setImc(imc[0]);

                if(btnVariavel.getText().toString().equals("Salvar")){
                    retornoDB = pessoaDao.salvarPessoa(pessoa);
                    if(retornoDB == -1){
                        alert("Erro ao Cadastrar");
                    }else{
                        alert("Cadastro Realizado com Sucesso");
                    }
                }else{

                }

                finish();
            }
        });
    }

    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}
