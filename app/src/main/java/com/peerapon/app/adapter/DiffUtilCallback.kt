package com.peerapon.app.adapter

import androidx.recyclerview.widget.DiffUtil
import com.peerapon.domain.contract.ArticleListViewState

class DiffUtilCallBack : DiffUtil.ItemCallback<ArticleListViewState>() {
    override fun areItemsTheSame(
        oldItem: ArticleListViewState,
        newItem: ArticleListViewState
    ): Boolean {
        return oldItem.url == newItem.url && oldItem.thumbnailUri == newItem.thumbnailUri
    }

    override fun areContentsTheSame(
        oldItem: ArticleListViewState,
        newItem: ArticleListViewState
    ): Boolean {
        return oldItem.url == newItem.url
                && oldItem.thumbnailUri == oldItem.thumbnailUri
                && oldItem.title == newItem.title
    }

}