package com.gabriel.martins.vote.converter;

import com.gabriel.martins.vote.dto.AssemblyDto;
import com.gabriel.martins.vote.dto.AssemblyKafkaDto;
import com.gabriel.martins.vote.entity.AssemblyEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring")
public interface AssemblyConverter {

    AssemblyDto convertToDto(AssemblyEntity entity);

    AssemblyEntity convertToEntity(AssemblyDto dto);

    @Mapping(target = "idClosedAssembly", source = "id")
    AssemblyKafkaDto convertToKafkaDto(AssemblyEntity entity);
}
