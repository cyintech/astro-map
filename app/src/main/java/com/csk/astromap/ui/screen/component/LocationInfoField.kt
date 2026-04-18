package com.csk.astromap.ui.screen.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.csk.astromap.R
import com.csk.astromap.model.UserAndISSLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationInfoField(
    @StringRes label: Int,
    userAndISSLocation: UserAndISSLocation,
    modifier: Modifier = Modifier
) {
    val latAndLng = when(label){
        R.string.your_location -> "lat = ${userAndISSLocation.userLocation?.latitude}, lng = ${userAndISSLocation.userLocation?.longitude}"
        R.string.iss_location -> "lat = ${userAndISSLocation.issLocation?.issPosition?.latitude}, lng = ${userAndISSLocation.issLocation?.issPosition?.longitude}"
        else -> ""
    }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        TextField(
            value = latAndLng,
            onValueChange = {},
            readOnly = true,
            maxLines = 2,
            label = {
                Text(
                    text = stringResource(
                        id = label
                    ),
                    overflow = TextOverflow.Ellipsis
                )
            }, modifier = modifier
                .fillMaxWidth()
                .padding(
                    horizontal = dimensionResource(id = R.dimen.column_padding),
                    vertical = dimensionResource(
                        id = R.dimen.vertical_padding
                    )
                )
        )
    }
}