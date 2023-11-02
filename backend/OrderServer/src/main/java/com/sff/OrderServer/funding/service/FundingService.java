package com.sff.OrderServer.funding.service;

import com.sff.OrderServer.bucket.entity.Bucket;
import com.sff.OrderServer.bucket.entity.OrderMenu;
import com.sff.OrderServer.bucket.repository.BucketRepository;
import com.sff.OrderServer.bucket.repository.OrderMenuRepository;
import com.sff.OrderServer.error.code.BucketError;
import com.sff.OrderServer.error.code.FundingError;
import com.sff.OrderServer.error.type.BaseException;
import com.sff.OrderServer.funding.dto.FundingDetailResponse;
import com.sff.OrderServer.funding.dto.FundingRequest;
import com.sff.OrderServer.funding.dto.FundingResponse;
import com.sff.OrderServer.funding.entity.Funding;
import com.sff.OrderServer.funding.repository.FundingRepository;
import com.sff.OrderServer.utils.ApiError;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FundingService {
    private final FundingRepository fundingRepository;
    private final BucketRepository bucketRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Transactional
    public void createFunding(Long userId, FundingRequest fundingRequest){
        Bucket bucket = bucketRepository.findByBucketIdAndUserId(fundingRequest.getBucketId(), userId).orElseThrow(()->
                new BaseException(new ApiError(BucketError.NON_EXIST_BUCKET_USER)));
        if(fundingRepository.findByBucket(bucket).isPresent()){
            // 바구니와 펀딩은 1대1 관계인데 이미 바구니에 해당하는 펀딩 정보가 존재할 경우 예외 처리
            // 이런 경우 결제 시 중복 요청의 가능성.
            throw new BaseException(new ApiError(FundingError.EXIST_FUNDING_FOR_BUCKET));
        }
        try{
            Funding funding = new Funding(bucket,fundingRequest, userId);
            fundingRepository.save(funding);
        }catch(Exception e){
            throw new BaseException(new ApiError(FundingError.FAIL_TO_CREATE_FUNDING));
        }

    }

    public List<FundingResponse> getFundings(Long userId){
        List<Funding> fundings = fundingRepository.findAllByUserId(userId);
        List<FundingResponse> fundingResponses = new ArrayList<>();
        for(Funding funding : fundings){
            Long storeId = funding.getStoreId();
            // storeId로 가게 이름, 이미지 URL 가져와야함.(MSA)
            String storeName = "temp";
            String storeUrl = "url";

            Bucket bucket = funding.getBucket();
            List<OrderMenu> menus = orderMenuRepository.findAllByBucket(bucket);
            OrderMenu menu = menus.get(0);
            Integer restCount = menus.size()-1;

            fundingResponses.add(new FundingResponse(funding, storeName, storeUrl, bucket.getTotalPrice(), menu, restCount));
        }
        return fundingResponses;
    }

    public FundingDetailResponse getFunding(Long userId, Long fundingId){
        Funding funding = fundingRepository.findByFundingIdAndUserId(fundingId, userId).orElseThrow(
                ()->new BaseException(new ApiError(FundingError.NOT_EXIST_FUNDING)));


    }
}