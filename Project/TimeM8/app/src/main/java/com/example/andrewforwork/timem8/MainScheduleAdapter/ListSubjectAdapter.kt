package com.example.andrewforwork.timem8.MainScheduleAdapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.andrewforwork.timem8.R
import com.example.andrewforwork.timem8.Subject.Sub
import kotlinx.android.synthetic.main.row_main.view.*

class ListSubjectAdapter(
        internal var activity: Activity,
        internal var lstSub:List<Sub>,
        internal var edit_day:EditText,
        internal var edit_name: EditText,
        internal var edit_time: EditText,
        internal var edit_imp: Int,
        internal var edit_cnt:EditText):BaseAdapter() {
    internal var inflater:LayoutInflater

    init {
        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
        rowView = inflater.inflate(R.layout.row_main,null)

        //rowView.txt_row_id.text = lstSub[position].id.toString() разобраться с днем недели

        rowView.txt_row_count.text = lstSub[position].count.toString()
        rowView.txt_row_name.text = lstSub[position].name.toString()
        rowView.txt_row_time.text = lstSub[position].time.toString()
        rowView.txt_row_type.text = lstSub[position].type.toString()
        rowView.txt_row_day.text = lstSub[position].day.toString()

        rowView.setOnClickListener{
            edit_cnt.setText(rowView.txt_row_count.text.toString())
            edit_name.setText(rowView.txt_row_name.text.toString())
            edit_time.setText(rowView.txt_row_time.text.toString())
            edit_day.setText(rowView.txt_row_day.text.toString())

        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return lstSub[position]
    }

    override fun getItemId(position: Int): Long {
        return lstSub[position].id.toLong()
    }

    override fun getCount(): Int {
        return lstSub.size
    }
}