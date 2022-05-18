package com.gabriel.martins.vote.documentation;

import com.gabriel.martins.vote.dto.AssemblyDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AssemblyControllerDocumentation {

    @ApiOperation(value = "Requisição para criação de pautas")
    @ApiResponses({ @ApiResponse(code = 201, message = "Pauta cadastrada.") })
    ResponseEntity<Void> create(@RequestBody AssemblyDto assembly);
}
