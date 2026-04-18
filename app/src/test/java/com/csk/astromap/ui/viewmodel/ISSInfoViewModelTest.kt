package com.csk.astromap.ui.viewmodel

import com.csk.astromap.data.model.ISSAstros
import com.csk.astromap.data.model.ISSLocation
import com.csk.astromap.data.model.ISSPosition
import com.csk.astromap.domain.ISSInfoRepository
import com.csk.astromap.domain.RecentLocationRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class ISSInfoViewModelTest {

    private lateinit var viewModel: ISSInfoViewModel
    private val issInfoRepository = mockk<ISSInfoRepository>()
    private val recentLocationRepository = mockk<RecentLocationRepository>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { recentLocationRepository.getAllLocations() } returns flowOf(emptyList())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should fetch ISS info successfully`() = runTest {
        val mockLocation = ISSLocation(ISSPosition("10.0", "20.0"), "success", 123)
        val mockAstros = ISSAstros("success", 2, emptyList())

        coEvery { issInfoRepository.getISSLocation() } returns mockLocation
        coEvery { issInfoRepository.getISSOnBoardAstronauts() } returns mockAstros

        viewModel = ISSInfoViewModel(issInfoRepository, recentLocationRepository)
        
        advanceUntilIdle()

        val state = viewModel.issInfoUiState.value
        assert(state is ISSInfoUiState.Success)
        val successState = state as ISSInfoUiState.Success
        assertEquals(mockLocation, successState.issInfo.issLocation)
        assertEquals(mockAstros, successState.issInfo.issAstros)
    }

    @Test
    fun `init should handle error when API call fails`() = runTest {
        coEvery { issInfoRepository.getISSLocation() } throws IOException("Network error")

        viewModel = ISSInfoViewModel(issInfoRepository, recentLocationRepository)
        
        advanceUntilIdle()

        val state = viewModel.issInfoUiState.value
        assert(state is ISSInfoUiState.Error)
    }
}
