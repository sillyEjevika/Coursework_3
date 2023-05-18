package Ejevika.socksstore.controllers;

import Ejevika.socksstore.models.Socks;
import Ejevika.socksstore.models.enums.Size;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Ejevika.socksstore.models.enums.Color;
import Ejevika.socksstore.services.SocksService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/socks")
@Tag(name = "Носки", description = "CRUD-методы для работы с носками")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService) {
        this.socksService = socksService;
    }

    @PostMapping()
    @Operation(
            summary = "Добавление носков на склад",
            description = "color available values: RED, GREEN, BLUE, YELLOW, ORANGE, PURPLE, BROWN, PINK, VIOLET, GREY, BLACK, WHITE<br>" +
                    "size available values: S, M, L, XL, XXL, XXXL<br>" +
                    "cottonRel range from 1 to 100"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось добавить приход",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Socks.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны",
                    content = @Content()
            )
    })
    public ResponseEntity<Socks> addSocks(@Valid @RequestBody Socks socks){
        return ResponseEntity.ok(socksService.addSocks(socks));

    }

    @GetMapping("all")
    @Operation(
            summary = "Получение всех носков"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Запрос выполнен, носки получены",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Socks.class))
            )
    )
    public ResponseEntity<List<Socks>> getAllSocks(){
        return ResponseEntity.ok(socksService.getAllSocks());
    }

    @GetMapping()
    @Operation(
            summary = "Получение кол-ва носков на складе по фильтрам"
    )
    @Parameters(value = {
            @Parameter(
                    name = "color",
                    schema = @Schema(implementation = Color.class)
            ),
            @Parameter(
                    name = "size",
                    schema = @Schema(implementation = Size.class)
            ),
            @Parameter(
                    name = "cottonMin",
                    description = "in range from 1 to 100"
            ),
            @Parameter(
                    name = "cottonMax",
                    description = "in range from 1 to 100"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, результат в теле ответа в виде целого числа",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = {@ExampleObject(value = "20")}
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны",
                    content = @Content()
            )
    })
    public ResponseEntity<Integer> getSocks(@RequestParam Color color,
                                            @RequestParam Size size,
                                            @RequestParam(defaultValue = "1") int cottonMin,
                                            @RequestParam(defaultValue = "100") int cottonMax){
        Integer quantity = socksService.getSocks(color, size, cottonMin, cottonMax);
        return ResponseEntity.ok(quantity);
    }

    @PutMapping()
    @Operation(
            summary = "Отпуск носков со склада"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Удалось произвести отпуск носков со склада",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Socks.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Товара нет на складе в нужном количестве или параметры запроса имеют некорректный формат",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны",
                    content = @Content()
            )
    })
    public ResponseEntity<Socks> updateSocks(@Valid @RequestBody Socks socks){
        if (socksService.updateSocks(socks)){
            return ResponseEntity.ok(socks);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping()
    @Operation(
            summary = "Списание носков со склада"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Запрос выполнен, товар списан со склада",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Socks.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Параметры запроса отсутствуют или имеют некорректный формат",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Произошла ошибка, не зависящая от вызывающей стороны",
                    content = @Content()
            )
    })
    public ResponseEntity<Socks> removeSocks(@RequestBody Socks socks){
        if (socksService.removeSocks(socks)){
            return ResponseEntity.ok(socks);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
