package com.example.iutilities

import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_marketplace.*
import kotlinx.android.synthetic.main.cardview.view.*

class Marketplace : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        sell_button_sell.setOnClickListener {
            val intent = Intent(this, Sell::class.java)
            startActivity(intent)
        }

        fetchitems()
    }

    private fun fetchitems()
    {
        val adapter = GroupAdapter<ViewHolder>()
        val ref = FirebaseDatabase.getInstance().getReference("/sell")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach{
                    val item_tmp = it.getValue(ItemObj::class.java)
                    if ( item_tmp != null )
                    {
                        adapter.add(itemholder(item_tmp))
                    }
                }
                item_recyclerview.adapter = adapter

                adapter.setOnItemClickListener { item, view ->
                    val item_tmptmp = item as itemholder
                    val intent = Intent(view.context, BuyItem::class.java)
                    intent.putExtra("ITEM", item_tmptmp.item)
                    startActivity(intent)
                }
            }
        })
    }

}

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

