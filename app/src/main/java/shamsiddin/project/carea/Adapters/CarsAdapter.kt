package shamsiddin.project.carea.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.R

class CarsAdapter(var list: MutableList<CarsData>, val onPressed: OnPressed, var onSelected: OnSelected, var context: Context ): RecyclerView.Adapter<CarsAdapter.MyHolder>() {
    class MyHolder(view: View): RecyclerView.ViewHolder(view) {
        var rasmi = view.findViewById<ImageView>(R.id.carimage)
        var nomi = view.findViewById<TextView>(R.id.carname)
        var reyting = view.findViewById<TextView>(R.id.reyting)
        var price = view.findViewById<TextView>(R.id.narxi)
        var yurakk = view.findViewById<ImageView>(R.id.yurakcha)
        var card = view.findViewById<ConstraintLayout>(R.id.contrain_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val myholder = MyHolder(LayoutInflater.from(parent.context).inflate(R.layout.cars_item, parent, false))
        return myholder
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val a = list.get(position)
        holder.rasmi.setImageResource(a.carrasm)
        holder.nomi.text = a.carname
        holder.price.text = a.carprice
        holder.reyting.text = a.carreyting
        val anim = AnimationUtils.loadAnimation(context, R.anim.item_anim)
        holder.card.startAnimation(anim)
        if (a.status){
            holder.yurakk.setImageResource(R.drawable.yurak2)
        }else{
            holder.yurakk.setImageResource(R.drawable.yurak)
        }
        holder.yurakk.setOnClickListener {
            if (!a.status){
                holder.yurakk.setImageResource(R.drawable.yurak2)
                a.status=true
                notifyDataSetChanged()
            }else{
                holder.yurakk.setImageResource(R.drawable.yurak)
                a.status=false
                notifyDataSetChanged()
            }
            onSelected.onSelect(a)
        }
        holder.itemView.setOnClickListener {
            onPressed.onPressed(a)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface OnPressed{
        fun onPressed(carsData: CarsData)
    }
    interface OnSelected{
        fun onSelect(carsData: CarsData)
    }

}