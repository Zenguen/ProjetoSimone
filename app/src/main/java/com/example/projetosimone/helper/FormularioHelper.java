package com.example.projetosimone.helper;

import android.widget.EditText;

import com.example.projetosimone.Activitys.FormularioActivity;
import com.example.projetosimone.R;
import com.example.projetosimone.modelo.Aluno;

public class FormularioHelper {

    private final EditText campoNome;
    private final EditText campoEmail;
    private final EditText campoRa;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity) {
        campoNome = activity.findViewById(R.id.form_nome);
        campoEmail = activity.findViewById(R.id.form_email);
        campoRa = activity.findViewById(R.id.form_ra);
        aluno = new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEmailAluno(campoEmail.getText().toString());
        aluno.setRA(Long.parseLong(campoRa.getText().toString()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEmail.setText(aluno.getEmailAluno());
        campoRa.setText(aluno.getRA().toString());
        this.aluno = aluno;
    }
}
