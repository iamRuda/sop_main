package com.example.demo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;
import java.util.Map;

public class QrCodeUtil {

    public static String generateQrCode(String data) {
        try {
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 100, 100, hints);

            StringBuilder qrCodeUrl = new StringBuilder();
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                for (int x = 0; x < bitMatrix.getWidth(); x++) {
                    qrCodeUrl.append(bitMatrix.get(x, y) ? "1" : "0");
                }
                qrCodeUrl.append("\n");
            }

            return qrCodeUrl.toString();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при генерации QR-кода", e);
        }
    }
}
