package com.don.storyApp.presentation.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.don.storyApp.R
import com.don.storyApp.databinding.ItemStoryBinding
import com.don.storyApp.domain.model.Story
import com.don.storyApp.util.Constant
import com.don.storyApp.util.DateHelper


/**
 * Created by gideon on 29 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class StoriesAdapter constructor(
    val onClick: (Story, AppCompatImageView) -> Unit
) :
    RecyclerView.Adapter<StoriesAdapter.ItemViewHolder>() {

    private var listStories: List<Story> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemStoryBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_story, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(listStories[position])
    }

    override fun getItemCount() = listStories.size

    fun submitList(stories: List<Story>) {
        listStories = listOf()
        listStories = stories
        notifyItemRangeChanged(0, stories.count())
    }

    inner class ItemViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Story) {
            with(binding) {
                story = item
                helper = DateHelper()
                dateFormat = Constant()
                cvContainer.setOnClickListener {
                    onClick(item, binding.ivImage)
                }
            }
        }
    }
}