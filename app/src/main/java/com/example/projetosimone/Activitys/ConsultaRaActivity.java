package com.example.projetosimone.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.projetosimone.Async.AsyncListarPorRA;
import com.example.projetosimone.R;
import com.example.projetosimone.adapter.AlunosAdapter;
import com.example.projetosimone.modelo.Aluno;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ConsultaRaActivity extends AppCompatActivity {

    private Button bt_consulta_ra;
    private EditText et_ra;
    private String ra;
    private String asyncRetorno;
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_ra);

        et_ra = findViewById(R.id.et_consulta_ra);
        bt_consulta_ra = findViewById(R.id.btnConsultarRA);

        listaAlunos = findViewById(R.id.lv_consultaRA);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(ConsultaRaActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);
            }
        });


        registerForContextMenu(listaAlunos);

    }

    @Override
    protected void onResume() {
        super.onResume();
        et_ra = findViewById(R.id.et_consulta_ra);
        bt_consulta_ra = findViewById(R.id.btnConsultarRA);

        listaAlunos = findViewById(R.id.lv_consultaRA);
        bt_consulta_ra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Aluno> listaDeAlunos = new ArrayList();
                ra = et_ra.getText().toString();

                AsyncListarPorRA AListarPorRA = new AsyncListarPorRA(); //fazer no onClick do consultar
                try {
                    asyncRetorno = AListarPorRA.execute(ra).get();
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
