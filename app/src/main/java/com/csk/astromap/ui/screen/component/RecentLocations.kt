package com.csk.astromap.ui.screen.component

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.csk.astromap.data.source.local.model.UserISSDistance

@Composable
fun RecentLocations(recentItemList: List<UserISSDistance>, modifier: Modifier = Modifier) {
    LazyColumn(){
        items(recentItemList, key={it.id}){ recentItem->
            RecentLocationCard(recentItem)
        }
    }

}