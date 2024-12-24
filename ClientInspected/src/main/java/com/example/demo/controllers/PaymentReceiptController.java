package com.example.demo.controllers;

import com.example.demo.grpc.InspectedPerson;
import com.example.demo.grpc.InspectedPersonClient;
import com.example.demo.util.QrCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

@RestController
public class PaymentReceiptController {

    private static final String ACCOUNT_NUMBER = "1234567890";

    @Autowired
    private InspectedPersonClient inspectedPersonClient;

    @GetMapping("/generateReceipt/{id}")
    public String generateReceipt(@PathVariable long id, @RequestParam(required = false) Double cost) {
        CountDownLatch latch = new CountDownLatch(1);
        InspectedPerson[] personWrapper = new InspectedPerson[1];

        inspectedPersonClient.fetchInspectedPersonById(id, latch, personWrapper);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Error fetching person details";
        }

        if (personWrapper[0] == null) {
            return "Inspected person not found";
        }

        String name = personWrapper[0].getName();
        String passportNumber = personWrapper[0].getPassportNumber();
        passportNumber = passportNumber.substring(0, 4) + "****" + passportNumber.substring(8);

        double taxAmount = (cost != null) ? cost : 1250;

        String qrCodeUrl = QrCodeUtil.generateQrCode("https://pay.me/" + ACCOUNT_NUMBER + "#name#" + name + "#price#" + cost);

        String receipt = String.format(
                "Payment Receipt:\n" +
                        "Name: %s\n" +
                        "Passport Number: %s\n" +
                        "Tax Amount: %.2f\n" +
                        "Account Number: %s\n" +
                        "QR Code: %s\n",
                name, passportNumber, taxAmount, ACCOUNT_NUMBER, qrCodeUrl
        );

        return receipt;
    }
}
