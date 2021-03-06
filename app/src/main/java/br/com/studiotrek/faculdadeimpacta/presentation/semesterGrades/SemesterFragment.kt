package br.com.studiotrek.faculdadeimpacta.presentation.semesterGrades

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.studiotrek.faculdadeimpacta.App
import br.com.studiotrek.faculdadeimpacta.R
import br.com.studiotrek.faculdadeimpacta.domain.entity.CookieDTO
import br.com.studiotrek.faculdadeimpacta.domain.entity.semesterGrades.SemesterResponse
import br.com.studiotrek.faculdadeimpacta.utils.PreferencesManager
import kotlinx.android.synthetic.main.fragment_semester.*
import javax.inject.Inject

/**
 * Created by Kleber on 21/02/2018.
 */
class SemesterFragment : Fragment(), SemesterPresenter.View {

    @Inject
    lateinit var presenter: SemesterPresenter
    private val TAG: String = "SemesterPresenter"
    private val SEMESTER_KEY: String = "semester"
    private var semesters: SemesterResponse? = null

    companion object {
        fun newInstance() = SemesterFragment() as Fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_semester, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (semesters != null) {
            outState.putParcelable(SEMESTER_KEY, semesters)
        }
        super.onSaveInstanceState(outState)
    }

    private fun init(savedInstanceState: Bundle?) {
        ((activity!!.application) as App).component.inject(this)
        presenter.bindView(this)

        if (savedInstanceState != null && savedInstanceState.containsKey(SEMESTER_KEY)) {
            val semesters = savedInstanceState.get(SEMESTER_KEY) as SemesterResponse?
            setupList(semesters as SemesterResponse)
        } else {
            pbSemester.visibility = View.VISIBLE
            getSemesterInfo(PreferencesManager(context!!).cookie)
        }

    }

    private fun setupList(semesterResponse : SemesterResponse) {
        semesters = semesterResponse

        val sectionAdapter = SemesterAdapter(semesterResponse)

        rvSemester.layoutManager = LinearLayoutManager(context!!) as RecyclerView.LayoutManager?
        rvSemester.adapter = sectionAdapter
    }

    fun getSemesterInfo(cookie: CookieDTO) {
        presenter.getSemester(cookie)
    }

    override fun successRequest(semesterResponse: SemesterResponse) {
        pbSemester.visibility = View.INVISIBLE
        setupList(semesterResponse)
    }

    override fun badRequest(errorMessage: String) {
        pbSemester.visibility = View.INVISIBLE
    }
}