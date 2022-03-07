package com.example.entities.interfaces

import entities.ValidationContainer

interface IWordValidator {
    fun getValidationResult(validationData: ValidationContainer) : MutableList<Boolean>
}