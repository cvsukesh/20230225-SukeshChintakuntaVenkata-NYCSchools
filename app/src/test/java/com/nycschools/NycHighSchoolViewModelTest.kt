package com.nycschools

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.nycschools.data.model.NycSchool
import com.nycschools.data.network.NycApiService
import com.nycschools.repository.MockNycSchoolRepository
import com.nycschools.room.NycSchoolDAO
import com.nycschools.mvvm.MainViewModel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NycHighSchoolViewModelTest {

    @Mock
    lateinit var nycSchoolApi: NycApiService

    private lateinit var repository: MockNycSchoolRepository

    @Mock
    lateinit var nycSchoolDao: NycSchoolDAO

    private lateinit var instrumentationContext: Context

    private lateinit var nycViewModel: MainViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this).close()
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        repository = MockNycSchoolRepository(instrumentationContext, nycSchoolApi, nycSchoolDao)
        nycViewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        // Any operations after the test.
    }

    @Test
    fun test_fetch_emitsHighSchoolSuccessResponse() {
        repository.isSuccess = true
        runBlocking {
            val response = repository.getNewyorkSchools()
            Assert.assertTrue(response.data is ArrayList<NycSchool>)
        }
    }

    @Test
    fun test_fetch_emitsHighSchoolScoresSuccessResponse() {
        repository.isSuccess = true
        runBlocking {
            val response = repository.getSatScoresForSchools()
            Assert.assertTrue(response.data is ArrayList<NycSchool>)
        }
    }
}