package com.el3asas.regym.ui.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.os.Build
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.el3asas.regym.R
import com.el3asas.regym.helpers.So3rOPJ
import com.el3asas.regym.ui.calories.MyDragShadowBuilder
import timber.log.Timber

class So3ratAdapter(private val so3rOPJS: ArrayList<So3rOPJ>) :
    RecyclerView.Adapter<So3ratAdapter.So3ratViewHolder>() {

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): So3ratViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.one_item_so3rat, null, false)
        return So3ratViewHolder(view)
    }

    override fun onBindViewHolder(holder: So3ratViewHolder, position: Int) {
        val f = so3rOPJS[position]
        holder.type.text = f.type
        holder.amount.text = f.amount
        holder.so3r.text = f.so3r
        
       /* holder.card.apply {
            setOnLongClickListener { v ->
                // Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.
                // Create a new ClipData.Item from the ImageView object's tag.
                val item = ClipData.Item(v.tag as? CharSequence)

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This creates a new ClipDescription object within the
                // ClipData and sets its MIME type to "text/plain".
                val dragData = ClipData(
                    v.tag as? CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item
                )

                // Instantiate the drag shadow builder.
                val myShadow = MyDragShadowBuilder(this)

                // Start the drag.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(
                        dragData,  // The data to be dragged
                        myShadow,  // The drag shadow builder
                        null,      // No need to use local data
                        0          // Flags (not currently used, set to 0)
                    )
                } else {
                    v.startDrag(
                        dragData,  // The data to be dragged
                        myShadow,  // The drag shadow builder
                        null,      // No need to use local data
                        0          // Flags (not currently used, set to 0)
                    )
                }

                // Indicate that the long-click was handled.
                true
            }
        }
        holder.card.setOnDragListener { view: View, dragEvent: DragEvent ->
            Timber.d("--------------- drag started")
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Timber.d("--------------- drag started")
                }
            }
            return@setOnDragListener true
        }*/
    }

    override fun getItemCount(): Int {
        return so3rOPJS.size
    }

    class So3ratViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val type: TextView = itemView.findViewById(R.id.type)
        val amount: TextView = itemView.findViewById(R.id.amount)
        val so3r: TextView = itemView.findViewById(R.id.so3r)
        val card: CardView = itemView.findViewById(R.id.card)
    }
}