package com.gabriel.martins.vote.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
@ApiModel(value = "AssemblyDto", description = "Dados de envio para registro da pauta")
public class AssemblyDto {

    @ApiModelProperty(notes = "Id da pauta", example = "1")
    private Long id;

    @ApiModelProperty(notes = "Descrição da pauta", example = "Pauta para mudança do síndico do prédio XPTO")
    private String description;

    @ApiModelProperty(notes = "Data de início da votação", example = "2022-05-18")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startingDate;

    @ApiModelProperty(notes = "Data de finalização da votação", example = "2022-05-31")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endingDate;
}
