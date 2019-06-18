package com.example.projetosimone.Activitys;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projetosimone.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class activity_excluir extends AppCompatActivity {
    private EditText ra;
    private Button btnExcluir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excluir);

        ra = (EditText) findViewById(R.id.et_exclui_ra);
        btnExcluir = (Button) findViewById(R.id.btnExclui);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent volta = new Intent(activity_excluir.this, MainActivity.class);
                AsyncExcluir AExcluir = new AsyncExcluir();
                AExcluir.execute(ra.getText().toString());
                startActivity(volta);
            }
        });
    }public class AsyncExcluir extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {

            try {
                URL objURL = new URL("http://177.220.18.47:8080/APIAndroid/webresources/generic/excluirAluno/" + strings[0]);
                HttpURLConnection con = (HttpURLConnection) objURL.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("Content-type", "application/json");
                con.setRequestProperty("Accept", "application/json");
                //con.setDoOutput(true);
                con.connect(); //estabelece a conexao

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));


                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();
                con.disconnect();


                return response.toString();
            }catch (Exception e){
                return "";
            }
        }
    }


}
