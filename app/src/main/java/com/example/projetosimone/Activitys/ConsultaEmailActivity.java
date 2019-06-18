package com.example.projetosimone.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetosimone.Async.AsyncListarPorEmail;
import com.example.projetosimone.R;
import com.example.projetosimone.adapter.AlunosAdapter;
import com.example.projetosimone.modelo.Aluno;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ConsultaEmailActivity extends AppCompatActivity {

    private Button bt_consulta_email;
    private EditText et_email;
    private String email;
    private String asyncRetorno;
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_email);

        et_email = findViewById(R.id.et_consulta_email);
        bt_consulta_email = findViewById(R.id.btnConsultarEmail);

        listaAlunos = findViewById(R.id.lv_consultaEmail);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ConsultaEmailActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);
            }
        });


        registerForContextMenu(listaAlunos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_email = findViewById(R.id.et_consulta_email);
        bt_consulta_email = findViewById(R.id.btnConsultarEmail);

        listaAlunos = findViewById(R.id.lv_consultaEmail);
        bt_consulta_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Aluno> listaDeAlunos = new ArrayList();
                email = et_email.getText().toString();

                AsyncListarPorEmail AListarPorEmail = new AsyncListarPorEmail(); //fazer no onClick do consultar
                try {
                    asyncRetorno = AListarPorEmail.execute(email).get();
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
