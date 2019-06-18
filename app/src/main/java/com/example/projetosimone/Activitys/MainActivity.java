package com.example.projetosimone.Activitys;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.projetosimone.Async.AsyncExcluir;
import com.example.projetosimone.Async.AsyncListar;
import com.example.projetosimone.R;
import com.example.projetosimone.adapter.AlunosAdapter;
import com.example.projetosimone.modelo.Aluno;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;


import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAlunos = findViewById(R.id.lista_alunos);

        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intentVaiProFormulario = new Intent(MainActivity.this, FormularioActivity.class);
                intentVaiProFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiProFormulario);
            }
        });

        Button novoAluno = (Button) findViewById(R.id.main_nvAluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiProFormulario = new Intent(MainActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormulario);
            }
        });

        registerForContextMenu(listaAlunos);

    }

    @Override
    protected void onResume() {

        super.onResume();
        ArrayList<Aluno> listaDeAlunos = new ArrayList();
        String str = "";

        try {
                AsyncListar AListar = new AsyncListar();
                str = AListar.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
        }

            JsonArray jsonArray = new JsonParser().parse(str).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                Aluno aluno = new Gson().fromJson(jsonArray.get(i), Aluno.class);
                listaDeAlunos.add(aluno);
            }
            AlunosAdapter alunos = new AlunosAdapter(listaAlunos.getContext(), listaDeAlunos);
            listaAlunos.setAdapter(alunos);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.popup_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.porNome:
                intent = new Intent(MainActivity.this, ConsultaNomeActivity.class);
                startActivity(intent);
                break;
            case R.id.porRa:
                intent = new Intent(MainActivity.this, ConsultaRaActivity.class);
                startActivity(intent);
                break;
            case R.id.porEmail:
                intent = new Intent(MainActivity.this, ConsultaEmailActivity.class);
                startActivity(intent);
                break;
            case R.id.excluir:
                intent = new Intent(MainActivity.this, activity_excluir.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.
//                    Intent intent;
//                    AsyncExcluir exclui = new AsyncExcluir();
//                    String raExcluido = aluno.getRA().toString();
//                    try {
//                        exclui.execute(raExcluido);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    intent = new Intent(MainActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
               return false;
            }
        });
    }


}
