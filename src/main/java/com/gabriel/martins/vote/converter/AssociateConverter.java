package com.gabriel.martins.vote.converter;

import com.gabriel.martins.vote.dto.AssociateDto;
import com.gabriel.martins.vote.entity.AssociateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface AssociateConverter {

    AssociateDto convertToDto(AssociateEntity entity);

    AssociateEntity convertToEntity(AssociateDto dto);
}
