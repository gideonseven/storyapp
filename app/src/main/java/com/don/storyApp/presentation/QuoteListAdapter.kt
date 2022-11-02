package com.don.storyApp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.don.storyApp.databinding.ItemStoryBinding
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Constant
import com.don.storyApp.util.DateHelper

/**
 * Created by gideon on 02 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

class QuoteListAdapter constructor(
    val onClick: (Story, AppCompatImageView) -> Unit
) :
    PagingDataAdapter<Story, QuoteListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    inner class MyViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Story) {
            with(binding) {
                story = data
                helper = DateHelper()
                dateFormat = Constant()
                cvContainer.setOnClickListener {
                    onClick(data, ivImage)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}