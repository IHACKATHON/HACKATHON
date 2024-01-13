package com.foodwallet.controller;

import com.foodwallet.controller.request.CreateStoreRequest;
import com.foodwallet.controller.response.StoreDetailResponse;
import com.foodwallet.controller.response.StoreResponse;
import com.foodwallet.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StoreResponse> createStore(@RequestBody CreateStoreRequest request) {
        StoreResponse response = storeService.createStore(request.getMemberId(), request.getName(), request.getInfo(), request.getAddress(), request.getOpening(), request.getCategory());
        return ApiResponse.created(response);
    }

    @GetMapping
    public ApiResponse<List<StoreResponse>> searchStores(
        @RequestParam(name = "query", defaultValue = "") String query
    ) {
        List<StoreResponse> responses = storeService.searchStores(query);

        return ApiResponse.ok(responses);
    }

    @GetMapping("/{storeId}")
    public ApiResponse<StoreDetailResponse> searchStore(@PathVariable(name = "storeId") Long storeId) {
        StoreDetailResponse responses = storeService.searchStore(storeId);

        return ApiResponse.ok(responses);
    }
}
