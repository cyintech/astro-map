package com.csk.myjpmcandroid.ui.screen.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.csk.myjpmcandroid.R


@Composable
fun DistanceInMiles(
    distance: Double
) {
    Text(
        text = stringResource(id = R.string.Distance, "$distance"),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.column_padding))
    )
}