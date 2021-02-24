package io.github.msh91.arch.ui.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.msh91.arch.R
import io.github.msh91.arch.data.source.db.entity.ServerModel
import io.github.msh91.arch.databinding.ItemServerBinding

class ServerAdapter : PagingDataAdapter<ServerModel, ServerAdapter.ServerViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServerViewHolder {
        val binding = DataBindingUtil.inflate<ItemServerBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_server,
            parent,
            false
        )

        return ServerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServerViewHolder, position: Int) {
        val serverModel = getItem(position)

        serverModel?.let { holder.bind(it) }
    }

    class ServerViewHolder(val binding: ItemServerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(serverModel: ServerModel) {
            binding.item = serverModel
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ServerModel>() {
            override fun areItemsTheSame(oldItem: ServerModel, newItem: ServerModel): Boolean {

                return oldItem.ServerId == newItem.ServerId

            }

            override fun areContentsTheSame(oldItem: ServerModel, newItem: ServerModel): Boolean {
                return newItem == oldItem

            }
        }
    }
}
