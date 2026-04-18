package com.csk.astromap.domain

import com.csk.astromap.data.model.GoogleMapsApiResponse
import com.csk.astromap.data.model.PlusCode
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAddressUseCaseTest {

    private lateinit var useCase: GetAddressUseCase
    private val repository = mockk<GoogleMapsRepo>()

    @Before
    fun setup() {
        useCase = GetAddressUseCase(repository)
    }

    @Test
    fun `invoke should return results from repository`() = runTest {
        val mockPlusCode = PlusCode("compound", "global")
        val mockResponse = GoogleMapsApiResponse(results = emptyList(), status = "OK", plus_code = mockPlusCode)
        coEvery { repository.getAddressByLatLng(any(), any()) } returns mockResponse

        val result = useCase("10.0,20.0")

        assertEquals(0, result.size)
    }
}
