package com.gabriel.martins.vote.feign;

import com.gabriel.martins.vote.dto.CpfReturnDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cpf", url = "https://user-info.herokuapp.com/users")
@Component
public interface ValidateCpf {

    @GetMapping("/{cpf}")
    ResponseEntity<CpfReturnDto> isValidCpf(@PathVariable(value = "cpf") String cpf);
}
