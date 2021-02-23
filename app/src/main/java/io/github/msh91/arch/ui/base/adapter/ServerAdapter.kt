package io.github.msh91.arch.ui.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.msh91.arch.R
import io.github.msh91.arch.data.source.db.entity.ServerModel

class ServerAdapter : PagedListAdapter<ServerModel, ServerAdapter.PokiViewHolder>(POKI_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_server, parent, false)
        return PokiViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokiViewHolder, position: Int) {
        val poki = getItem(position)
        poki?.let { holder.bind(it) }
    }

    class PokiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val pokiImage: ImageView = view.findViewById(R.id.item_poki_picture)
//        private val pokiName: TextView = view.findViewById(R.id.item_poki_name)
        fun bind(poki: ServerModel) {
//            pokiName.text = poki.name?.toUpperCase(Locale.FRENCH)
//            Picasso.get()
//                .load(Utils.IMAGE_BASE_URL + Utils.getPokemonIdFromUrl(poki.pokiUrl!!) + Utils.IMAGE_EXTENSION)
//                .resize(96, 96)
//                .centerInside()
//                .placeholder(R.drawable.ic_loading)
//                .into(pokiImage)
        }
    }

    companion object {
        private val POKI_COMPARATOR = object : DiffUtil.ItemCallback<ServerModel>() {
            override fun areItemsTheSame(oldItem: ServerModel, newItem: ServerModel): Boolean =
                oldItem.ServerId == newItem.ServerId

            override fun areContentsTheSame(oldItem: ServerModel, newItem: ServerModel): Boolean =
                newItem.equals(oldItem)
        }
    }
}
