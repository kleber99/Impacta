package br.com.studiotrek.faculdadeimpacta.domain.repository

import br.com.studiotrek.faculdadeimpacta.presentation.schedule.ScheduleResponse
import br.com.studiotrek.faculdadeimpacta.presentation.login.LoginResponse
import br.com.studiotrek.faculdadeimpacta.presentation.semester_grades.SemesterResponse
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

/**
 * Created by Wilder on 18/02/18.
 */

interface ImpactaApi {

    //TODO: create response class
    @GET("login/{ra}/{password}")
    fun login(@Path("ra") ra: String, @Path("password") password: String): Observable<Response<LoginResponse>>

    //TODO: Change jsonobject to appropriate classes
    @POST("horario")
    fun getClassesSchedule(@Body cookie: String): Observable<Response<ScheduleResponse>>

    //TODO: Change jsonobject to appropriate classes
    @POST("semestre-nota")
    fun getCurrentSemesterGrades(@Body cookie: String): Observable<Response<SemesterResponse>>

    @POST("nota-falta")
    fun getCurrentGrandesFailure(@Body cookie: String) : Observable<Response<JSONObject>>
}