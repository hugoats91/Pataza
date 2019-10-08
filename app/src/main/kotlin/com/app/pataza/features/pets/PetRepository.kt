package com.app.pataza.features.pets

import com.app.pataza.core.exception.Failure
import com.app.pataza.core.functional.Either
import com.app.pataza.core.platform.NetworkHandler
import com.app.pataza.features.pets.add.Pet
import okhttp3.MultipartBody
import javax.inject.Inject

interface PetRepository {
    fun sendPetPhotos(petId: String, file: List<MultipartBody.Part>): Either<Failure, Boolean>
    fun addPet(name: String, birthdate: String, color: String, size: Double, weight: Double, description: String): Either<Failure, Boolean>

    class Impl
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: PetService) : PetRepository {
        override fun sendPetPhotos(petId: String, file: List<MultipartBody.Part>): Either<Failure, Boolean> {
            return when (networkHandler.isConnected) {
                true -> {
                    val loginResponse = service.uploadPetPhotos(petId, file).execute()
                    when (loginResponse.isSuccessful) {
                        true -> {
                            Either.Right(true)
                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun addPet(name: String, birthdate: String, color: String, size: Double, weight: Double, description: String): Either<Failure, Boolean> {
            return when (networkHandler.isConnected) {
                true -> {
                    val loginResponse = service.addPet(Pet.Request(name, birthdate, color, size, weight, description)).execute()
                    when (loginResponse.isSuccessful) {
                        true -> {
                            Either.Right(true)
                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}