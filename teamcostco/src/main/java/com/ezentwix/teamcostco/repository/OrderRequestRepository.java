package com.ezentwix.teamcostco.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.product.OrderRequestDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderRequestRepository {

    private final SqlSessionTemplate sql;

    public List<OrderRequestDTO> getAll() {
        return sql.selectList("OrderRequest.getAllWithProductName");
    }

    public void insertOrderRequest(OrderRequestDTO orderRequest) {
        int seq = sql.selectOne("OrderRequest.getNextRequestId");
        orderRequest.setRequest_id(seq);
        System.out.println(orderRequest);
        sql.insert("OrderRequest.insertOrderRequest", orderRequest);
    }

    public int getNextRequestId() {
        return sql.selectOne("OrderRequest.getNextRequestId");
    }

    public OrderRequestDTO getById(Integer request_id){
        return sql.selectOne("OrderRequest.getById", request_id);
    }

    public void updateReceivedQty(Integer requestId, int receivedQty) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateReceivedQty'");
    }

    public void updateDefectiveQty(Integer requestId, int defectiveQty) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDefectiveQty'");
    }
}
