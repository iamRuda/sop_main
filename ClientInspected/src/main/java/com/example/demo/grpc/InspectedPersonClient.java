package com.example.demo.grpc;

import com.example.demo.grpc.InspectedPersonProtos.*;
import com.example.demo.grpc.InspectedPersonServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

@Service
public class InspectedPersonClient {

    private final ManagedChannel channel;

    private final InspectedPersonServiceGrpc.InspectedPersonServiceStub asyncStub;

    public InspectedPersonClient() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        this.asyncStub = InspectedPersonServiceGrpc.newStub(channel);
    }

    public void fetchAllInspectedPersons(CountDownLatch latch) {
        com.google.protobuf.Empty request = com.google.protobuf.Empty.getDefaultInstance();

        StreamObserver<AllInspectedPersonResponse> responseObserver = new StreamObserver<AllInspectedPersonResponse>() {
            @Override
            public void onNext(AllInspectedPersonResponse response) {
                System.out.println("Getting Inspected Person:");
                response.getInspectedPersonsList().forEach(person -> {
                    System.out.println("ID: " + person.getId() + ", Name: " + person.getName());
                });
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("Answer Server ended.");
                latch.countDown();
            }
        };

        asyncStub.allInspectedPerson(request, responseObserver);
    }

    public void fetchInspectedPersonById(long id, CountDownLatch latch, InspectedPerson[] personWrapper) {
        GetInspectedPersonRequest request = GetInspectedPersonRequest.newBuilder()
                .setId(id)
                .build();

        StreamObserver<GetInspectedPersonResponse> responseObserver = new StreamObserver<GetInspectedPersonResponse>() {
            @Override
            public void onNext(GetInspectedPersonResponse response) {
                if (response.hasInspectedPerson()) {
                    personWrapper[0] = response.getInspectedPerson();
                } else {
                    personWrapper[0] = null;
                }
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Error: " + t.getMessage());
                personWrapper[0] = null;
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        };

        asyncStub.getInspectedPerson(request, responseObserver);
    }

}
