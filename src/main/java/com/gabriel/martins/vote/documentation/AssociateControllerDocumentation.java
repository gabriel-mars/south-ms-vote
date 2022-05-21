package com.gabriel.martins.vote.documentation;

import com.gabriel.martins.vote.dto.AssociateDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface AssociateControllerDocumentation {

    @ApiOperation(value = "Requisição para criação de associados")
    @ApiResponses({ @ApiResponse(code = 201, message = "Associado cadastrado.") })
    ResponseEntity<Void> create(@RequestBody AssociateDto associate);

    @ApiOperation(value = "Requisição para listagem paginada de associados cadastrados")
    @ApiResponses({ @ApiResponse(code = 200, message = "Associados cadastrados.") })
    ResponseEntity<Page<AssociateDto>> getAssociates(@RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                     @RequestParam(name = "sortParameter", defaultValue = "id") String sortParameter,
                                                     @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection);

    @ApiOperation(value = "Requisição para atualização de um associado. Disponível apenas na /v2")
    @ApiResponses({ @ApiResponse(code = 200, message = "Associados atualizado.") })
    ResponseEntity<String> updateAssociate(@PathVariable(value = "id") Long associateId, @RequestBody AssociateDto associateDto);
}
