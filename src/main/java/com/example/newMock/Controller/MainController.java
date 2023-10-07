package com.example.newMock.Controller;

import com.example.newMock.Model.RequestDTO;
import com.example.newMock.Model.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Random;

@RestController
public class MainController {
    private Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    // метод получает наш запрос и отдает нам ответ
    @PostMapping (
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )


    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            String RqUID = requestDTO.getRqUID();
            String clientId = requestDTO.getClientId();
            //распарсим номер и вытащим первую цифру
            char firstDigit = clientId.charAt(0);
            BigDecimal maxLimit;
            BigDecimal balance;
            String currency;

            if (firstDigit == '8') {
                maxLimit = new BigDecimal(2000);
                balance = maxLimit.multiply(BigDecimal.valueOf(Math.random())).setScale(2, BigDecimal.ROUND_HALF_UP);
                currency = "US";


            }else  if (firstDigit == '9') {
                maxLimit = new BigDecimal(1000);
                balance = maxLimit.multiply(BigDecimal.valueOf(Math.random())).setScale(2, BigDecimal.ROUND_HALF_UP);
                currency = "EU";
            }else {
                maxLimit = new BigDecimal(10000);
                balance = maxLimit.multiply(BigDecimal.valueOf(Math.random())).setScale(2, BigDecimal.ROUND_HALF_UP);
                currency = "RUB";
            }
            ResponseDTO responseDTO = new ResponseDTO();

            responseDTO.setRqUID(RqUID);
            responseDTO.setClientId(clientId);
            responseDTO.setAccount(requestDTO.getAccount());
            responseDTO.setCurrency(currency);
            responseDTO.setBalance(balance);
            responseDTO.setMaxLimit(maxLimit);

            log.error("********** LimitRequestDTO **********"
            + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.error("********** LimitResponseDTO **********"
                    + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responseDTO));
            return responseDTO;


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());


        }
    }


}
