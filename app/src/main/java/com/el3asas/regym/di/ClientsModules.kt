package com.el3asas.regym.di

import com.el3asas.regym.clients.ProfileClientService
import org.koin.dsl.module

val ClientsModules = module {
    single { ProfileClientService(get()) }
}