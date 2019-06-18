package com.example.projetosimone.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetosimone.Async.AsyncAlterar;
import com.example.projetosimone.R;
import com.example.projetosimone.helper.FormularioHelper;
import com.example.projetosimone.modelo.Aluno;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class activity_alterar extends AppCompatActivity {
    private EditText ra;
    private EditText nome;
    private EditText email;
    private Button   btnAlterar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        ra         = (EditText) findViewById(R.id.form_ra);
        nome       = (EditText) findViewById(R.id.form_nome);
        email      = (EditText) findViewById(R.id.form_email);
        btnAlterar = (Button) findViewById(R.id.btnAlterar);

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Aluno aluno = new Aluno();
                aluno.setEmailAluno(email.getText().toString());
                aluno.setNome(nome.getText().toString());
                aluno.setRA(Long.parseLong(ra.getText().toString()));

                String strAltera = new Gson().toJson(aluno,Aluno.class);
                Intent volta = new Intent(activity_alterar.this, MainActivity.class);
                AsyncAlterar alterar = new AsyncAlterar();
                alterar.execute(strAltera);
                startActivity(volta);
            }
        });
    }
    public class AsyncAlterar extends AsyncTask<String,Void,Void> {
        private Void retorno;
        @Override
        protected Void doInBackground(String... strings) {
            try{
                URL objURL = new URL("http://177.220.18.47:8080/APIAndroid/webresources/generic/alterarAluno/");
                HttpURLConnection con = (HttpURLConnection) objURL.openConnection();
                con.setDoOutput(true);

                con.setRequestMethod("PUT");
                con.setRequestProperty("Content-Type", "application/json");

                //Formata em json o item da lista a ser inserido com POST
                String output = strings[0];


                //Pega a conexão aberta em con (getOutputStream()) e faz OutputStream, ouseja, faz o fluxo de dados do cliente para o servidor
                OutputStream os = con.getOutputStream();

                //Escreve o output tranformado em Bytes
                os.write(output.getBytes());


                int responseCode = con.getResponseCode();
                //Se retornar 200 significa que deu certo

                System.out.println("Response Code: "+ responseCode);

                //Armazena o retorno do método POST do servidor
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response = new StringBuffer();
                String inputLine;

                while((inputLine = br.readLine())!=null){
                    response.append(inputLine);

                }

                br.close();
                con.disconnect();
                return retorno;
            }
            catch (Exception e){
                return retorno;
            }
        }
    }
}
