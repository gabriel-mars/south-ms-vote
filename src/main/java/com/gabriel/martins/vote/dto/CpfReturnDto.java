package com.gabriel.martins.vote.dto;

import com.gabriel.martins.vote.enums.CpfStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "CpfReturnDto", description = "Dados de retorno da consulta para o CPF")
public class CpfReturnDto {

    @ApiModelProperty(notes = "Status", example = "ABLE_TO_VOTE")
    private CpfStatus status;
}
