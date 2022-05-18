package com.gabriel.martins.vote.converter;

import com.gabriel.martins.vote.dto.AssemblyDto;
import com.gabriel.martins.vote.entity.AssemblyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface AssemblyConverter {

    AssemblyDto convertToDto(AssemblyEntity entity);

    AssemblyEntity convertToEntity(AssemblyDto dto);
}
