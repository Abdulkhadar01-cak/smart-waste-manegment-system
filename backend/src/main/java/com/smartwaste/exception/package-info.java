/**
 * Exception Handling Layer for the Smart Waste Management System.
 * 
 * Purpose:
 * Provides centralized global error handling using @ControllerAdvice / @RestControllerAdvice,
 * custom domain exceptions, and standard error payload structures.
 * Ensures friendly, descriptive error messages for end users and uniform API responses.
 * 
 * Typical components:
 * - GlobalExceptionHandler
 * - ResourceNotFoundException
 * - BinCapacityExceededException
 * - InvalidSensorPayloadException
 * - ErrorResponseDto
 */
package com.smartwaste.exception;
