package com.example.roomdatabasedemo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabasedemo.R
import com.example.roomdatabasedemo.databinding.RawLayoutBinding
import com.example.roomdatabasedemo.modal.UserEntity

class UserDataAdapter(private var allData:MutableList<UserEntity> ,
                     private val deleteItemClick : DeleteItemClickListner ,
                     private val updateItemClick : UpdateItemClickListner)
    : RecyclerView.Adapter<UserDataAdapter.ViewHolder>()
{
    class ViewHolder( private var binding : RawLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val linearLayout = binding.llMain
        val textview1 = binding.tvId
        val textview2 = binding.tvName
        val textview3 = binding.tvEmail
        val btnEdit = binding.ivEdit
        val btnDelete = binding.ivDelete
    }
    interface DeleteItemClickListner
    {
        fun onItemClickDeleteData(userEntity: UserEntity)
    }
    interface UpdateItemClickListner
    {
        fun onItemClickUpdateData(userEntity: UserEntity)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RawLayoutBinding.inflate(LayoutInflater.from(parent.context)  , parent ,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myData = allData[position]
        holder.textview1.text = myData.id.toString()
        holder.textview2.text = myData.username
        holder.textview3.text = myData.email

        if(position % 2 == 0)
        {
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context , R.color.lightGrey))
        }
        else
        {
            holder.linearLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.context , R.color.white))
        }

        holder.btnDelete.setOnClickListener {
            deleteItemClick.onItemClickDeleteData(allData[position])
        }
        holder.btnEdit.setOnClickListener {
            updateItemClick.onItemClickUpdateData(allData[position])
        }

    }

    override fun getItemCount(): Int {
        return allData.size
    }




}