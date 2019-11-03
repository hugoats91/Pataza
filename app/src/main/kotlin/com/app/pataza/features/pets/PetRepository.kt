package com.app.pataza.features.pets

import com.app.pataza.core.exception.Failure
import com.app.pataza.core.functional.Either
import com.app.pataza.core.platform.NetworkHandler
import com.app.pataza.data.models.Pet
import com.app.pataza.features.pets.add.AddPetRemote
import okhttp3.MultipartBody
import java.lang.Exception
import javax.inject.Inject

interface PetRepository {
    fun sendPetPhotos(petId: String, file: List<MultipartBody.Part>): Either<Failure, Boolean>
    fun addPet(name: String,
               monthsAge: Int?,
               yearsAge: Int?,
               race: Int,
               color: List<String>,
               gender: Int,
               size: Int,
               weight: Double?,
               description: String,
               vaccines: List<Int>?,
               qualities: List<Int>?,
               diseases: List<Int>?,
               additionalRequirements: List<Int>?): Either<Failure, String>
    fun petsByUser(): Either<Failure, List<Pet>>

    class Impl
    @Inject constructor(private val networkHandler: NetworkHandler,
                        private val service: PetService) : PetRepository {
        override fun petsByUser(): Either<Failure, List<Pet>> {
            return when (networkHandler.isConnected) {
                true -> {
                    try {
                        val petResponse = service.petsByUser().execute()
                        when (petResponse.isSuccessful) {
                            true -> {
                                petResponse.body()?.data?.pets?.let {
                                    Either.Right(it)
                                }?: Either.Left(Failure.ServerError)

                            }
                            false -> Either.Left(Failure.ServerError)
                        }
                    }catch (e: Exception){
                        Either.Left(Failure.ServerError)
                    }

                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

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

        override fun addPet(name: String, monthsAge: Int?, yearsAge: Int?, race: Int, color: List<String>, gender: Int, size: Int, weight: Double?, description: String, vaccines: List<Int>?, qualities: List<Int>?, diseases: List<Int>?, additionalRequirements: List<Int>?): Either<Failure, String> {
            return when (networkHandler.isConnected) {
                true -> {
                    val loginResponse = service.addPet(AddPetRemote.Request(name, monthsAge, yearsAge, race, color, gender, size, weight, description, vaccines, qualities, diseases, additionalRequirements)).execute()
                    when (loginResponse.isSuccessful) {
                        true -> {
                            loginResponse.body()?.data?.pet?.let {
                                Either.Right(it.id)
                            }?: Either.Left(Failure.ServerError)

                        }
                        false -> Either.Left(Failure.ServerError)
                    }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}