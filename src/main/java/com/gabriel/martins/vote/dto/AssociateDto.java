package com.gabriel.martins.vote.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "AssociateDto", description = "Dados de registro do associado")
public class AssociateDto {

    @ApiModelProperty(notes = "Documento(CPF) do associado", example = "123.456.789-00")
    private String cpf;

    @ApiModelProperty(notes = "Nome completo do associado", example = "Fulano da Silva Rodrigues")
    private String name;
}
