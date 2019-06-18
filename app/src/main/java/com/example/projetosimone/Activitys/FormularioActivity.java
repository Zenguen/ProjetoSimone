package com.example.projetosimone.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.projetosimone.Async.AsyncAdicionar;
import com.example.projetosimone.Async.AsyncAlterar;
import com.example.projetosimone.R;
import com.example.projetosimone.helper.FormularioHelper;
import com.example.projetosimone.modelo.Aluno;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.ExecutionException;

public class FormularioActivity extends AppCompatActivity {
    private FormularioHelper helper;
    private Aluno incluido;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null) {
            helper.preencheFormulario(aluno);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_form_ok:
                helper = new FormularioHelper(FormularioActivity.this);
                    incluido = helper.pegaAluno();
                    AsyncAdicionar inclui = new AsyncAdicionar();
                    String strIncluido = new Gson().toJson(incluido, Aluno.class);
                    try {
                        inclui.execute(strIncluido).get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                intent = new Intent(FormularioActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
