package com.csk.myjpmcandroid.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.csk.myjpmcandroid.R

@Composable
fun AskLocationPermissionButton(
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dimensionResource(
                    id = R.dimen.column_padding
                )
            )
    ) {
        Button(onClick = { onClick() }) {
            Text(text = stringResource(id = R.string.ask_location_permission))
        }
        Text(
            text = stringResource(id = R.string.reason_for_location_permission_access),
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.column_padding))
        )
    }
}