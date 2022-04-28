package com.el3asas.regym.di

import com.el3asas.regym.ui.profile.ProfileRepository
import org.koin.dsl.module

val RepositoriesModules = module {
    single { ProfileRepository(get()) }
}