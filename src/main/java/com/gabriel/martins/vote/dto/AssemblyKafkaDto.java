package com.gabriel.martins.vote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gabriel.martins.vote.enums.AssemblyStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@ApiModel(value = "AssemblyKafkaDto", description = "Dados de envio da pauta fechada para o Kafka")
public class AssemblyKafkaDto {

    @ApiModelProperty(notes = "Id da pauta que foi fechada", example = "1")
    private Long idClosedAssembly;

    @ApiModelProperty(notes = "Descrição da pauta", example = "Pauta para mudança do síndico do prédio XPTO")
    private String description;

    @ApiModelProperty(notes = "Status final da pauta", example = "Indica o status final de uma pauta")
    private AssemblyStatus status;

    @ApiModelProperty(notes = "Data de início da votação", example = "2022-05-18")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startingDate;

    @ApiModelProperty(notes = "Data de finalização da votação", example = "2022-05-31")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endingDate;
}
