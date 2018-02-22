package br.com.studiotrek.faculdadeimpacta.presentation.semester_grades

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Kleber on 21/02/2018.
 */
class SemesterModel (@SerializedName("nomeAluno") @Expose var nomeAluno: String,
                     @SerializedName("rmAluno") @Expose var rmAluno: String,
                     @SerializedName("curso") @Expose var curso: String,
                     @SerializedName("semestres") @Expose var semestres: List<SemesterDetailModel>)