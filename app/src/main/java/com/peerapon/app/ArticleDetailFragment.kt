package com.peerapon.app

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.peerapon.app.viewmodel.ArticleDetailViewModel
import com.peerapon.domain.contract.ArticleDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_content_main.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.loading_view.*

@AndroidEntryPoint
class ArticleDetailFragment : BottomSheetDialogFragment() {

    private val args: ArticleDetailFragmentArgs by navArgs()
    private val viewModel: ArticleDetailViewModel by viewModels()

    override fun getTheme(): Int {
        return R.style.BottomSheetDialog
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        BottomSheetDialog(requireContext(), theme)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.content.observe(this, thumbnailObserver)
        viewModel.showError.observe(this, errorObserver)
        viewModel.showLoading.observe(this, loadingObserver)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onLoad(args.id)
        super.onViewCreated(view, savedInstanceState)

    }

    private val thumbnailObserver = Observer<ArticleDetail?> { detail ->
        if (detail == null) {
            contentViewGroup.visibility = View.GONE
        } else {
            contentViewGroup.visibility = View.VISIBLE

            Glide.with(this)
                .load(Uri.parse(detail.thumbnailUrl))
                .into(thumbnailUrl)

            titleTextView.text = detail.title
            abstractTextView.text = detail.abstractText
        }

    }

    private val loadingObserver = Observer<Boolean> { loading ->
        if (loading) {
            loadingView.visibility = View.VISIBLE
        } else {
            loadingView.visibility = View.GONE
        }

    }

    private val errorObserver = Observer<Boolean> { error ->
        if (error) {
            errorViewGroup.visibility = View.VISIBLE
        } else {
            errorViewGroup.visibility = View.GONE
        }
    }


}