package shamsiddin.project.carea.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import shamsiddin.project.carea.DataClasses.OrderedCars
import shamsiddin.project.carea.R

class OrderedCarsAdapter(var mutableList: MutableList<OrderedCars>, var context: Context): RecyclerView.Adapter<OrderedCarsAdapter.MyHolder>() {
    class MyHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        var rasm = itemview.findViewById<ImageView>(R.id.done_image)
        var nomi = itemview.findViewById<TextView>(R.id.done_name)
        var puli = itemview.findViewById<TextView>(R.id.done_price)
        var card = itemview.findViewById<CardView>(R.id.cardfororderedcars)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myholder = MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.orderedcar_item, parent, false))
        return myholder
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val a = mutableList[position]
        holder.rasm.setImageResource(a.image)
        holder.nomi.text = a.name
        holder.puli.text = a.price
        val anim = AnimationUtils.loadAnimation(context, R.anim.pop_up)
        holder.card.startAnimation(anim)
    }
}