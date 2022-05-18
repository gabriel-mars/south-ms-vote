package com.gabriel.martins.vote.documentation;

import com.gabriel.martins.vote.dto.AssemblyDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface AssemblyControllerDocumentation {

    @ApiOperation(value = "Requisição para criação de pautas")
    @ApiResponses({ @ApiResponse(code = 201, message = "Pauta cadastrada.") })
    ResponseEntity<Void> create(@RequestBody AssemblyDto assembly);

    @ApiOperation(value = "Requisição para fechamento de pautas abertas ou em votação")
    @ApiResponses({ @ApiResponse(code = 200, message = "Pauta fechada.") })
    ResponseEntity<Void> closeAssembly(@PathVariable(value = "id") Long assemblyId);

    @ApiOperation(value = "Requisição para abertura de pautas aguardando início")
    @ApiResponses({ @ApiResponse(code = 200, message = "Pauta aberta.") })
    ResponseEntity<Void> openAssembly(@PathVariable(value = "id") Long assemblyId);
}
