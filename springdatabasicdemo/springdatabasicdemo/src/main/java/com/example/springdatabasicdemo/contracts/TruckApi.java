package com.example.springdatabasicdemo.contracts;

import com.example.springdatabasicdemo.dtos.InspectorDTO;
import com.example.springdatabasicdemo.dtos.TruckDTO;
import com.example.springdatabasicdemo.dtos.TruckHALDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Грузовики(trucks)")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
public interface TruckApi {

    @Operation(summary = "Создать грузовик")
    @PostMapping(value = "/trucks", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TruckDTO> createTruck(@Valid @RequestBody TruckDTO truckRequest);

    @Operation(summary = "Получить информацию о грузовике по ID")
    @GetMapping(value = "/trucks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TruckDTO> getTruckById(@PathVariable("id") Long id);

    @Operation(summary = "Получить все грузовики")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<TruckDTO> getAllTrucks();

    @Operation(summary = "Обновить информацию о грузовике")
    @PutMapping(value = "/trucks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TruckDTO> updateTruck(@PathVariable("id") Long id, @Valid @RequestBody TruckDTO truckRequest);

    @Operation(summary = "Удалить грузовик по ID")
    @DeleteMapping(value = "/trucks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteTruck(@PathVariable("id") Long id);

    @Operation(summary = "Грузовик отправить в очередь")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> sendTruckToQueue(@PathVariable Long id);
}
