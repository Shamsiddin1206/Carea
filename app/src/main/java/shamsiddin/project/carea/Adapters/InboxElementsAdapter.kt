package shamsiddin.project.carea.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shamsiddin.project.carea.DataClasses.InboxElementsData
import shamsiddin.project.carea.R

class InboxElementsAdapter(val list: MutableList<InboxElementsData>): RecyclerView.Adapter<InboxElementsAdapter.MyHolder>() {
    class MyHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var image = itemview.findViewById<ImageView>(R.id.inboxelements_rasm)
        var secondrasm = itemview.findViewById<ImageView>(R.id.inboxelements_staffrasmi)
        var title = itemview.findViewById<TextView>(R.id.inboxelements_title)
        var extrainfo = itemview.findViewById<TextView>(R.id.inboxelements_phonenumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myHolder = MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.inboxelements_item, parent, false))
        return myHolder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val a = list.get(position)
        holder.image.setImageResource(a.main_image)
        holder.secondrasm.setImageResource(a.staff_image)
        holder.title.text = a.firma_title
        holder.extrainfo.text = a.extras
    }
}