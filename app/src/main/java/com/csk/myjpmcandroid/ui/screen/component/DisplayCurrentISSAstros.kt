package com.csk.myjpmcandroid.ui.screen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.csk.myjpmcandroid.R
import com.csk.myjpmcandroid.data.model.People

@Composable
fun DisplayCurrentISSAstros(currentAstros: List<People>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.current_iss_astros_on_board),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        LazyRow(
            modifier = Modifier.wrapContentHeight()
        ) {
            items(currentAstros) { astro ->
                Text(text = astro.name, Modifier.padding(4.dp))
            }
        }
    }
}