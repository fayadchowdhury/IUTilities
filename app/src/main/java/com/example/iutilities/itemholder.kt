package com.example.iutilities

import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.cardview.view.*

class itemholder(val item: ItemObj): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.itemName.setText("${item?.name.toString()}")
        viewHolder.itemView.itemPrice.setText("${item?.price.toString()}")
        Picasso.get().load("${item?.photourl.toString()}").into(viewHolder.itemView.itemPic)
    }

    override fun getLayout(): Int {
        return R.layout.cardview
    }
}