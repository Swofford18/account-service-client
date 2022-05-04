package com.test.accountserviceclient.service;

import com.test.accountserviceclient.model.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final FeignClient feignClient;

    @Value("${rCount}")
    private Integer rCount;

    @Value("${wCount}")
    private Integer wCount;

    @Value("#{'${idList}'.split(',')}")
    private List<Integer> idList;

    @Value("${minValue}")
    private Integer minValue;

    @Value("${maxValue}")
    private Integer maxValue;

    public void startClient() {

        for (int i = 0; i < rCount; i++) {
            Thread clientThread = new Thread(new Reader());
            clientThread.start();
        }
        for (int i = 0; i < wCount; i++) {
            Thread clientThread = new Thread(new Writer());
            clientThread.start();
        }

        System.out.println("___________________________");
    }

    private class Reader implements Runnable {
        @Override
        public void run() {
            var id = idList.get(ThreadLocalRandom.current().nextInt(idList.size()));
            var amount = feignClient.getAmount(id);
            System.out.println("getAmount: id -> " + id + " | amount -> " + amount);
        }
    }

    private class Writer implements  Runnable {
        @Override
        public void run() {
            var id = idList.get(ThreadLocalRandom.current().nextInt(idList.size()));
            var value = ThreadLocalRandom.current().nextInt(minValue, maxValue);
            feignClient.addAmount(AccountDto.builder()
                            .id(id)
                            .value((long) value)
                    .build());
            System.out.println("addAmount: id -> " + id + " | value -> " + value);
        }
    }
}
