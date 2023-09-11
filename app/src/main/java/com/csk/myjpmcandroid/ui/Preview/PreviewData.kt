package com.csk.myjpmcandroid.ui.Preview

import com.csk.myjpmcandroid.data.model.ISSAstros
import com.csk.myjpmcandroid.data.model.ISSLocation
import com.csk.myjpmcandroid.data.model.ISSPosition
import com.csk.myjpmcandroid.data.model.People
import com.csk.myjpmcandroid.data.source.local.model.UserISSDistance
import com.csk.myjpmcandroid.model.UserAndISSLocation
import com.csk.myjpmcandroid.model.UserLocation
import com.csk.myjpmcandroid.ui.viewmodel.ISSInfo
import com.csk.myjpmcandroid.ui.viewmodel.RecentItemUiState

object PreviewData {
    val issInfo = ISSInfo(
        issLocation = ISSLocation(
            issPosition = ISSPosition(
                latitude = "25.1697",
                longitude = "-101.9786"
            ),
            message = "success",
            timestamp = 1694229254
        ),
        issAstros = ISSAstros(
            message = "success",
            number = 2,
            people = listOf(
                People(craft = "ISS", name = "Sergey Prokopyev"),
                People(craft = "ISS", name = "Dmitry Petelin"),
                People(craft = "ISS", name = "Sergey Prokopyev"),
                People(craft = "ISS", name = "Dmitry Petelin")
            )
        )
    )

    val recentItemUiState = RecentItemUiState(
        listOf(
            UserISSDistance(id = 1, "1","2", "0.0"),
            UserISSDistance(id = 2, "2","3", "0.0")
        )
    )

    val userAndISSLocation = UserAndISSLocation(
        userLocation = UserLocation(0.0,0.0),
        issLocation = issInfo.issLocation
    )
}