package com.mjob.picturegallery.interactor

import androidx.paging.DataSource
import com.mjob.picturegallery.helpers.TestCoroutineContextProvider
import com.mjob.picturegallery.helpers.TestCoroutinesRule
import com.mjob.picturegallery.interactor.PictureInteractor
import com.mjob.picturegallery.repository.api.PictureApiRepository
import com.mjob.picturegallery.repository.api.model.Picture
import com.mjob.picturegallery.repository.data.PictureDataRepository
import com.mjob.picturegallery.repository.data.model.DatabaseStatus
import com.mjob.picturegallery.repository.data.model.DatabaseStatusCode
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call


class PictureInteractorTest {
    @Mock
    lateinit var mockPictureApiRepository: PictureApiRepository

    @Mock
    lateinit var mockPictureDataRepository: PictureDataRepository

    @Mock
    lateinit var mockDataSource: DataSource.Factory<Int, Int>

    private lateinit var testCoroutineContextProvider: TestCoroutineContextProvider

    private lateinit var pictureInteractor: PictureInteractor


    @get:Rule
    val testCoroutinesRule = TestCoroutinesRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testCoroutineContextProvider = TestCoroutineContextProvider()
        pictureInteractor = PictureInteractor(
            mockPictureDataRepository,
            mockPictureApiRepository,
            testCoroutineContextProvider
        )
    }

    @Test
    fun given_a_database_successfully_filled_when_querying_for_album_then_return_a_non_empty_data_source() {
        testCoroutinesRule.runBlocking {

            val databaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.SUCCESS)
            Mockito.`when`(mockPictureDataRepository.getDatabaseStatus())
                .thenReturn(databaseStatus)

            Mockito.`when`(mockPictureDataRepository.getAlbums())
                .thenReturn(mockDataSource)

            val albums = pictureInteractor.getAlbums()

            Mockito.verify(mockPictureApiRepository, Mockito.times(0)).get()
            assert(albums != null)
        }
    }

    @Test
    fun given_a_database_unsuccessfully_filled_when_querying_for_album_pictures_are_not_empty_then_download_and_return_a_non_empty_data_source() {
        testCoroutinesRule.runBlocking {

            val initialDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.Failure)
            val finalDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.SUCCESS)
            val pictures = listOf<Picture>(Picture(1,1,"photo","www.google.fr","www.google.fr"))

            Mockito.`when`(mockPictureDataRepository.getDatabaseStatus())
                .thenReturn(initialDatabaseStatus)

            Mockito.`when`(mockPictureApiRepository.get())
                .thenReturn(pictures)

            Mockito.`when`(mockPictureDataRepository.getAlbums())
                .thenReturn(mockDataSource)

            val albums = pictureInteractor.getAlbums()

            Mockito.verify(mockPictureApiRepository).get()
            Mockito.verify(mockPictureDataRepository).insert(pictures)
            Mockito.verify(mockPictureDataRepository).setDatabaseStatus(finalDatabaseStatus)
            assert(albums != null)
        }

    }

    @Test
    fun given_a_database_unsuccessfully_filled_when_querying_for_album_and_pictures_are_empty_return_a_null_data_source() {
        testCoroutinesRule.runBlocking {

            val failingDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.Failure)
            val successfulDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.SUCCESS)
            val pictures = listOf<Picture>()

            Mockito.`when`(mockPictureDataRepository.getDatabaseStatus())
                .thenReturn(failingDatabaseStatus)

            Mockito.`when`(mockPictureApiRepository.get())
                .thenReturn(pictures)

            Mockito.`when`(mockPictureDataRepository.getAlbums())
                .thenReturn(null)

            val albums = pictureInteractor.getAlbums()

            Mockito.verify(mockPictureApiRepository).get()
            Mockito.verify(mockPictureDataRepository, Mockito.times(0)).insert(pictures)
            Mockito.verify(mockPictureDataRepository).setDatabaseStatus(failingDatabaseStatus)
            Mockito.verify(
                mockPictureDataRepository,
                Mockito.times(0)
            ).setDatabaseStatus(successfulDatabaseStatus)
            assert(albums == null)
        }

    }

    @Test
    fun given_a_database_unsuccessfully_filled_when_querying_for_album_and_download_failed_then_return_a_null_data_source() {
        testCoroutinesRule.runBlocking {

            val failingDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.Failure)
            val successfulDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.SUCCESS)
            val pictures = listOf<Picture>()

            Mockito.`when`(mockPictureDataRepository.getDatabaseStatus())
                .thenReturn(failingDatabaseStatus)

            Mockito.`when`(mockPictureDataRepository.getAlbums())
                .thenReturn(null)

            val exception = Exception()
            Mockito.`when`(mockPictureApiRepository.get())
                .thenThrow(exception)

            val albums = pictureInteractor.getAlbums()

            Mockito.verify(mockPictureApiRepository).get()
            Mockito.verify(mockPictureDataRepository, Mockito.times(0)).insert(pictures)
            Mockito.verify(mockPictureDataRepository).setDatabaseStatus(failingDatabaseStatus)
            Mockito.verify(
                mockPictureDataRepository,
                Mockito.times(0)
            ).setDatabaseStatus(successfulDatabaseStatus)
            assert(albums == null)
        }

    }

    @Test
    fun given_a_database_non_filled_yet_when_querying_for_album_and_picture_are_not_empty_then_download_and_return_a_non_empty_data_source() {
        testCoroutinesRule.runBlocking {

            val finalDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.SUCCESS)
            val pictures = listOf<Picture>(Picture(1,1,"photo","www.google.fr","www.google.fr"))

            Mockito.`when`(mockPictureDataRepository.getDatabaseStatus())
                .thenReturn(null)

            Mockito.`when`(mockPictureApiRepository.get())
                .thenReturn(pictures)

            Mockito.`when`(mockPictureDataRepository.getAlbums())
                .thenReturn(mockDataSource)

            val albums = pictureInteractor.getAlbums()

            Mockito.verify(mockPictureApiRepository).get()
            Mockito.verify(mockPictureDataRepository).insert(pictures)
            Mockito.verify(mockPictureDataRepository).setDatabaseStatus(finalDatabaseStatus)
            assert(albums != null)
        }

    }

    @Test
    fun given_a_database_non_filled_yet_when_querying_for_album_and_picture_are_empty_then_download_and_return_a_non_empty_data_source() {
        testCoroutinesRule.runBlocking {

            val failingDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.Failure)
            val successfulDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.SUCCESS)
            val pictures = listOf<Picture>()

            Mockito.`when`(mockPictureDataRepository.getDatabaseStatus())
                .thenReturn(null)

            Mockito.`when`(mockPictureApiRepository.get())
                .thenReturn(pictures)

            Mockito.`when`(mockPictureDataRepository.getAlbums())
                .thenReturn(null)

            val albums = pictureInteractor.getAlbums()

            Mockito.verify(mockPictureApiRepository).get()
            Mockito.verify(mockPictureDataRepository, Mockito.times(0)).insert(pictures)
            Mockito.verify(mockPictureDataRepository).setDatabaseStatus(failingDatabaseStatus)
            Mockito.verify(
                mockPictureDataRepository,
                Mockito.times(0)
            ).setDatabaseStatus(successfulDatabaseStatus)
            assert(albums == null)
        }

    }

    @Test
    fun given_a_database_non_filled_yet_when_querying_for_album_and_download_failed_then_return_a_null_data_source() {
        testCoroutinesRule.runBlocking {
            val failingDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.Failure)
            val successfulDatabaseStatus = DatabaseStatus(statusCode = DatabaseStatusCode.SUCCESS)
            val pictures = listOf<Picture>()

            Mockito.`when`(mockPictureDataRepository.getDatabaseStatus())
                .thenReturn(null)

            Mockito.`when`(mockPictureDataRepository.getAlbums())
                .thenReturn(null)

            val exception = Exception()
            Mockito.`when`(mockPictureApiRepository.get())
                .thenThrow(exception)

            val albums = pictureInteractor.getAlbums()

            Mockito.verify(mockPictureApiRepository).get()
            Mockito.verify(mockPictureDataRepository, Mockito.times(0)).insert(pictures)
            Mockito.verify(mockPictureDataRepository).setDatabaseStatus(failingDatabaseStatus)
            Mockito.verify(
                mockPictureDataRepository,
                Mockito.times(0)
            ).setDatabaseStatus(successfulDatabaseStatus)
            assert(albums == null)
        }

    }


}

