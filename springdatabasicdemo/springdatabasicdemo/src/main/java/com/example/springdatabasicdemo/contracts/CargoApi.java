package com.example.springdatabasicdemo.contracts;

import com.example.springdatabasicdemo.dtos.CargoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Груз(cargo)")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешная обработка запроса"),
        @ApiResponse(responseCode = "400", description = "Ошибка валидации"),
        @ApiResponse(responseCode = "404", description = "Ресурс не найден"),
        @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
})
@RequestMapping("/cargos")
public
interface CargoApi {

    @Operation(summary = "Создать груз")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CargoDTO> createCargo(@RequestBody CargoDTO newCargo);

    @Operation(summary = "Получить груз по ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CargoDTO> getCargoById(@PathVariable Long id);

    @Operation(summary = "Получить все грузы")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<CargoDTO> getAllCargos();

    @Operation(summary = "Обновить груз по ID")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CargoDTO> updateCargo(@PathVariable Long id, @RequestBody CargoDTO updatedCargo);

    @Operation(summary = "Удалить груз по ID")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> deleteCargo(@PathVariable Long id);
}
