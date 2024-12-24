package com.iamruda.contractfirst.controllers;

import com.iamruda.contractfirst.dtos.InspectionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Проверка(inspection)")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface InspectionApi {

    @Operation(summary = "Создать проверку")
    @PostMapping(value = "/inspection", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectionDTO> createInspection(@Valid @RequestBody InspectionDTO newInspectionPerson);

    @Operation(summary = "Получить информацию о проверке ID")
    @GetMapping(value = "/inspection/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectionDTO> getInspectionById(@PathVariable("id") Long id);

    @Operation(summary = "Получить список всех проверок")
    @GetMapping(value = "/inspection", produces = MediaType.APPLICATION_JSON_VALUE)
    List<InspectionDTO> getAllInspections();

    @Operation(summary = "Обновить информацию о проверке")
    @PutMapping(value = "/inspection/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectionDTO> updateInspection(@PathVariable("id") Long id, @Valid @RequestBody InspectionDTO updatedInspectionPerson);

    @Operation(summary = "Удалить проверку")
    @DeleteMapping(value = "/inspection/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteInspection(@PathVariable("id") Long id);

    @Operation(summary = "Отправить проверку в очередь")
    @GetMapping(value = "/sendToQueue/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> sendInspectionToQueue(@PathVariable Long id);
}
