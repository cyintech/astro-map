package com.csk.astromap.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.csk.astromap.R
import com.csk.astromap.ui.screen.component.ISSLocationPreview
import com.csk.astromap.ui.screen.component.LocationOnMapPreview
import com.csk.astromap.ui.screen.component.UserLocationPreview
import com.csk.astromap.ui.theme.AstroMap


@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        UserLocationPreview()
        ISSLocationPreview()
        LocationOnMapPreview()
        Text(text = stringResource(id = R.string.error), textAlign = TextAlign.Center)
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    AstroMap {
        ErrorScreen()
    }
}