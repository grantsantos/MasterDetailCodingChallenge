package com.masterdetailcodingchallenge.feature_itunes_search.presentation.result_detail

import android.graphics.Typeface
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.masterdetailcodingchallenge.R
import com.masterdetailcodingchallenge.common.base.BaseFragment
import com.masterdetailcodingchallenge.common.util.centerCrop
import com.masterdetailcodingchallenge.common.util.showSnackBar
import com.masterdetailcodingchallenge.databinding.FragmentResultDetailBinding
import com.masterdetailcodingchallenge.feature_itunes_search.presentation.ItunesSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class ResultDetailFragment : BaseFragment<FragmentResultDetailBinding>() {

    private val args: ResultDetailFragmentArgs by navArgs()

    private val viewModel: ItunesSearchViewModel by viewModels()

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentResultDetailBinding {
        return FragmentResultDetailBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        var dynamicResult = args.result
        binding.ivArtWork.centerCrop(dynamicResult.imageUrl)
        binding.tvTrackName.text = dynamicResult.title

        binding.tvGenre.text = "Genre: ${dynamicResult.primaryGenreName}"
        val rating = dynamicResult.contentAdvisoryRating.ifEmpty { "N/A" }
        binding.tvRating.text = "Rating: $rating"
        binding.tvPrice.text = "Purchase for: $${dynamicResult.price}"

        val trimmedStringDate = dynamicResult.releaseDate.removeRange(10, dynamicResult.releaseDate.length)
        val parser = SimpleDateFormat("yyyy-mm-dd")
        val format = SimpleDateFormat("MMMM dd, yyyy")
        val date = format.format(parser.parse(trimmedStringDate))
        binding.tvReleaseDate.text = "Released on: $date"

        if (dynamicResult.longDescription.isEmpty()) {
            binding.tvDescription.text = "There is no further details"
            binding.tvDescription.setTypeface(binding.tvDescription.typeface, Typeface.ITALIC)
        } else {
            binding.tvDescription.text = Html.fromHtml(dynamicResult.longDescription, Html.FROM_HTML_MODE_COMPACT)
        }

        binding.tvType.text = dynamicResult.type

        if (dynamicResult.isFavorite) {
            binding.floatingActionButton.setImageResource(R.drawable.favorite_filled)
        }

        binding.floatingActionButton.setOnClickListener {
            dynamicResult = dynamicResult.copy(isFavorite = !dynamicResult.isFavorite)
            if (dynamicResult.isFavorite) {
                binding.floatingActionButton.setImageResource(R.drawable.favorite_filled)
                binding.root.showSnackBar(
                    message = "Set as favorite",
                    duration = Snackbar.LENGTH_SHORT
                )
            } else {
                binding.floatingActionButton.setImageResource(R.drawable.favorite)
            }

            viewModel.updateFavoriteStatus(dynamicResult)
        }
    }

    override fun initObservers() {

    }
}