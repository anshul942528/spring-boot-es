package com.ansh.controller;

import com.ansh.dto.RequestDTO;
import com.ansh.dto.RequestData;
import com.ansh.dto.ResponseDTO;
import com.ansh.dto.ResponseData;
import com.ansh.service.BasicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@Slf4j
public class BasicController {

    @Autowired
    private BasicService basicService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseDTO<ResponseData> getOrder(@PathVariable("id") int id) throws IOException {
        log.info("Basic controller : getRequest method");
         return basicService.getMethod(id);
    }

    @RequestMapping(value = "/add", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity addOrder(@RequestBody RequestDTO<RequestData> requestDTO) throws IOException {
        log.info("Basic controller : putRequest method");
        return new ResponseEntity(basicService.putMethod(requestDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity updateOrder(@RequestBody RequestDTO<RequestData> requestDTO) throws IOException {
        log.info("Basic controller : postRequest method");
        return new ResponseEntity(basicService.postMethod(requestDTO), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteOrder(@PathVariable("id") int id) throws IOException {
        log.info("Basic controller : deleteRequest method");
        return basicService.deleteMethod(id);
    }
}
