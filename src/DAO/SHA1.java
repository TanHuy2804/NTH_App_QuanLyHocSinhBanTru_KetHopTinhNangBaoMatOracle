
package DAO;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

// Tính toán giá trị băm của một file bằng thuật toán SHA-1
public class SHA1 {

    /* 
        Định nghĩa kích thước của bộ đệm (buffer) dùng để đọc dữ liệu từ file
        Biểu diễn KB -> Byte, 1KB = 1024Byte
        Mỗi lần đọc dữ liệu từ file, chương trình sẽ đọc 32KB dữ liệu và lưu trữ trong bộ nhớ đệm
     */
    private static int BUFFER_SIZE = 32 * 1024;

    public static void main(String[] args) throws Exception {

    }

    public BigInteger md(String f) throws Exception {
        // Đọc dữ liệu từ file
        BufferedInputStream file = new BufferedInputStream(new FileInputStream(f));

        // Tạo đối tượng MessageDigest với thuật toán SHA-1
        MessageDigest md = MessageDigest.getInstance("SHA-1");

        // Tính toán giá trị băm
        DigestInputStream in = new DigestInputStream(file, md);
        int i;

        // Dữ liệu từ file được đọc vào bộ nhớ đệm -> Vòng lặp kết thúc khi không con giá trị nào được đọc.
        byte[] buffer = new byte[BUFFER_SIZE];
        do {
            i = in.read(buffer, 0, BUFFER_SIZE);
        } while (i == BUFFER_SIZE);
        md = in.getMessageDigest();

        // Đóng luồng DigestInputStream và giải phóng tài nguyên
        in.close();

        // Trả về giá trị băm
        return new BigInteger(md.digest());
    }
}
