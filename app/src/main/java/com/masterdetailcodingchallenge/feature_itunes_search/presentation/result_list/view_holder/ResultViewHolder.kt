package com.masterdetailcodingchallenge.feature_itunes_search.presentation.result_list.view_holder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.masterdetailcodingchallenge.R
import com.masterdetailcodingchallenge.common.util.centerCrop
import com.masterdetailcodingchallenge.databinding.ItemResultBinding
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import com.masterdetailcodingchallenge.feature_itunes_search.presentation.result_list.ResultListPagerAdapter

class ResultViewHolder(
    val binding: ItemResultBinding,
    val interaction: ResultListPagerAdapter.Interaction
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("UseCompatLoadingForDrawables")
    fun bind(result: Result) {
        var dynamicResult = result
        binding.root.apply {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                setOnClickListener {
                    interaction.onItemClick(bindingAdapterPosition, result)
                }
            }
        }
        binding.apply {
            ivArtWork.centerCrop(result.imageUrl)
            tvTrackName.text = result.title
            tvArtist.text = if (result.artistName.lowercase() == "unknown" || result.artistName.isEmpty()) {
                "Unknown Artist"
            } else {
                result.artistName
            }
            tvType.text = result.type

            tvPrice.text = "$${result.price}"
            tvGenre.text = result.primaryGenreName

            btnFavorite.setOnClickListener {
                dynamicResult = result.copy(isFavorite = !result.isFavorite)

                if (dynamicResult.isFavorite) {
                    btnFavorite.setImageDrawable(btnFavorite.context.getDrawable(R.drawable.favorite))
                } else {
                    btnFavorite.setImageDrawable(btnFavorite.context.getDrawable(R.drawable.favorite_filled))
                }

                interaction.updateFavoriteStatus(dynamicResult)
            }

            if (result.isFavorite) {
                btnFavorite.setImageDrawable(btnFavorite.context.getDrawable(R.drawable.favorite_filled))
            } else {
                btnFavorite.setImageDrawable(btnFavorite.context.getDrawable(R.drawable.favorite))
            }


        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            interaction: ResultListPagerAdapter.Interaction
        ): ResultViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemResultBinding.inflate(inflater, parent, false)
            return ResultViewHolder(binding, interaction)
        }
    }
}