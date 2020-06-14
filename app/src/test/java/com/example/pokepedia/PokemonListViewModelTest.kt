package com.example.pokepedia

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel
import com.example.pokepedia.models.PokemonResponse
import com.example.pokepedia.pokemonList.PokemonListViewModel
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

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class PokemonListViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: PokemonListViewModel

    @Mock
    private lateinit var mockObserver: Observer<PagedList<PokemonModel>>

    @Mock
    private lateinit var service: PokemonService

    private val pokemonResponse = PokemonResponse(
        count = 1,
        nextPageUrl = "",
        previousPageUrl = "",
        pokemonList = listOf(
            PokemonModel(
                name = "pikachu",
                url = "pikachu.com"
            )
        )
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = PokemonListViewModel(service)
    }

    @Test
    fun assertListSize() {
        `when`(service.getPokemonsInfo())
            .thenReturn(Observable.just(pokemonResponse))

        viewModel.childModels.observeForever(mockObserver)

        val value = viewModel.childModels.getOrAwaitValue()
        assertThat(value.size.toString(), `is`("1"))
    }

    @Test
    fun assertListContents() {
        `when`(service.getPokemonsInfo())
            .thenReturn(Observable.just(pokemonResponse))

        viewModel.childModels.observeForever(mockObserver)

        val value = viewModel.childModels.getOrAwaitValue()
        assertThat(value.first().name, `is`("pikachu"))
    }
}