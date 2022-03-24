package com.peerapon.app.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.peerapon.app.R
import com.peerapon.app.ui.component.ArticleImage
import com.peerapon.app.viewmodel.ArticleDetailViewModel

@Composable
fun ArticleDetailScreen(
    viewModel: ArticleDetailViewModel = hiltViewModel(),
) {
    val detailContent = viewModel.content.observeAsState()
    val isError = viewModel.showError.observeAsState()
    val isLoading = viewModel.showLoading.observeAsState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Box(modifier = Modifier.wrapContentHeight()) {
                if (isError.value == true) {
                    Text(
                        text = stringResource(R.string.error_loading),
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                top = 8.dp,
                                end = 16.dp,
                            )
                    )
                } else {
                    detailContent.value?.let {
                        ArticleImage(
                            url = it.thumbnailUrl ?: "",
                            height = dimensionResource(id = R.dimen.image_dimen)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                        ) {
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(dimensionResource(id = R.dimen.image_dimen))
                                    .padding(
                                        start = 16.dp,
                                        top = 8.dp,
                                        end = 16.dp,
                                    )
                            )
                            Text(
                                text = it.abstractText,
                                style = MaterialTheme.typography.body1,
                                color = Color.DarkGray,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 16.dp,
                                        top = 8.dp,
                                        end = 16.dp,
                                    )
                            )
                        }
                    }
                }
            }
        }
        if (isLoading.value == true) {
            LinearProgressIndicator(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
        }
    }
}
