package com.example.projetosimone.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetosimone.Async.AsyncListarPorNome;
import com.example.projetosimone.R;
import com.example.projetosimone.adapter.AlunosAdapter;
import com.example.projetosimone.modelo.Aluno;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ConsultaNomeActivity extends AppCompatActivity {

    private Button bt_consulta_nome;
    private EditText et_nome;
    private String nome;
    private String asyncRetorno;
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_nome);

        et_nome = findViewById(R.id.et_consulta_nome);
        bt_consulta_nome = findViewById(R.id.btnConsultarNome);

        listaAlunos = findViewById(R.id.lv_consultaNome);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ConsultaNomeActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);
            }
        });


        registerForContextMenu(listaAlunos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_nome = findViewById(R.id.et_consulta_nome);
        bt_consulta_nome = findViewById(R.id.btnConsultarNome);

        listaAlunos = findViewById(R.id.lv_consultaNome);
        bt_consulta_nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Aluno> listaDeAlunos = new ArrayList();
                nome = et_nome.getText().toString();

                AsyncListarPorNome AListarPorNome = new AsyncListarPorNome(); //fazer no onClick do consultar
                try {
                    asyncRetorno = AListarPorNome.execute(nome).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                JsonParser parser = new JsonParser();
                JsonObject obj = (JsonObject) parser.parse(asyncRetorno);
                Aluno aluno = new Gson().fromJson(obj, Aluno.class);
                listaDeAlunos.add(aluno);
                AlunosAdapter alunos = new AlunosAdapter(listaAlunos.getContext(), listaDeAlunos);
                listaAlunos.setAdapter(alunos);
            }
        });
    }
}
