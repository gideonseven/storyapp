package com.don.storyApp.presentation.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.don.storyApp.R
import com.don.storyApp.databinding.ItemStoryBinding
import com.don.storyApp.domain.model.Story


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class StoriesAdapter constructor(
    private val data: List<Story>,
    val onClick: (Story) -> Unit
) :
    RecyclerView.Adapter<StoriesAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemStoryBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_story, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class ItemViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            binding.story = item
        }
    }
}