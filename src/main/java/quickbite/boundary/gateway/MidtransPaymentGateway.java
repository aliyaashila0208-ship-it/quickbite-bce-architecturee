package quickbite.boundary.gateway;

import org.springframework.stereotype.Component;

@Component
public class MidtransPaymentGateway implements PaymentGatewayAPI {
    @Override
    public String prosesPembayaran(String idPesanan, double jumlah, String tokenPembayaran) {
        if ("invalid_card".equals(tokenPembayaran)) {
            throw new RuntimeException("Kartu ditolak oleh bank");
        }
        return "MIDTRANS-REF-" + System.currentTimeMillis();
    }
}