package com.example.mystoryapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mystoryapp.DetailStoryActivity
import com.example.mystoryapp.databinding.ItemListStoryBinding
import com.example.mystoryapp.models.response.ListStoryItem


class StoriesAdapter :
    PagingDataAdapter<ListStoryItem, StoriesAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemListStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Glide.with(holder.itemView.context)
//            .load(listStorie[position].photoUrl)
//            .centerCrop()
//            .into(holder.binding.img)
//        holder.binding.textView.text = listStorie[position].name
//
//        holder.itemView.setOnClickListener {
//            onItemClickCallBack.onItemClicked(listStorie[holder.adapterPosition])
//        }
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ViewHolder(private val binding: ItemListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listStorie: ListStoryItem) {
            Glide.with(binding.root.context)
                .load(listStorie.photoUrl)
                .centerCrop()
                .into(binding.img)
            binding.textView.text = listStorie.name


            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailStoryActivity::class.java).also {
                    it.putExtra(DetailStoryActivity.EXTRA_NAME, listStorie.name)
                                it.putExtra(DetailStoryActivity.EXTRA_DESCRIPTION, listStorie.description)
                                it.putExtra(DetailStoryActivity.EXTRA_URL_IMAGE, listStorie.photoUrl)
                }
                binding.root.context.startActivity(intent)
            }

//            itemView.setOnClickListener {
//                onOptionsItemSelected()
//            }
        }

//        itemView.setOnClickListener {
//            onItemClickCallBack.onItemClicked(listStorie[holder.adapterPosition])
//        }

    }

//    override fun getItemCount(): Int = listStorie.size

//    inner class ViewHolder(var binding: ItemListStoryBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//    }

    interface OnItemClickCallBack {
        fun onItemClicked(data: ListStoryItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}


//class StoriesAdapter(private val listStorie: List<ListStoryItem>) :
//    RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {
//
//    private lateinit var onItemClickCallBack: OnItemClickCallBack
//
//    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
//        this.onItemClickCallBack = onItemClickCallBack
//    }
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
//        val binding =
//            ItemListStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Glide.with(holder.itemView.context)
//            .load(listStorie[position].photoUrl)
//            .centerCrop()
//            .into(holder.binding.img)
//        holder.binding.textView.text = listStorie[position].name
//
//        holder.itemView.setOnClickListener {
//            onItemClickCallBack.onItemClicked(listStorie[holder.adapterPosition])
//
//
//        }
//    }
//
//    override fun getItemCount(): Int = listStorie.size
//
//    inner class ViewHolder(var binding: ItemListStoryBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//    }
//
//    interface OnItemClickCallBack {
//        fun onItemClicked(data: ListStoryItem)
//    }
//}