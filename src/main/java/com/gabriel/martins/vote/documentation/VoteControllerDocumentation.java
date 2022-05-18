package com.gabriel.martins.vote.documentation;

import com.gabriel.martins.vote.dto.VoteDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface VoteControllerDocumentation {

    @ApiOperation(value = "Requisição para registro do voto")
    @ApiResponses({ @ApiResponse(code = 201, message = "Voto cadastrado.") })
    ResponseEntity<Void> create(@RequestBody VoteDto vote);
}
