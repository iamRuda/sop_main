package com.example.springdatabasicdemo.contracts;

import com.example.springdatabasicdemo.dtos.InspectorDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Проверяющий(inspector)")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
@RequestMapping("/inspectors")
public interface InspectorApi {

    @Operation(summary = "Создать инспектора")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectorDTO> createInspector(@RequestBody InspectorDTO newInspector);

    @Operation(summary = "Получить инспектора по ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectorDTO> getInspectorById(@PathVariable Long id);

    @Operation(summary = "Получить всех инспекторов")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<InspectorDTO> getAllInspectors();

    @Operation(summary = "Обновить инспектора по ID")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<InspectorDTO> updateInspector(@PathVariable Long id, @RequestBody InspectorDTO updatedInspector);

    @Operation(summary = "Удалить инспектора по ID")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteInspector(@PathVariable Long id);
}
