package quickbite.boundary.gateway;

public interface PaymentGatewayAPI {
    String prosesPembayaran(String idPesanan, double jumlah, String tokenPembayaran);
}