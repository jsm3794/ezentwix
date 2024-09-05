package com.ezentwix.teamcostco.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.product.OrderRequestDTO;
import com.ezentwix.teamcostco.dto.product.RequestAndProductDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class OrderRequestRepository {

    private final SqlSessionTemplate sql;

    public List<OrderRequestDTO> getAll() {
        return sql.selectList("OrderRequest.getAllWithProductName");
    }

    public List<RequestAndProductDTO> getRequestAndProductInfo() {
        return sql.selectList("OrderRequest.getRequestAndProductInfo");
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
}
