package com.example.springdatabasicdemo.grpc;

import com.example.springdatabasicdemo.grpc.InspectedPersonProtos.*;
import com.example.springdatabasicdemo.repositories.InspectedPersonRepository;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("inspectedPersonServiceImplGrpc")
public class InspectedPersonServiceImpl extends InspectedPersonServiceGrpc.InspectedPersonServiceImplBase {

    private final InspectedPersonRepository inspectedPersonRepository;

    public InspectedPersonServiceImpl(InspectedPersonRepository inspectedPersonRepository) {
        this.inspectedPersonRepository = inspectedPersonRepository;
    }

    @Override
    public void allInspectedPerson(com.google.protobuf.Empty request, StreamObserver<AllInspectedPersonResponse> responseObserver) {
        List<com.example.springdatabasicdemo.models.InspectedPerson> inspectedPersons = inspectedPersonRepository.findAll();

        AllInspectedPersonResponse.Builder responseBuilder = AllInspectedPersonResponse.newBuilder();

        for (com.example.springdatabasicdemo.models.InspectedPerson person : inspectedPersons) {
            InspectedPerson grpcPerson = InspectedPerson.newBuilder()
                    .setId(person.getId())
                    .setName(person.getName())
                    .setPassportNumber(person.getPassportNumber())
                    .setChecked(person.getChecked() != null && person.getChecked())
                    .build();
            responseBuilder.addInspectedPersons(grpcPerson);
        }

        AllInspectedPersonResponse response = responseBuilder.build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getInspectedPerson(GetInspectedPersonRequest request, StreamObserver<GetInspectedPersonResponse> responseObserver) {
        long id = request.getId();

        com.example.springdatabasicdemo.models.InspectedPerson inspectedPerson = inspectedPersonRepository.findById(id)
                .orElse(null);

        GetInspectedPersonResponse response;

        if (inspectedPerson != null) {
            InspectedPerson grpcPerson = InspectedPerson.newBuilder()
                    .setId(inspectedPerson.getId())
                    .setName(inspectedPerson.getName())
                    .setPassportNumber(inspectedPerson.getPassportNumber())
                    .setChecked(inspectedPerson.getChecked() != null && inspectedPerson.getChecked())
                    .build();

            response = GetInspectedPersonResponse.newBuilder()
                    .setInspectedPerson(grpcPerson)
                    .build();
        } else {
            response = GetInspectedPersonResponse.newBuilder().build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
