package br.com.studiotrek.faculdadeimpacta.presentation.home

import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.studiotrek.faculdadeimpacta.R
import br.com.studiotrek.faculdadeimpacta.presentation.schedule.ScheduleDetailModel
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlinx.android.synthetic.main.home_header.view.*
import kotlinx.android.synthetic.main.schedule_item.view.*

/**
 * Created by kleber on 02/03/2018.
 */
class HomeSection(var classHome: HomeScheduleResponse) : StatelessSection(R.layout.home_header, R.layout.schedule_item) {

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return HomeSection.HeaderViewHolder(view)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        holder as HeaderViewHolder?
        holder?.bind(classHome.name, classHome.course)
    }

    override fun getContentItemsTotal(): Int {
        return classHome.homeScheduleModel.scheduleDetail.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return ItemViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as HomeSection.ItemViewHolder
        val scheduledDetail = classHome.homeScheduleModel.scheduleDetail.get(position)
        holder.bind(scheduledDetail)
    }

    class HeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(name: String, course: String) {
            view.tv_homeheader_name.text = String.format("Olá, %s", name)
            view.tv_homeheader_course.text = String.format("Hoje suas aulas no %s são:", course)
        }

    }

    internal inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(scheduleDetail: ScheduleDetailModel) {
            with (scheduleDetail) {
                itemView.tvClassTitle.text = className
                itemView.tvProfessorName.text = professor
                itemView.tvClassroom.text = room.trim()
            }
        }
    }

}