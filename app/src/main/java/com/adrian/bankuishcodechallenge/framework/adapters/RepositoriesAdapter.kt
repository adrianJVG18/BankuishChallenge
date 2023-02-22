package com.adrian.bankuishcodechallenge.framework.adapters

import android.content.res.Resources.getSystem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adrian.bankuishcodechallenge.R
import com.adrian.bankuishcodechallenge.adapter.model.RepositoryUi
import com.adrian.bankuishcodechallenge.databinding.ItemSimpleRepositoryBinding
import com.adrian.bankuishcodechallenge.framework.utils.toDp
import com.squareup.picasso.Picasso

class RepositoriesAdapter(
    private var items: MutableList<RepositoryUi>,
    private val onItemClickListener: OnItemClickListener,
    private val onBottomReachedListener: OnBottomReachedListener
) : RecyclerView.Adapter<RepositoryViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: RepositoryUi)
    }

    interface OnBottomReachedListener {
        fun onBottomReached(isAtBottom: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding =
            ItemSimpleRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoryViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
        // It is the last item of the list
        if (position + 1 == itemCount) {
            // Set bottom margin to 72dp
            setBottomMargin(
                holder.itemView, (60).toDp()
            );
            onBottomReachedListener.onBottomReached(true)
        } else {
            // Reset bottom margin back to zero
            setBottomMargin(holder.itemView, 0);
            onBottomReachedListener.onBottomReached(false)
        }
    }

    // TODO make it work to replace updateList
    fun submitList(newList: List<RepositoryUi>) {
        val diffResult = DiffUtil.calculateDiff(Diff(this.items, newList))
        diffResult.dispatchUpdatesTo(this)
    }

    // TODO should be submit (uses DiffUtils) to avoid notifyDataSetChanged()) but somehow submit isn't working
    fun updateList(newList: List<RepositoryUi>) {
        items.run {
            clear()
            addAll(newList)
            notifyDataSetChanged()
        }
    }

    /*
    private val differCallback = object : DiffUtil.ItemCallback<RepositoryDto>(){
        override fun areItemsTheSame(oldItem: RepositoryDto, newItem: RepositoryDto): Boolean {
            return  oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: RepositoryDto, newItem: RepositoryDto): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)
     */

    companion object {
        private fun setBottomMargin(view: View, bottomMargin: Int) {
            if (view.layoutParams is MarginLayoutParams) {
                val params = view.layoutParams as MarginLayoutParams
                params.setMargins(
                    params.leftMargin,
                    params.topMargin,
                    params.rightMargin,
                    bottomMargin
                )
                view.requestLayout()
            }
        }
    }
}

class RepositoryViewHolder(
    private val binding: ItemSimpleRepositoryBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RepositoryUi, clickListener: RepositoriesAdapter.OnItemClickListener) {
        binding.repositoryNameText.text = item.name
        binding.repositoryAuthorText.text = item.author
        Picasso.get()
            .load(item.avatarUrl)
            .placeholder(R.drawable.baseline_person_24)
            .into(binding.avatarImageview)
        binding.root.setOnClickListener {
            clickListener.onItemClick(item)
        }
    }

}

class Diff(
    private val oldList: List<RepositoryUi>,
    private val newList: List<RepositoryUi>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}