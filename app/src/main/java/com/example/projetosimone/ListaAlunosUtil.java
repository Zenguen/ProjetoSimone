package com.example.projetosimone;

import com.example.projetosimone.modelo.Aluno;
import com.google.gson.Gson;

public class ListaAlunosUtil {

    public static Aluno getAlunoFromJson(String json){
        Aluno aluno = new Aluno();
        Gson gson = new Gson();

        try {
            aluno = gson.fromJson(json, Aluno.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return aluno;
    }
}
