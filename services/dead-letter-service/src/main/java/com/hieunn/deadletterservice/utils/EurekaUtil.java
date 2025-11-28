package com.hieunn.deadletterservice.utils;

import com.hieunn.commonlib.enums.constants.ServiceName;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EurekaUtil {
    private final EurekaClient eurekaClient;

    public boolean areServicesUp(ServiceName... serviceNames) {
        for (ServiceName serviceName : serviceNames) {
            List<InstanceInfo> instances = eurekaClient.getInstancesByVipAddress(
                    serviceName.getValue(),
                    false
            );

            if (instances == null || instances.stream().noneMatch(
                    instance -> instance.getStatus() == InstanceInfo.InstanceStatus.UP)) {
                return false;
            }
        }
        return true;
    }

    public boolean areServicesUpAny(ServiceName... serviceNames) {
        for (ServiceName serviceName : serviceNames) {
            List<InstanceInfo> instances =
                    eurekaClient.getInstancesByVipAddress(serviceName.getValue(), false);

            if (instances != null && instances.stream().anyMatch(
                    instance -> instance.getStatus() == InstanceInfo.InstanceStatus.UP)) {
                return true;
            }
        }
        return false;
    }
}
