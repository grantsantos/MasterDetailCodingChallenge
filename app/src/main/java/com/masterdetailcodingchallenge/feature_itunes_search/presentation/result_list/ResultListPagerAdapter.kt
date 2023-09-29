package com.masterdetailcodingchallenge.feature_itunes_search.presentation.result_list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import com.masterdetailcodingchallenge.feature_itunes_search.presentation.result_list.view_holder.ResultViewHolder

class ResultListPagerAdapter(
    private val interaction: Interaction,
): PagingDataAdapter<Result, RecyclerView.ViewHolder>(ListComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResultViewHolder.create(parent, interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { result ->
            (holder as ResultViewHolder).bind(result)
        }
    }

    class ListComparator :  DiffUtil.ItemCallback<Result>() {

        override fun areItemsTheSame(oldItem: Result, newItem: Result) =
            oldItem.trackId == newItem.trackId

        override fun areContentsTheSame(oldItem: Result, newItem: Result) =
            oldItem == newItem
    }

    interface Interaction {

        fun onItemClick(position: Int, result: Result)

        fun updateFavoriteStatus(result: Result)

    }
}