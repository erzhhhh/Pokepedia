package com.example.pokepedia

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonDetailModel
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
class PokemonDetailsViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonDetailsViewModel

    @Mock
    private lateinit var mockObserver: Observer<PokemonDetailModel>

    @Mock
    private lateinit var mockObserver2: Observer<Boolean>

    @Mock
    private lateinit var service: PokemonService

    private val url = "https://pokeapi.co/api/v2/pokemon/1/"

    private val pokemonDetailModel = PokemonDetailModel(
        name = "pikachu",
        exp = "145",
        height = "1",
        weight = "12"
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        `when`(service.getPokemonDetails(url))
            .thenReturn(Observable.just(pokemonDetailModel))

        viewModel = PokemonDetailsViewModel(service, url)
    }

    @Test
    fun assertModelContents() {
        viewModel.childModel.observeForever(mockObserver)

        val value = viewModel.childModel.getOrAwaitValue()

        assertThat(value.name, `is`("pikachu"))
    }

    @Test
    fun assertModelIsLoading() {
        viewModel.isLoading.observeForever(mockObserver2)

        val value = viewModel.isLoading.getOrAwaitValue()

        assertThat(value, `is`(false))
    }
}