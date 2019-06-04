package com.example.iutilities

import android.util.Log.d
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.cardview_tuitions.view.*

class TuitionsPlaced (val offer : TuitionObj) : Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.offerername.setText("Offered by: ${offer?.the_person_offering.toString()}")
        viewHolder.itemView.studentclass.setText("Class: ${offer?.students_class.toString()}")
        viewHolder.itemView.studentcur.setText("Medium: ${offer?.curriculum.toString()}")
    }

    override fun getLayout(): Int {
        return R.layout.cardview_tuitions
    }
}