package com.mv.patient.grpc;

import com.example.billing.BillingRequest;
import com.example.billing.BillingResponse;
import com.example.billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BillingServiceGrpcClient {
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress,
                                    @Value("${billing.service.grpc.port:9001}") int serverPort) {

        log.info("Connecting to billing service GRPC Server at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingResponse createBillingAccount(String patientId, String name, String email) {
        BillingRequest billingRequest = BillingRequest.newBuilder().setPatientId(patientId).setEmail(email).setName(name).build();
        BillingResponse billingResponse = blockingStub.createBillingAccount(billingRequest);
        log.info("Received response from billing service via GRPC: {}", billingResponse);
        return billingResponse;
    }

}
