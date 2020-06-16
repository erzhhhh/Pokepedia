package com.example.pokepedia

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.pokemonDetails.PokemonDetailsViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode


@RunWith(CustomRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
@LooperMode(LooperMode.Mode.PAUSED)
class PokemonDetailsErrorViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonDetailsViewModel

    @Mock
    private lateinit var mockObserver2: Observer<Boolean>

    @Mock
    private lateinit var mockObserver3: Observer<PokemonDetailsViewModel.PokemonError>

    @Mock
    private lateinit var service: PokemonService

    private val url = "https://pokeapi.co/api/v2/pokemon/1/"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        `when`(service.getPokemonDetails(url))
            .thenReturn(Observable.error(Exception()))

        viewModel = PokemonDetailsViewModel(service, url)
    }

    @Test
    fun assertModelIsError() {
        viewModel.isLoading.observeForever(mockObserver2)
        viewModel.isError.observeForever(mockObserver3)

        val isLoading = viewModel.isLoading.getOrAwaitValue()
        val isError = viewModel.isError.getOrAwaitValue()

        assertThat(isLoading, `is`(false))
        assertThat(isError.isError, `is`(true))
    }
}