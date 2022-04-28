package com.el3asas.regym.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.el3asas.regym.R
import com.el3asas.regym.helpers.PrimaryOPJ
import com.el3asas.regym.helpers.Image
import java.util.*

class PrimaryAdapter(
    private val itemClickListener: ItemClickListener,
    private val context: Context,
    private val primaryOpj: ArrayList<PrimaryOPJ>
) :
    RecyclerView.Adapter<PrimaryAdapter.So3ratViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): So3ratViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.primary_recycle_item, null, false)
        return So3ratViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: So3ratViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val f = primaryOpj[position]
        holder.title.text = f.title
        val imgBmp = getBitmap(f.imgRs)
        holder.img.setImageBitmap(imgBmp)

        holder.title.transitionName = "title$position"
        holder.img.transitionName = "img$position"
        holder.card.transitionName = "card$position"
        holder.back.transitionName = "back$position"

        holder.card.setOnClickListener {
            itemClickListener.onItemClick(
                it,
                holder.card,
                holder.back,
                holder.title,
                holder.img,
                f.title,
                imgBmp
            )
        }
    }

    private fun getBitmap(img: Int) =
        Image.decodeSampledBitmapFromResource(
            context.resources,
            img,
            100,
            100
        )

    override fun getItemCount(): Int {
        return primaryOpj.size
    }

    class So3ratViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val img: ImageView = itemView.findViewById(R.id.img)
        val card = itemView.findViewById<CardView>(R.id.c)
        val back: ImageButton = itemView.findViewById(R.id.back)
    }

    interface ItemClickListener {
        fun onItemClick(
            view: View,
            cardView: CardView,
            imageButton: ImageButton,
            text: TextView,
            imgView: ImageView,
            title: String,
            img: Bitmap
        )
    }
}