package com.example.projetosimone.Async;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncListar extends AsyncTask <Void,Void,String> {

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL objURL = new URL("http://177.220.18.47:8080/APIAndroid/webresources/generic/consultar");
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
