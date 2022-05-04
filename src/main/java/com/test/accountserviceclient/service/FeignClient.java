package com.test.accountserviceclient.service;

import com.test.accountserviceclient.model.AccountDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@org.springframework.cloud.openfeign.FeignClient(value = "feignClient", url = "${account-service-url}")
public interface FeignClient {

    @GetMapping("/{id}")
    Long getAmount(@PathVariable Integer id);

    @PostMapping
    void addAmount(AccountDto accountDto);

}
