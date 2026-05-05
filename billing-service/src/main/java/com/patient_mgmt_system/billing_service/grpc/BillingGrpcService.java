package com.patient_mgmt_system.billing_service.grpc;


import com.example.billing.BillingRequest;
import com.example.billing.BillingResponse;
import com.example.billing.BillingServiceGrpc;
import com.patient_mgmt_system.billing_service.BillingServiceApplication;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse> responseStreamObserver) {
        log.info("createBillingAccount request received {}", billingRequest.toString());
        // Bussiness loigc
        BillingResponse response = BillingResponse.newBuilder().setAccountId("12345").setStatus("ACTIVE").build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }
}
