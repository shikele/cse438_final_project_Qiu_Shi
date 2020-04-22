package com.example.final_project.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.util.Order
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.fragment_order_checkout.view.*


class CheckoutViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.checkout_item,parent,false)){
    private val name: TextView = itemView.findViewById(R.id.checkout_food_name)

    private val amount : TextView = itemView.findViewById(R.id.checkout_amount)
    private val total : TextView = itemView.findViewById(R.id.checkout_total)




    fun bind(order: Order){
        val amountStr = order.amount.toString()
        val totalStr = order.total_price.toString()
        name.text = order.foodname
        amount.text = "Amount: $amountStr"
        total.text = "Total: $totalStr"
    }





}

class CheckoutAdapter(private val orderList:ArrayList<Order>, private var idList:ArrayList<String>)
    : RecyclerView.Adapter<CheckoutViewHolder>(){
    lateinit var db : FirebaseFirestore
    private var id = ""


    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):CheckoutViewHolder{
        val inflater = LayoutInflater.from(parent.context)
        return CheckoutViewHolder(inflater,parent)
    }
    override fun onBindViewHolder(holder:CheckoutViewHolder,position:Int){
        val order:Order = orderList[position]
        holder.bind(order)
        holder.itemView.setOnClickListener{
            Log.d("mylog", "here")
            removeItem(position)
            removeItemDB(position)
        }
    }
    override fun getItemCount():Int = orderList.size

    private fun removeItem(position: Int) {
        orderList.removeAt(position)

        notifyDataSetChanged()
    }

    private fun removeItemDB(position:Int){
        id = idList[position]
        Log.d("mylog", id)
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.firestoreSettings = settings
        db.collection("orders").document("$id")
            .delete()
            .addOnSuccessListener { Log.d("mylog", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("mylog", "Error deleting document", e) }


    }


}