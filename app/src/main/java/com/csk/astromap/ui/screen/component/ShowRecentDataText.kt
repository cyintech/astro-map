package com.csk.astromap.ui.screen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.csk.astromap.R

@Composable
fun ShowRecentDataText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.text_data_padding)),
        overflow = TextOverflow.Ellipsis
    )
}