package com.csk.astromap.ui.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.csk.astromap.R
import com.csk.astromap.ui.Preview.PreviewData

@Composable
fun LocationOnMapPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.box_height_for_map))
            .background(Color.LightGray)
    ) {
        Text(
            text = stringResource(id = R.string.preview),
            style = MaterialTheme.typography.headlineMedium, modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.5f)
        )
    }
}

@Preview
@Composable
fun UserLocationPreview() {
    LocationInfoField(
        label = R.string.your_location,
        userAndISSLocation = PreviewData.userAndISSLocation
    )
}

@Preview
@Composable
fun ISSLocationPreview () {
    LocationInfoField(
        label = R.string.iss_location,
        userAndISSLocation = PreviewData.userAndISSLocation
    )
}