package com.example.iutilities

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.cardview_food.view.*
import kotlinx.android.synthetic.main.cardview_rides.view.*
import java.text.FieldPosition

class FoodAdapter(val food: FoodObj): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        Log.d("IUTils", "${food?.restname.toString()}")
        viewHolder.itemView.orderName.setText("Restaurant: ${food?.restname.toString()}")
        viewHolder.itemView.orderDesc.setText("Order description: ${food?.description.toString()}")
        viewHolder.itemView.orderCost.setText("Cost: ${food?.cost.toString()}")
    }

    override fun getLayout(): Int {
        return R.layout.cardview_food
    }
}