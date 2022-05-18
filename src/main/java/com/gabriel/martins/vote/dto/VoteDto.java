package com.gabriel.martins.vote.dto;

import com.gabriel.martins.vote.enums.VoteType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "VoteDto", description = "Dados de envio para computação do voto")
public class VoteDto {

    @ApiModelProperty(notes = "Identificador do associado votante", example = "123.456.789-00")
    private Long associateID;

    @ApiModelProperty(notes = "Voto", example = "SIM")
    private VoteType voteType;

    @ApiModelProperty(notes = "Identificador da pauta", example = "1")
    private Long assemblyID;
}
