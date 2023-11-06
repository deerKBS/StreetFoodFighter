package com.sff.PaymentServer.infra;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "notificationClient", url = "${spring.data.Notification_Server}")
public interface NotificationClient {

}