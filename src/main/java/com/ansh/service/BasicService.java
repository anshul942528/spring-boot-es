package com.ansh.service;

import com.ansh.constant.ESConstants;
import com.ansh.dto.RequestDTO;
import com.ansh.dto.RequestData;
import com.ansh.dto.ResponseDTO;
import com.ansh.dto.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class BasicService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseDTO<ResponseData> getMethod(int id) throws IOException {
        log.info("Basic service : getMethod method : " + id);
        GetRequest getRequest = new GetRequest(ESConstants.INDEX);
        getRequest.id(id+"");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> resultMap = getResponse.getSource();
        ResponseData responseData = objectMapper.convertValue(resultMap, ResponseData.class);
        ResponseDTO<ResponseData> responseDTO = new ResponseDTO<>();
        responseDTO.setMetaData("success");
        responseDTO.setData(responseData);
        return responseDTO;
    }

    public String postMethod(RequestDTO<RequestData> requestDTO) throws IOException {
        log.info("Basic service : postMethod method");
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(ESConstants.INDEX);
        updateRequest.id(requestDTO.getData().getOrderId()+"");
        Map<String, Object> documentMapper = objectMapper.convertValue(requestDTO.getData(), Map.class);
        updateRequest.doc(documentMapper);
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return updateResponse.getResult().name();
    }

    public String putMethod(RequestDTO<RequestData> requestDTO) throws IOException {
        log.info("Basic service : putMethod method");
        Map<String, Object> documentMapper = objectMapper.convertValue(requestDTO.getData(), Map.class);
        IndexRequest indexRequest = new IndexRequest(ESConstants.INDEX).source(documentMapper);
        indexRequest.id(requestDTO.getData().getOrderId()+"");
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("res : " + indexResponse.getResult());
        return indexResponse.getResult().name();
    }

    public String deleteMethod(int id) throws IOException {
        log.info("Basic service : deleteMethod method");
        DeleteRequest deleteRequest = new DeleteRequest(ESConstants.INDEX);
        deleteRequest.id(id+"");
        DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return response.getResult().name();
    }
}
