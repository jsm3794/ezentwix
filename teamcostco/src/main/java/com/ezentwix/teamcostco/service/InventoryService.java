package com.ezentwix.teamcostco.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezentwix.teamcostco.PageMetadataProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService implements PageMetadataProvider {

    @Override
    public String getUri() {
        return "inventory/inventory";
    }

    @Override
    public String getPageTitle() {
        return "재고관리";
    }

    // 아래 두 메서드(getCssFiles, getJsFiles)는
    // Override 메서드를 구현하지않으면 null을 반환하도록 설계되어있음

    @Override
    public List<String> getCssFiles() {
        return List.of("/css/contents/inventory.css");

    }

    @Override
    public List<String> getJsFiles() {
        return List.of("/js/contents/inventory.js");
    }

}