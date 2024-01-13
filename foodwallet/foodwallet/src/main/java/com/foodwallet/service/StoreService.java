package com.foodwallet.service;

import com.foodwallet.controller.response.StoreDetailResponse;
import com.foodwallet.controller.response.StoreResponse;
import com.foodwallet.domain.Member;
import com.foodwallet.domain.Store;
import com.foodwallet.repository.MemberRepository;
import com.foodwallet.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    public StoreResponse createStore(Long memberId, String name, String info, String address, String opening, String category) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(IllegalArgumentException::new);
        Store store = Store.create(name, info, address, opening, category, member);
        Store savedStore = storeRepository.save(store);
        return StoreResponse.of(savedStore);
    }

    public List<StoreResponse> searchStores(String query) {
        List<Store> stores = storeRepository.findAllByNameLike(query);
        return stores.stream()
            .map(StoreResponse::of)
            .collect(Collectors.toList());
    }

    public StoreDetailResponse searchStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
            .orElseThrow(IllegalArgumentException::new);

        return StoreDetailResponse.of(store);
    }
}
