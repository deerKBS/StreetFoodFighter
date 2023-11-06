package com.sff.PaymentServer.payment.service;

import com.sff.PaymentServer.dto.OrderCreateRequest;
import com.sff.PaymentServer.dto.PurposeCreateRequest;
import com.sff.PaymentServer.error.code.NetworkError;
import com.sff.PaymentServer.error.type.BaseException;
import com.sff.PaymentServer.infra.OrderClient;
import com.sff.PaymentServer.infra.UserClient;
import com.sff.PaymentServer.payment.repository.PaymentRecordRepository;
import com.sff.PaymentServer.utils.ApiError;
import com.sff.PaymentServer.utils.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPaymentService {
    private final PaymentRecordRepository paymentRecordRepository;
    private final OrderClient orderClient;
    private final UserClient userClient;
    public void createOrderPayment(Long userId, OrderCreateRequest orderCreateRequest){
        // 주문 정보 추가 -> OrderServer
        Integer totalPrice = createOrderRecord(orderCreateRequest);

        // 회원 포인트 차감 -> UserServer
        subtractUser(totalPrice);

        // 주문 정보 변경 - 주문 성공 + 바구니 상태 변경 -> OrderServer

        // 주문 결제 완료 알림(to 고객) -> NotificationServer

        // 주문 접수 대기 알림(to 사장) -> NotificationServer

    }

    // 총액을 알아내는 것과 주문 정보 저장 동작을 한 번의 요청으로 할지 분리할지 고민.
    private Integer createOrderRecord(OrderCreateRequest orderCreateRequest){
        try{
            ApiResult result = orderClient.createOrderRecord(orderCreateRequest);
            if(result.isSuccess()) {
                return (Integer)result.getResponse();
            }
            throw new BaseException(new ApiError(result.getApiError()));
        }catch (Exception e){
            throw new BaseException(new ApiError(NetworkError.NETWORK_ERROR_ORDER));
        }
    }

    private void subtractUser(Integer totalPrice){
        try{
            ApiResult result = userClient.updateUserPoint(new PurposeCreateRequest(totalPrice, false));
            if(result.isSuccess()){
                return;
            }
            throw new BaseException(new ApiError(result.getApiError()));
        }catch (Exception e){
            throw new BaseException(new ApiError(NetworkError.NETWORK_ERROR_USER));
        }
    }
}
