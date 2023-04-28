package shamsiddin.project.carea.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import shamsiddin.project.carea.DataClasses.CarsData
import shamsiddin.project.carea.DataClasses.OfferCarsInfo
import shamsiddin.project.carea.R

class OfferCarsAdapter(private val items: MutableList<OfferCarsInfo>):RecyclerView.Adapter<OfferCarsAdapter.ItemViewHolder>() {
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val image = view.findViewById<ImageView>(R.id.moshina_rasm)
        private val foizi = view.findViewById<TextView>(R.id.foiz)
        private val text1 = view.findViewById<TextView>(R.id.description1)
        private val text2 = view.findViewById<TextView>(R.id.description2)

        fun info(offerCarsInfo: OfferCarsInfo){
            image.setImageResource(offerCarsInfo.rasm)
            foizi.text = offerCarsInfo.discount
            text1.text = offerCarsInfo.matn1
            text2.text = offerCarsInfo.matn2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.offer_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.info(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}