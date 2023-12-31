package com.sff.OrderServer.order.dto;

import com.sff.OrderServer.dto.Grade;
import com.sff.OrderServer.dto.MemberInfoResponse;
import com.sff.OrderServer.dto.ReviewMSAResponse;
import com.sff.OrderServer.order.entity.OrderRecord;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerOrderDetailResponse {

    private Long orderId;
    private LocalDateTime createAt;
    private String userNickName;
    private Grade userGrade;
    private String userPhone;
    private String requirement;
    private List<OrderItem> orderItemList;
    private Integer totalPrice;
    private ReviewMSAResponse review;

    public OwnerOrderDetailResponse(OrderRecord orderRecord, MemberInfoResponse member,
            ReviewMSAResponse review, List<OrderItem> orderItemList) {
        this.orderId = orderRecord.getOrderId();
        this.createAt = orderRecord.getCreatedAt();
        this.userNickName = member.getNickname();
        this.userGrade = member.getGrade();
        this.userPhone = member.getPhone();
        this.requirement = orderRecord.getRequirement();
        this.orderItemList = orderItemList;
        this.totalPrice = orderRecord.getBucket().getTotalPrice();
        this.review = review;
    }
}
