package com.csk.astromap.data.repository

import com.csk.astromap.data.RecentLocationRepositoryImpl
import com.csk.astromap.data.source.local.dao.RecentLocationDao
import com.csk.astromap.data.source.local.model.UserISSDistance
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecentLocationRepositoryImplTest {

    private lateinit var repository: RecentLocationRepositoryImpl
    private val dao = mockk<RecentLocationDao>()

    @Before
    fun setup() {
        repository = RecentLocationRepositoryImpl(dao)
    }

    @Test
    fun `getAllLocations should return flow from DAO`() = runTest {
        val mockList = listOf(UserISSDistance(1, "1.0, 2.0", "3.0, 4.0", "100.0"))
        every { dao.readAll() } returns flowOf(mockList)

        repository.getAllLocations().collect {
            assertEquals(mockList, it)
        }
    }

    @Test
    fun `insert should call DAO insert`() = runTest {
        val mockItem = UserISSDistance(1, "1.0, 2.0", "3.0, 4.0", "100.0")
        coEvery { dao.insert(any()) } returns Unit

        repository.insert(mockItem)

        coVerify { dao.insert(mockItem) }
    }
}
