package com.iamruda.contractfirst.controllers;

import com.iamruda.contractfirst.dtos.InspectedPersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Проверяемый(inspectedPerson)")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface InspectedApi {

    @Operation(summary = "Создать проверяемого человека")
    @PostMapping(value = "/inspected", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectedPersonDTO> createInspectedPerson(@Valid @RequestBody InspectedPersonDTO newInspectedPerson);

    @Operation(summary = "Получить информацию о проверяемом человеке по ID")
    @GetMapping(value = "/inspected/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectedPersonDTO> getInspectedPersonById(@PathVariable("id") Long id);

    @Operation(summary = "Получить список всех проверяемых людей")
    @GetMapping(value = "/inspected", produces = MediaType.APPLICATION_JSON_VALUE)
    List<InspectedPersonDTO> getAllInspectedPersons();

    @Operation(summary = "Обновить информацию о проверяемом человеке")
    @PutMapping(value = "/inspected/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectedPersonDTO> updateInspectedPerson(@PathVariable("id") Long id, @Valid @RequestBody InspectedPersonDTO updatedInspectedPerson);

    @Operation(summary = "Удалить проверяемого человека по ID")
    @DeleteMapping(value = "/inspected/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteInspectedPerson(@PathVariable("id") Long id);

    @Operation(summary = "Отправить гражданина в очередь")
    @GetMapping(value = "/sendToQueue/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> sendInspectedToQueue(@PathVariable Long id);
}
