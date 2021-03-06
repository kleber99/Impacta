package br.com.studiotrek.faculdadeimpacta.presentation.gradesAbsence

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View
import br.com.studiotrek.faculdadeimpacta.App
import br.com.studiotrek.faculdadeimpacta.R
import br.com.studiotrek.faculdadeimpacta.domain.entity.gradesAbsence.GradesAbsenceResponse
import br.com.studiotrek.faculdadeimpacta.utils.Analytics
import br.com.studiotrek.faculdadeimpacta.utils.PreferencesManager
import com.google.firebase.analytics.FirebaseAnalytics
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_grades_absence.*
import javax.inject.Inject

/**
 * Created by kleber on 05/03/2018.
 */
class GradesAbsenceActivity : AppCompatActivity(), GradesAbsencePresenter.View {

    @Inject
    lateinit var presenter: GradesAbsencePresenter
    private val TAG: String = "GradesAbsenceActivity"
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grades_absence)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        init()
    }


    private fun init() {
        (application as App).component.inject(this)
        presenter.bindView(this)

        val bundle = intent.extras
        if (bundle != null) {
            pbGrandesAbsence.visibility = View.VISIBLE

            val semester = bundle.getString("urlSemestre")

            getGradesAndAbsences(PreferencesManager(this).cookie.cookie, semester)
            firebaseAnalytics = FirebaseAnalytics.getInstance(this)
            Analytics.logVisit(firebaseAnalytics, PreferencesManager(this).user?.ra!!, "$TAG - $semester")
        }

    }

    private fun setupList(classAbsenceResponse: GradesAbsenceResponse) {
        val sectionAdapter = SectionedRecyclerViewAdapter()

        sectionAdapter.addSection(GrandesAbsenceSection(classAbsenceResponse))

        rvNoteAbsence.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        rvNoteAbsence.adapter = sectionAdapter
    }

    private fun getGradesAndAbsences(cookie: String, semester: String) {
        presenter.getGrandesAbsence(cookie, semester)
    }

    override fun successRequest(classGradesAbsence: GradesAbsenceResponse) {
        Log.d(TAG, "GrandesAbsence: "+ classGradesAbsence.toString())
        pbGrandesAbsence.visibility = View.INVISIBLE
        setupList(classGradesAbsence)
    }

    override fun badRequest(errorMessage: String) {
        //Apresentar dados na activity
        pbGrandesAbsence.visibility = View.INVISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}