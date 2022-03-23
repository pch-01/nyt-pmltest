package com.peerapon.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.peerapon.app.R
import com.peerapon.domain.contract.ArticleListViewState
import kotlinx.android.synthetic.main.recycler_item.view.*

class ArticleListAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<ArticleListViewState>,
    private val launchDetailScreen: (ArticleListViewState) -> (Unit)
) : ListAdapter<ArticleListViewState, ArticleListAdapter.MyViewHolder>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view, launchDetailScreen)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position).let { holder.bind(it) }
    }

    class MyViewHolder(
        itemView: View,
        private val launchDetailScreen: (ArticleListViewState) -> (Unit)
    ) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.itemTitle

        fun bind(data: ArticleListViewState) {
            title.text = data.title
            title.setOnClickListener {
                launchDetailScreen(data)
            }
        }
    }
}