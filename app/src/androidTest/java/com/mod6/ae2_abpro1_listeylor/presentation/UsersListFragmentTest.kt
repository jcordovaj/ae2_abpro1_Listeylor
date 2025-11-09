package com.mod6.ae2_abpro1_listeylor.presentation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mod6.ae2_abpro1_listeylor.R
import org.hamcrest.Matchers.anyOf
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UsersListFragmentTest {

    @Test
    fun fragment_se_inicia_y_muestra_vistas_basicas() {
        // Lanza el fragmento en un contenedor con el tema de la app
        launchFragmentInContainer<UsersListFragment>(
            themeResId = R.style.Theme_Ae2_abpro1_Listeylor
        )

        // Verifica que el RecyclerView existe y la visibilidad es válida
        onView(withId(R.id.rv_users))
            .check(
                matches(
                    anyOf(
                        withEffectiveVisibility(Visibility.VISIBLE),
                        withEffectiveVisibility(Visibility.INVISIBLE),
                        withEffectiveVisibility(Visibility.GONE)
                    )
                )
            )

        // Verifica que el ProgressBar existe y no produce errores de visibilidad
        onView(withId(R.id.progress_bar))
            .check(
                matches(
                    anyOf(
                        withEffectiveVisibility(Visibility.VISIBLE),
                        withEffectiveVisibility(Visibility.GONE)
                    )
                )
            )

        // Verifica que el TextView de error está oculto inicialmente
        onView(withId(R.id.tv_error))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}