package com.example.cedri_app.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cedri_app.R
import com.example.cedri_app.model.MenuCard
import kotlinx.android.synthetic.main.menu_card_item.view.*

class MenuCardListAdapter(
    private val menuCards : List<MenuCard>,
    private val context: Context,
    private val onItemClickListener: (menuCard: MenuCard, position: Int) -> Unit) : Adapter<MenuCardListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val menuCard = menuCards[position]
        holder.bindView(menuCard)
        holder.itemView.setOnClickListener {
            onItemClickListener(menuCard, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() : Int {
        return menuCards.size
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(menuCard: MenuCard) {
            /*
            val title = itemView.menu_card_item_card_view.menu_card .menu .menu_card_item_button_name
            val image = itemView.menu_card_item_image
            title.text = menuCard.title
            image.setImageResource(menuCard.drawableId)*/


            /*

            <LinearLayout
            android:layout_gravity="center_horizontal|center_vertical"
            android:layout_margin="16dp"
            android:orientation="vertical"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@color/colorPrimaryDark">

            <ImageView
            android:src="@drawable/ic_spellcheck_black_24dp"
            android:scaleType="centerCrop"
            android:layout_gravity="center_horizontal"
            android:layout_width="48dp"
            android:layout_height="48dp"/>

            <TextView


            </LinearLayout>*/

        }
    }
}