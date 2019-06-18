package com.example.projetosimone.Async;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncAdicionar extends AsyncTask<String, Void, Void> {
    private Void retorno;
    @Override
    protected Void doInBackground(String... strings) {
        try{
            URL objURL = new URL("http://177.220.18.47:8080/APIAndroid/webresources/generic/incluirAluno/");
            HttpURLConnection con = (HttpURLConnection) objURL.openConnection();

            con.setDoOutput(true);

            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            //Formata em json o item da lista a ser inserido com POST
            String output = strings[0];



            //Pega a conexão aberta em con (getOutputStream()) e faz OutputStream, ou seja, faz o fluxo de dados do cliente para o servidor
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

        }catch (Exception e){
          return retorno;
        }
        return retorno;
    }
}
