package DAO;

import java.math.BigInteger;
import java.security.SecureRandom;

public class AlgorithmRSA {

    // bi mat: d, cong khai: e, modul: n
    private BigInteger modul, khoa_bi_mat, khoa_cong_khai;

    public BigInteger getModul() {
        return modul;
    }

    public void setModul(BigInteger modul) {
        this.modul = modul;
    }

    public BigInteger getKhoa_bi_mat() {
        return khoa_bi_mat;
    }

    public void setKhoa_bi_mat(BigInteger khoa_bi_mat) {
        this.khoa_bi_mat = khoa_bi_mat;
    }

    public BigInteger getKhoa_cong_khai() {
        return khoa_cong_khai;
    }

    public void setKhoa_cong_khai(BigInteger khoa_cong_khai) {
        this.khoa_cong_khai = khoa_cong_khai;
    }

    private BigInteger modulHover;

    public AlgorithmRSA() {

    }

    public void TaoKhoaRSA(int bits) {
        // Sử dụng SecureRandom để sinh ra số ngẫu nhiên -> để tạo số nguyên tố p và q
        SecureRandom r = new SecureRandom();

        // certainty 100: là một số nguyên xác định mức độ chắc chắn rằng số được tạo ra là số nguyên tố
        BigInteger p = new BigInteger(bits / 2, 100, r);
        BigInteger q = new BigInteger(bits / 2, 100, r);

        // Tính modul bằng cách nhân hai số nguyên tố p và q
        modul = p.multiply(q);

        // Tính giá trị m, là tích của (p-1) và (q-1) -> Giá trị này là một phần của quá trình tạo khóa RSA
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q
                .subtract(BigInteger.ONE));

        // Khởi tạo một biến boolean found để kiểm tra xem đã tìm thấy khóa công khai hợp lệ chưa
        boolean found = false;

        /*
            Thực hiện vòng lặp để tiếp tục tạo các giá trị khoá công khai mới cho đến khi tìm thấy một giá trị hợp lệ:
            + Tạo một số nguyên tố ngẫu nhiên mới cho khóa công khai với độ dài bits / 2.
            + 50 là số ngẫu nhiên không chắc chắn, cho phép số được tạo ra có xác suất là số nguyên tố.
         */
        do {
            khoa_cong_khai = new BigInteger(bits / 2, 50, r);
            // Đảm bảo rằng số nguyên tố mới tạo có ước chung lớn nhất với m là 1
            if (m.gcd(khoa_cong_khai).equals(BigInteger.ONE) && khoa_cong_khai.compareTo(m) < 0) {
                found = true;
            }
        } while (!found);

        /* Tìm khoa bi mat d: (khoa cong khai e * khoa bi mat d) mod m = 1
           vd: e = 17, m = 120 -> d = (17 * d ) mod 120 = 1 */
        khoa_bi_mat = khoa_cong_khai.modInverse(m);

    }

    /*
        + Với cặp khoá công khai (e, n) -> mã hoá thông điệp bằng cách sử dụng phép toán mũ modulo: message^e mod n
        + vd: có message = HELLO, cặp khoá (e,n) = (17,27)
        + message được chuyển thành từng byte theo mã ASCII -> nối chuỗi thành 1 số nguyên lớn duy nhất
            H: 72
            E: 69
            L: 76
            L: 76
            O: 79
        + HELLO = 7269767679
        + Sử dụng khoá công khai (e,n) để mã hoá số này
     */
    
    public BigInteger v1_mahoa(BigInteger message, BigInteger privateKey) {

        System.out.println(modul + " " + privateKey + " " + message);
        return message.modPow(privateKey, modul);
    }

    public synchronized BigInteger v1_giaima(BigInteger message, AlgorithmRSA rsa) {
        // Kiểm tra nếu modul hoặc khoa_cong_khai là null, nếu có thì ném ra ngoại lệ IllegalStateException
        if (rsa.getModul() == null || rsa.getKhoa_cong_khai() == null) {
            throw new IllegalStateException("modul hoặc khoa_cong_khai là null");

        }
        // Sử dụng phương thức modPow với m và modul đã được khởi tạo
        return message.modPow(rsa.getKhoa_cong_khai(), rsa.getModul());
    }
}
