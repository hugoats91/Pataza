package com.app.pataza.data.resource

import com.app.pataza.PatazaApp
import com.app.pataza.core.exception.Failure
import com.app.pataza.core.functional.Either
import com.app.pataza.data.models.Resource
import javax.inject.Inject

interface ResourceRepository {
    fun resources(): Either<Failure, Resource>

    class Impl
    @Inject constructor() : ResourceRepository {
        override fun resources(): Either<Failure, Resource> {
            return Either.Right(Resource(PatazaApp.database.resourceDao().getCountries().map { it.toCountry() },
                    PatazaApp.database.resourceDao().getRaces().map { it.toRace() },
                    PatazaApp.database.resourceDao().getSizes().map { it.toSize() },
                    PatazaApp.database.resourceDao().getDiseases().map { it.toDisease() },
                    PatazaApp.database.resourceDao().getVaccines().map { it.toVaccine() },
                    PatazaApp.database.resourceDao().getQualities().map { it.toQuality() }))
        }


    }
}