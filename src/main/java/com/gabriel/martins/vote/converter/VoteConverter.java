package com.gabriel.martins.vote.converter;

import com.gabriel.martins.vote.dto.VoteDto;
import com.gabriel.martins.vote.entity.VoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface VoteConverter {

    VoteDto convertToDto(VoteEntity entity);

    VoteEntity convertToEntity(VoteDto dto);
}
