package com.example.wordValidator.domain

interface IWordValidator {
    fun getValidationResult(validationData: ValidationContainer) : MutableList<Boolean>
}