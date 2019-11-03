package com.app.pataza.features.pets.add

import com.app.pataza.core.interactor.UseCase
import com.app.pataza.data.models.Resource
import com.app.pataza.data.resource.ResourceRepository
import javax.inject.Inject

class GetResources
@Inject constructor(private val resourceRepository: ResourceRepository) : UseCase<Resource, UseCase.None>() {

    override suspend fun run(params: None) = resourceRepository.resources()
}