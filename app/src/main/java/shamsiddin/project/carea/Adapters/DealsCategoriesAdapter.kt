package shamsiddin.project.carea.Adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shamsiddin.project.carea.DataClasses.Titles
import shamsiddin.project.carea.R

class DealsCategoriesAdapter(var list: MutableList<Titles>, val choosedTopic: ChoosedTopic):RecyclerView.Adapter<DealsCategoriesAdapter.MyHolder>() {
    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var text = itemView.findViewById<TextView>(R.id.dealsname)
        var layout = itemView.findViewById<RelativeLayout>(R.id.dealsrealtive)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myholder = MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.topdeals_item, parent, false))
        return myholder
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val a = list.get(position)
        holder.text.text = a.title
        if (a.status){
            holder.itemView.setBackgroundResource(R.drawable.outlinedboxdark)
            holder.text.setTextColor(Color.parseColor("#ffffff"))
        }else{
            holder.itemView.setBackgroundResource(R.drawable.outlinedbox)
            holder.text.setTextColor(Color.parseColor("#000000"))
        }
        holder.itemView.setOnClickListener {
            choosedTopic.Topic(a.title)
            if (a.status){
                holder.itemView.setBackgroundResource(R.drawable.outlinedbox)
                holder.text.setTextColor(Color.parseColor("#000000"))
                a.status = false
                notifyDataSetChanged()
            }else{
                holder.itemView.setBackgroundResource(R.drawable.outlinedboxdark)
                holder.text.setTextColor(Color.parseColor("#ffffff"))
                a.status = true
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ChoosedTopic{
        fun Topic(string: String)
    }
}