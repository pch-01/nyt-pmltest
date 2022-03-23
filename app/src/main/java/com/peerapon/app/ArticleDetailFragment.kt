package com.peerapon.app

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.peerapon.app.viewmodel.ArticleDetailViewModel
import com.peerapon.domain.contract.ArticleDetail
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.detail_content_main.*
import kotlinx.android.synthetic.main.error_view.*
import kotlinx.android.synthetic.main.loading_view.*
import javax.inject.Inject

class ArticleDetailFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val args: ArticleDetailFragmentArgs by navArgs()

    private val viewModel: ArticleDetailViewModel by viewModels { viewModelFactory }

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
        AndroidSupportInjection.inject(this)

        viewModel.content.observe(this, profileObserver)
        viewModel.showError.observe(this, errorObserver)
        viewModel.showLoading.observe(this, loadingObserver)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.onLoad(args.id)
        super.onViewCreated(view, savedInstanceState)

    }

    private val profileObserver = Observer<ArticleDetail?> { detail ->
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