--Tao mot doi tuong thu muc
    CREATE DIRECTORY TanHuy AS 'C:\Users\nth\Desktop\DUMP_FILE';
    SELECT * FROM all_directories WHERE directory_name = 'TANHUY';
    DROP DIRECTORY TANHUY;
--Export co so du lieu tu user NV001
expdp NV001/NV001@ORCL DIRECTORY=TanHuy DUMPFILE=bakup.dmp SCHEMAS=NV001
--Import co so du lieu
impdp NV001/NV001@DB_HSBT DIRECTORY=TanHuy DUMPFILE=bakup.dmp SCHEMAS=NV001
-- Chinh sua ngay 15.06.2024
--------------------------------------------------------------------------------------------------------------------------------
-- XOA BANG
drop table TAIKHOAN CASCADE CONSTRAINTS
drop table PHUHUYNH CASCADE CONSTRAINTS
drop table NHANVIEN CASCADE CONSTRAINTS
drop table HOCSINH CASCADE CONSTRAINTS
drop table CONGNO CASCADE CONSTRAINTS
drop table GIAMSATUSER CASCADE CONSTRAINTS

drop table LOP CASCADE CONSTRAINTS
drop table DIEMDANH CASCADE CONSTRAINTS

drop table DANGKYKHAUPHANAN CASCADE CONSTRAINTS
drop table COMBOMONANHIENTHI CASCADE CONSTRAINTS
drop table COMBOMONAN CASCADE CONSTRAINTS

drop table MONHOC CASCADE CONSTRAINTS
drop table CAUHOI CASCADE CONSTRAINTS
drop table DETHI CASCADE CONSTRAINTS
drop table CHITIETDETHI CASCADE CONSTRAINTS
drop table KETQUA CASCADE CONSTRAINTS

commit

--------------------------------------------------------------------------------------------------------------------------------
-- TAO BANG
CREATE TABLE TaiKhoan
(
	UserName_TK VARCHAR2(50),
	Password_TK VARCHAR2(200)
    CONSTRAINT PK_TaiKhoan PRIMARY KEY (UserName_TK)
);
ALTER TABLE TaiKhoan
ADD CONSTRAINT PK_TaiKhoan PRIMARY KEY (UserName_TK);
GRANT SELECT, INSERT, UPDATE, DELETE ON TaiKhoan TO NV001;

CREATE TABLE NhanVien 
(
    MaNV VARCHAR2(50) PRIMARY KEY,
    TenNV VARCHAR2(100),
    GioiTinh VARCHAR2(100),
    NgaySinh DATE,
    ChucVu VARCHAR2(100),
    DiaChi VARCHAR2(100),
    Email VARCHAR2(100),
    SoDT VARCHAR2(100),
    TrinhDo VARCHAR2(100),
    ChuyenMon VARCHAR2(100),
    NoiDaoTao VARCHAR2(100),
    NamTotNghiep NUMBER(5),
    HinhAnh BLOB
);
GRANT SELECT, INSERT, UPDATE, DELETE ON NhanVien TO NV001;

CREATE TABLE PhuHuynh 
(
    MaPH VARCHAR2(50) PRIMARY KEY,
    TenPH VARCHAR2(100),
    GioiTinh VARCHAR2(100),
    NgaySinh DATE,
    NgheNghiep VARCHAR2(100),
    DiaChi VARCHAR2(100),
    Email VARCHAR2(100),
    SoDT VARCHAR2(100)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON PhuHuynh TO NV001;

CREATE TABLE Lop 
(
    MaLop VARCHAR2(50) PRIMARY KEY,
    TenLop VARCHAR2(100),
    SiSo NUMBER(5),
    NamHoc NUMBER(5)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON Lop TO NV001;

CREATE TABLE HocSinh 
(
    MaHS VARCHAR2(50) PRIMARY KEY,
    TenHS VARCHAR2(100),
    GioiTinh VARCHAR2(100),
    NgaySinh DATE,
    DiaChi VARCHAR2(100),
    NgayVaoTruong DATE,
    TinhTrangSucKhoe VARCHAR2(100),
    MaLop VARCHAR2(50),
    MaPH VARCHAR2(50),
    CONSTRAINT FK_HocSinh_Lop FOREIGN KEY (MaLop) REFERENCES Lop(MaLop),
    CONSTRAINT FK_HocSinh_PhuHuynh FOREIGN KEY (MaPH) REFERENCES PhuHuynh(MaPH)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON HocSinh TO NV001;

CREATE TABLE CongNo 
(
    MaCongNo VARCHAR2(50) PRIMARY KEY,
    MaHS VARCHAR2(50),
    NgayTaoCongNo DATE,
    TienHocPhi DECIMAL(10, 2),
    TienAn DECIMAL(10, 2),
    TienPhuThu DECIMAL(10, 2),
    TongCongNo DECIMAL(10, 2),
    TrangThai VARCHAR2(100),
    CONSTRAINT FK_CongNo_HocSinh FOREIGN KEY (MaHS) REFERENCES HocSinh(MaHS)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON CongNo TO NV001;

CREATE TABLE DiemDanh 
(
    NgayDiemDanh DATE,
    MaHS VARCHAR2(50),
    TenHS VARCHAR2(100),
    TrangThaiDiemDanh VARCHAR2(100),
    GhiChu VARCHAR2(100),
    MaNV VARCHAR2(50),
    MaLop VARCHAR2(50),
    CONSTRAINT PK_DIEMDANH PRIMARY KEY (NgayDiemDanh, MaHS),
    CONSTRAINT FK_DiemDanh_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    CONSTRAINT FK_DiemDanh_Lop FOREIGN KEY (MaLop) REFERENCES Lop(MaLop),
    CONSTRAINT FK_DiemDanh_HocSinh FOREIGN KEY (MaHS) REFERENCES HocSinh(MaHS)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON DiemDanh TO NV001;

-- Tao sequence
CREATE SEQUENCE ComBoMonSeq
  START WITH 1
  INCREMENT BY 1
  MAXVALUE 999999
  CYCLE;

CREATE TABLE ComBoMonAn (
    MaComBoMon VARCHAR2(50) DEFAULT 'CB' || LPAD(ComBoMonSeq.NEXTVAL, 3, '0') PRIMARY KEY,
    TenMon1 VARCHAR2(100),
    TenMon2 VARCHAR2(100),
    TenMon3 VARCHAR2(100),
    TenMon4 VARCHAR2(100),
    LoaiComBoMon VARCHAR2(100),
    ThuTrongTuan INT,
    GiaTien FLOAT,
    QRCode VARCHAR2(100),
    MaNV VARCHAR2(100),
    CONSTRAINT FK_ComBoMonAn_NhanVien FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON ComBoMonAn TO NV001;
ALTER TABLE ComBoMonAn
MODIFY MaNV VARCHAR2(50);
COMMIT;
CREATE TABLE ComBoMonAnHienThi 
(
    MaComBoMon VARCHAR2(50) PRIMARY KEY,
    TenMon1 VARCHAR2(100),
    TenMon2 VARCHAR2(100),
    TenMon3 VARCHAR2(100),
    TenMon4 VARCHAR2(100),
    LoaiComBoMon VARCHAR2(100),
    ThuTrongTuan int,
    GiaTien FLOAT,
    QRCode VARCHAR2(100),
    CONSTRAINT FK_CbMonAn_CbHienThi FOREIGN KEY (MaComBoMon) REFERENCES ComBoMonAn(MaComBoMon)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON ComBoMonAnHienThi TO NV001;

CREATE TABLE DangKyKhauPhanAn
(
    MaHS VARCHAR2(50),
    NgayDangKyKP VARCHAR2(11),
    ThuTrongTuan int,
    Gia float,
    MaComBoMon VARCHAR2(50),
    QRCode VARCHAR2(100),
    PRIMARY KEY (MaHS, NgayDangKyKP,ThuTrongTuan),
    CONSTRAINT FK_DangKyKhauPhanAn_HocSinh FOREIGN KEY (MaHS) REFERENCES HocSinh(MaHS),
    CONSTRAINT FK_DangKyKhauPhanAn_CbMonAn FOREIGN KEY (MaComBoMon) REFERENCES ComBoMonAn(MaComBoMon)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON DangKyKhauPhanAn TO NV001;

CREATE TABLE MonHoc 
(
    MaMH VARCHAR2(50) PRIMARY KEY,
    TenMH VARCHAR2(100),
    MaLop VARCHAR2(50),
    CONSTRAINT FK_MonHoc_Lop FOREIGN KEY (MaLop) REFERENCES Lop(MaLop)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON MonHoc TO NV001;

CREATE TABLE CauHoi
(
    MaCauHoi VARCHAR2(50) PRIMARY KEY,
    CauHoi VARCHAR2(100),
    DapAn_A VARCHAR2(100),
    DapAn_B VARCHAR2(100),
    DapAn_C VARCHAR2(100),
    DapAn_D VARCHAR2(100),
    DapAnDung VARCHAR2(100),
    MaMH VARCHAR2(50),
    CONSTRAINT FK_CauHoi_MonHoc FOREIGN KEY (MaMH) REFERENCES MonHoc(MaMH)
);
ALTER TABLE CauHoi
MODIFY CauHoi VARCHAR2(3000);
ALTER TABLE CauHoi
MODIFY DapAn_A VARCHAR2(1000);
ALTER TABLE CauHoi
MODIFY DapAn_B VARCHAR2(1000);
ALTER TABLE CauHoi
MODIFY DapAn_C VARCHAR2(1000);
ALTER TABLE CauHoi
MODIFY DapAn_D VARCHAR2(1000);
ALTER TABLE CauHoi
MODIFY DapAnDung VARCHAR2(100);
COMMIT;
GRANT SELECT, INSERT, UPDATE, DELETE ON CauHoi TO NV001;

CREATE TABLE DeThi
(
    MaDeThi VARCHAR2(50) PRIMARY KEY,
    NgaySoanDe DATE,
    SoLuongCauHoi VARCHAR2(100),
    TrangThai VARCHAR2(100),
    MaNV VARCHAR2(50),
    MaMH VARCHAR2(50),
    TrangThaiTruyCapDe VARCHAR2(100),
    CONSTRAINT FK_DeThi_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    CONSTRAINT FK_DeThi_MonHoc FOREIGN KEY (MaMH) REFERENCES MonHoc(MaMH)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON DeThi TO NV001;

CREATE TABLE ChiTietDeThi
(
    MaDeThi VARCHAR2(50),
    MaCauHoi VARCHAR2(50),
    NgayThi DATE,
    ThoiGianThi VARCHAR2(100),
    ThoiGianBatDau VARCHAR2(50),
    ThoiGianKetThuc VARCHAR2(50),
    CONSTRAINT PK_ChiTietDeThi PRIMARY KEY (MaDeThi, MaCauHoi),
    CONSTRAINT FK_ChiTietDeThi_CauHoi FOREIGN KEY (MaCauHoi) REFERENCES CauHoi(MaCauHoi)
);
ALTER TABLE ChiTietDeThi
ADD CONSTRAINT FK_ChiTietDeThi_DeThi FOREIGN KEY (MaDeThi) REFERENCES DeThi(MaDeThi);

GRANT SELECT, INSERT, UPDATE, DELETE ON ChiTietDeThi TO NV001;

CREATE TABLE KetQua
(
    MaHocSinh VARCHAR2(50),
    MaDeThi VARCHAR2(50),
    SoCauDung VARCHAR2(100),
    DiemSo VARCHAR2(100),
    ThoiGianLucNop TIMESTAMP,
    CONSTRAINT PK_KetQua PRIMARY KEY (MaHocSinh, MaDeThi, ThoiGianLucNop),
    CONSTRAINT FK_KetQua_DeThi FOREIGN KEY (MaDeThi) REFERENCES DeThi(MaDeThi),
    CONSTRAINT FK_KetQua FOREIGN KEY (MaHocSinh) REFERENCES HocSinh(MaHS)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON KetQua TO NV001;

commit
--------------------------------------------------------------------------------------------------------------------------------
-- THEM DU LIEU
-- Insert du lieu TaiKhoan
INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS001', 'HS001');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS002', 'HS002');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS003', 'HS003');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS004', 'HS004');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS005', 'HS005');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS006', 'HS006');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS007', 'HS007');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS008', 'HS008');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS009', 'HS009');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS010', 'HS010');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS011', 'HS011');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS012', 'HS012');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS013', 'HS013');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS014', 'HS014');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('HS015', 'HS015');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV001', 'NV001');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV002', 'NV002');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV003', 'NV003');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV004', 'NV004');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV005', 'NV005');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV006', 'NV006');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV007', 'NV007');

INSERT INTO TaiKhoan (UserName_TK, Password_TK)
VALUES ('NV008', 'NV008');

SELECT * FROM NV001.TaiKhoan;

-- Insert du lieu nhan vien
INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV001', 'Nguy?n Tu?n Anh', 'Nam', TO_DATE('01/01/2002', 'DD/MM/YYYY'), 'Quan Tri He Thong', 'Hà N?i', 'nguyentuananh@gmail.com', '0905091029', '??i h?c', 'Qu?n lý h? th?ng', '??i h?c Công Th??ng', 2020, EMPTY_BLOB());

INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV002', 'Nguy?n Nh? Qu?nh', 'N?', TO_DATE('01/11/2002', 'DD/MM/YYYY'), 'Giao Vien', 'Qu?ng Nam', 'nguyennhuquynh@gmail.com', '0332908764', '??i h?c', 'S? ph?m', '??i h?c S? Ph?m', 2020, EMPTY_BLOB());

INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV003', 'Nguy?n Hu?nh ??ng Phúc', 'Nam', TO_DATE('05/01/2002', 'DD/MM/YYYY'), 'Quan Ly', 'Hà N?i', 'phucdang@gmail.com', '0905333029', '??i h?c', 'Qu?n tr? kinh doanh', '??i h?c Công Th??ng', 2020, EMPTY_BLOB());

INSERT INTO NV001.NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV004', 'Nguy?n T?n Vang', 'Nam', TO_DATE('06/05/2002', 'DD/MM/YYYY'), 'Nhan Vien Thu Ngan', 'TP HCM', 'tanvang@gmail.com', '0337333519', '??i h?c', 'K? toán tài chính', '??i h?c Công Th??ng', 2020, EMPTY_BLOB());

INSERT INTO NV001.NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV005', 'Nguy?n Hoàng Anh', 'Nam', TO_DATE('16/03/2002', 'DD/MM/YYYY'), 'Giao Vien', 'TP HCM', 'tanvang@gmail.com', '0337033079', '??i h?c', 'S? ph?m toán', '??i h?c S? Ph?m', 2020, EMPTY_BLOB());

INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV006', 'Nguy?n T?n Hoàng', 'Nam', TO_DATE('08/03/2005', 'DD/MM/YYYY'), 'Quan Tri He Thong', 'Qu?ng Nam', 'nguyentanhoang@gmail.com', '0332806538', '??i h?c', 'Qu?n lý h? th?ng', '??i h?c Công Th??ng', 2020, EMPTY_BLOB());

INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV007', 'Nguy?n Bích Ng?c', 'N?', TO_DATE('15/01/1999', 'DD/MM/YYYY'), 'Quan Ly', 'TP HCM', 'bichngoc@gmail.com', '0337778889', '??i h?c', 'Qu?n tr? kinh doanh', '??i h?c Công Th??ng', 2020, EMPTY_BLOB());

INSERT INTO NhanVien (MaNV, TenNV, GioiTinh, NgaySinh, ChucVu, DiaChi, Email, SoDT, TrinhDo, ChuyenMon, NoiDaoTao, NamTotNghiep, HinhAnh)
VALUES ('NV008', 'Nguy?n V?n Hoàng', 'Nam', TO_DATE('26/10/2001', 'DD/MM/YYYY'), 'Nhan Vien Thu Ngan', 'Qu?ng Nam', 'vanhoang@gmail.com', '0377368209', '??i h?c', 'K? toán tài chính', '??i h?c Công Th??ng', 2020, EMPTY_BLOB());

select * from NV001.NhanVien

-- Insert du lieu phu huynh
INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH001', 'Lê Th? Bình', 'N?', TO_DATE('05/05/1970', 'DD/MM/YYYY'), 'Công nhân', 'TP HCM', 'le_b@email.com', '0334568721');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH002', 'Nguy?n An Khang', 'Nam', TO_DATE('15/06/1980', 'DD/MM/YYYY'), 'Nông dân', 'Long An', 'ankhang@email.com', '0332808728');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH003', 'Lê T?n Tài', 'Nam', TO_DATE('15/06/1992', 'DD/MM/YYYY'), 'Bác s?', 'TP HCM', 'le_b@email.com', '0328968651');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH004', 'Tr?n V?n Tâm', 'Nam', TO_DATE('05/06/1990', 'DD/MM/YYYY'), 'Gi?ng viên', 'TP HCM', 'tvt@email.com', '0344968651');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH005', 'Lê Tu?n H?ng', 'Nam', TO_DATE('11/11/1996', 'DD/MM/YYYY'), 'Công nhân', 'TP HCM', 'lth@email.com', '0328962251');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH006', 'Ph?m Thanh Th?o', 'N?', TO_DATE('07/06/1996', 'DD/MM/YYYY'), 'Nông dân', 'TP HCM', 'ptt@email.com', '0328988671');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH007', 'Nguy?n V?n Nam', 'Nam', TO_DATE('01/06/1996', 'DD/MM/YYYY'), 'Giáo Viên', 'TP HCM', 'nvn@email.com', '0328988161');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH008', 'Nguy?n ??c Nam', 'Nam', TO_DATE('05/05/1995', 'DD/MM/YYYY'), 'Công nhân', 'TP HCM', 'ndn@email.com', '0328988151');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH009', 'Nguy?n Yên Thanh Ngân', 'N?', TO_DATE('08/08/1997', 'DD/MM/YYYY'), 'Công nhân', 'TP HCM', 'nytn@email.com', '0328988614');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH010', 'Ph?m Ng?c Ngân', 'N?', TO_DATE('06/06/1991', 'DD/MM/YYYY'), 'Nông dân', 'TP HCM', 'pnn@email.com', '0328988632');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH011', 'Kh??ng Th? Tuy?t Ng?c', 'N?', TO_DATE('07/05/1995', 'DD/MM/YYYY'), 'Công An', 'TP HCM', 'kttn@email.com', '0328988655');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH012', '?ào Th? Thu Nguy?t', 'N?', TO_DATE('11/06/1998', 'DD/MM/YYYY'), 'Nông dân', 'Hà N?i', 'dttn@email.com', '0328988699');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH013', 'Lê B?ng Nhi', 'N?', TO_DATE('06/07/1991', 'DD/MM/YYYY'), 'Nông dân', 'TP HCM', 'lbn@email.com', '0328988678');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH014', 'Nguy?n Th? H?ng Phúc', 'N?', TO_DATE('07/05/1997', 'DD/MM/YYYY'), 'Nông dân', 'TP HCM', 'nthp@email.com', '0321188671');

INSERT INTO PhuHuynh (MaPH, TenPH, GioiTinh, NgaySinh, NgheNghiep, DiaChi, Email, SoDT)
VALUES ('PH015', 'Lâm Vi?t Qui', 'Nam', TO_DATE('07/07/1997', 'DD/MM/YYYY'), 'Nông dân', 'TP HCM', 'lvq@email.com', '0328998671');

select * from NV001.PhuHuynh

INSERT INTO Lop (MaLop, TenLop, SiSo, NamHoc)
VALUES ('L5A', 'Lop 5A', 30, 2024);
INSERT INTO Lop (MaLop, TenLop, SiSo, NamHoc)
VALUES ('L5B', 'Lop 5B', 30, 2024);
INSERT INTO Lop (MaLop, TenLop, SiSo, NamHoc)
VALUES ('L5C', 'Lop 5C', 30, 2024);

select * from NV001.Lop

-- Insert du lieu hoc sinh
INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS001', 'Nguy?n V?n An', 'Nam', TO_DATE('09/12/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5A', 'PH001');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS002', 'Hu?nh Th? Tính', 'N?', TO_DATE('20/10/2008', 'DD/MM/YYYY'), 'Long An', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'Bình th??ng', 'L5A', 'PH002');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS003', 'Võ Th? H?ng Ánh', 'N?', TO_DATE('08/03/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'Bình th??ng', 'L5A', 'PH003');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS004', 'Nguy?n Qu?nh Chi', 'N?', TO_DATE('15/11/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5A', 'PH004');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS005', 'Võ Lê Ánh Tuy?t', 'N?', TO_DATE('08/12/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'Bình th??ng', 'L5A', 'PH005');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS006', 'Nguy?n Th? Kim Tuy?t', 'N?', TO_DATE('07/07/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5B', 'PH006');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS007', 'Võ Lê Tu?n', 'Nam', TO_DATE('01/06/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5B', 'PH007');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS008', 'T? Th? Qu?nh Trang', 'N?', TO_DATE('08/08/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'Bình th??ng', 'L5B', 'PH008');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS009', 'Nguy?n Ng?c Xuân Trân', 'N?', TO_DATE('12/03/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5B', 'PH009');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS010', 'Hu?nh Tr?ng Tín', 'Nam', TO_DATE('11/11/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5B', 'PH010');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS011', 'Nguy?n Thu? Thu? Tiên', 'N?', TO_DATE('08/06/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5C', 'PH011');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS012', '??ng Nguy?n Ng?c Th??ng', 'N?', TO_DATE('04/03/2008', 'DD/MM/YYYY'), 'Hà N?i', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'Bình th??ng', 'L5C', 'PH012');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS013', 'Lê Nguy?n Anh Th?', 'N?', TO_DATE('05/05/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5C', 'PH013');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS014', 'Bùi V?n Th?', 'Nam', TO_DATE('02/03/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'Bình th??ng', 'L5C', 'PH014');

INSERT INTO HocSinh (MaHS, TenHS, GioiTinh, NgaySinh, DiaChi, NgayVaoTruong, TinhTrangSucKhoe, MaLop, MaPH)
VALUES ('HS015', 'Nguy?n Th? Út Quyên', 'N?', TO_DATE('01/03/2008', 'DD/MM/YYYY'), 'TP HCM', TO_DATE('01/09/2021', 'DD/MM/YYYY'), 'T?t', 'L5C', 'PH015');

select * from NV001.HocSinh

-- Insert du lieu cong no
INSERT INTO CongNo (MaCongNo, MaHS, NgayTaoCongNo, TienHocPhi, TienAn, TienPhuThu, TongCongNo, TrangThai)
VALUES ('CN001', 'HS001', SYSDATE, 1500000, 500000, 200000, 2200000, 'Chua thanh toan');

INSERT INTO CongNo (MaCongNo, MaHS, NgayTaoCongNo, TienHocPhi, TienAn, TienPhuThu, TongCongNo, TrangThai)
VALUES ('CN002', 'HS002', SYSDATE, 1500000, 1000000, 200000, 2700000, 'Chua thanh toan');

INSERT INTO CongNo (MaCongNo, MaHS, NgayTaoCongNo, TienHocPhi, TienAn, TienPhuThu, TongCongNo, TrangThai)
VALUES ('CN003', 'HS003', SYSDATE, 1500000, 500000, 200000, 2200000, 'Chua thanh toan');

select * from NV001.CongNo

-- Insert du lieu ComBoMonAn
INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('Th?t kho', 'Canh chua ca lóc', 'Rau c?i lu?c', 'M?n', 'M?n', 2, 25000, '2man.png', 'NV003');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('Mít kho', 'Canh chua chay', 'B?u lu?c', 'M?n', 'Chay', 2, 20000, '2chay.png', 'NV003');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('Th?t s??n n??ng', 'Canh rong bi?n', 'D?a leo', 'Chu?i', 'M?n', 3, 25000, '3man.png', 'NV003');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('??u h? chiên x?', 'Canh rong bi?n chay', 'N?m xào', 'Chu?i', 'Chay', 3, 20000, '3chay.png', 'NV003');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('Cá ng? kho th?m', 'Canh cà chua tr?ng', 'Rau mu?ng xào', '?i', 'M?n', 4, 25000, '4man.png', 'NV003');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('Rau c?i kho', 'Canh cà chua chay', 'Rau mu?ng lu?c', '?i', 'Chay', 4, 20000, '4chay.png', 'NV007');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('Gà kho x? ?t', 'Canh bí ??', 'B?p c?i lu?c', 'D?a h?u', 'M?n', 5, 25000, '5man.png', 'NV007');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('Cà ri chay', 'Rau s?ng', 'Bánh mì ', 'D?a h?u', 'Chay', 5, 20000, '5chay.png', 'NV007');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('??u h? nh?i th?t', 'Canh bí ?ao', 'C? su xào', 'Xoài', 'M?n', 6, 25000, '6man.png', 'NV007');

INSERT INTO ComBoMonAn (TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode, MaNV)
VALUES ('N?m xào x? ?t', 'Canh bí ?ao', 'C? su xào', 'Xoài', 'Chay', 6, 20000, '6chay.png', 'NV007');

select * from NV001.ComBoMonAn

INSERT INTO MonHoc (MaMH, TenMH, MaLop)
VALUES ('MH001-TOAN', 'Toan', 'L5A');
INSERT INTO MonHoc (MaMH, TenMH, MaLop)
VALUES ('MH002-VAN', 'Tieng Viet', 'L5B');
INSERT INTO MonHoc (MaMH, TenMH, MaLop)
VALUES ('MH003-ANH', 'Tieng Anh', 'L5C');

select * from NV001.MonHoc

INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH001', '2 + 2 b?ng bao nhiêu ?', '3', '4', '5', '6', 'B', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH002', '5 x 3 b?ng bao nhiêu ?', '12', '15', '18', '20', 'B', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH003', '10 : 2 b?ng bao nhiêu ?', '2', '3', '4', '5', 'D', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH004', '25 - 13 b?ng bao nhiêu ?', '8', '10', '12', '14', 'C', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH005', '2 x 2 x 2 b?ng bao nhiêu ?', '4', '6', '8', '10', 'C', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH006', '9 x 3 b?ng bao nhiêu ?', '56', '27', '72', '81', 'B', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH007', '27 : 3 b?ng bao nhiêu ?', '2', '9', '8', '5', 'B', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH008', '12 + 18 b?ng bao nhiêu ?', '28', '30', '32', '34', 'B', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH009', '30 - 17 b?ng bao nhiêu ?', '10', '11', '12', '13', 'D', 'MH001-TOAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH010', '7 x 9 b?ng bao nhiêu ?', '56', '63', '72', '81', 'B', 'MH001-TOAN');

INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH021', 'Ai là tác gi? c?a tác ph?m "T?t ?èn"?', 'Nguy?n Du', 'Nam Cao', 'Th?ch Lam', 'Ngô T?t T?', 'C', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH022', 'Tác ph?m "Lão H?c" ???c vi?t b?i ai?', 'Tô Hoài', 'Nguy?n Du', 'Nam Cao', 'T?n ??t', 'A', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH023', 'Ai là tác gi? c?a "S? ??"?', 'Nam Cao', 'Nguy?n Ng?c Ng?n', 'Ngô T?t T?', 'V? Tr?ng Ph?ng', 'D', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH024', 'Tác ph?m "Chí Phèo" thu?c th? lo?i gì?', 'Ti?u thuy?t', 'Truy?n ng?n', 'K?ch', 'T?n v?n', 'B', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH025', 'Ai là tác gi? c?a "T?t ?èn"?', 'Nam Cao', 'Th?ch Lam', 'Tô Hoài', 'Nguy?n Du', 'B', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH026', 'Trong "Chí Phèo", Chí Phèo có m?t ng??i b?n tên là ai?', 'Th? N?', 'M?u', 'T?m Cám', 'M?n', 'B', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH027', '"Lão H?c" là tên g?i c?a nhân v?t chính trong tác ph?m nào?', 'Lão H?c', 'T?t ?èn', 'Truy?n Ki?u', 'S? ??', 'D', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH028', 'Tác ph?m "T?t ?èn" thu?c th? lo?i gì?', 'Truy?n dài', 'Truy?n ng?n', 'Ti?u thuy?t', 'K?ch', 'B', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH029', 'Ai là tác gi? c?a "Truy?n Ki?u"?', 'T?n ??t', 'Tô Hoài', 'Nguy?n Du', 'Nam Cao', 'C', 'MH002-VAN');
INSERT INTO CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
VALUES ('CH030', 'Tác ph?m "S? ??" nói v? cu?c s?ng c?a t?ng l?p nào?', 'T?ng l?p trí th?c', 'T?ng l?p nông dân', 'T?ng l?p công nhân', 'T?ng l?p quý t?c', 'C', 'MH002-VAN');

select * from NV001.CauHoi

INSERT INTO DeThi (MaDeThi, NgaySoanDe, SoLuongCauHoi, TrangThai, MaNV, MaMH, TrangThaiTruyCapDe)
VALUES ('DETHI-0001', TO_DATE('2024-04-13', 'YYYY-MM-DD'), 10, 'Hoàn thành', 'NV002', 'MH001-TOAN','M?');

INSERT INTO DeThi (MaDeThi, NgaySoanDe, SoLuongCauHoi, TrangThai, MaNV, MaMH, TrangThaiTruyCapDe)
VALUES ('DETHI-0002', TO_DATE('2024-04-13', 'YYYY-MM-DD'), 10, 'Hoàn thành', 'NV002', 'MH002-VAN', 'M?');

INSERT INTO DeThi (MaDeThi, NgaySoanDe, SoLuongCauHoi, TrangThai, MaNV, MaMH, TrangThaiTruyCapDe)
VALUES ('DETHI-0003', TO_DATE('2024-04-13', 'YYYY-MM-DD'), 10, 'Hoàn thành', 'NV002', 'MH003-ANH', 'M?');

select * from NV001.DeThi

INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH001', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH002', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH003', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH004', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH005', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH006', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH007', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH008', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH009', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0001', 'CH010', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 08:00:00', '2024-04-13 08:45:00');

INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH021', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH022', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH023', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH024', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH025', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH026', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH027', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH028', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH029', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');
INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
VALUES ('DETHI-0002', 'CH030', TO_DATE('2024-04-13', 'YYYY-MM-DD'), '45 phút', '2024-04-13 10:00:00', '2024-04-13 10:45:00');

select * from NV001.ChiTietDeThi

commit

--------------------------------------------------------------------------------------------------------------------------------
-- THIET LAP PROFILE
DROP PROFILE NhanVien;

alter session set "_ORACLE_SCRIPT"=true;

ALTER PROFILE NHANVIEN LIMIT 
    FAILED_LOGIN_ATTEMPTS 3
    SESSIONS_PER_USER 3  
    CONNECT_TIME 45 
    IDLE_TIME 15
    PASSWORD_LOCK_TIME 5;
COMMIT;
--Vi du:
CREATE USER NV002 IDENTIFIED BY NV002 PROFILE NhanVien;
GRANT CREATE SESSION TO NV002;
GRANT ALL PRIVILEGES TO NV002;
--KIEM TOAN VA GIAI TRINH

--Trigger
    -- Tao bang luu lich su giam sat ?ang nhap va dang xuat
    CREATE TABLE GiamSatUser (
        key_id NUMBER PRIMARY KEY,
        username VARCHAR2(50),
        login_time TIMESTAMP,
        logout_time TIMESTAMP,
        hoat_dong VARCHAR2(50)
    );
    DROP TABLE GiamSatUser;
    SELECT * FROM NV001.GIAMSATUSER;
    -- Tao sequence de tao gia tri duy nhat cho key_id
    CREATE SEQUENCE GiamSatDangNhap;
    ALTER SEQUENCE GiamSatDangNhap INCREMENT BY 1 ORDER;
    DROP SEQUENCE GiamSatDangNhap;
    -- Trigger gi?m s?t ??ng nh?p
    DROP TRIGGER GiamSatUserLogin_trigger;
    CREATE OR REPLACE TRIGGER GiamSatUserLogin_trigger
    AFTER LOGON ON DATABASE
    BEGIN
        IF USER != 'SYS' THEN
            INSERT INTO GiamSatUser (key_id, username, login_time, hoat_dong)
            VALUES (GiamSatDangNhap.NEXTVAL, USER, SYSTIMESTAMP, 'Online');
        END IF;
    END GiamSatUserLogin_trigger;
    /
    
    -- Trigger gi?m s?t ??ng xu?t
    DROP TRIGGER GiamSatUserLogout_trigger;
    CREATE OR REPLACE TRIGGER GiamSatUserLogout_trigger
    BEFORE LOGOFF ON DATABASE
    BEGIN
        IF USER != 'SYS' THEN
            INSERT INTO GiamSatUser (key_id, username, logout_time, hoat_dong)
            VALUES (GiamSatDangNhap.NEXTVAL, USER, SYSTIMESTAMP, 'Offline');
        END IF;
    END GiamSatUserLogout_trigger;
    /
    --Xem du lieu giam sat
    SELECT * FROM GiamSatUser;

--FGA
    --Chinh sach FGA do user NV002 l? nguoi co quyen quan ly khoi tao nen ta cap cac quyen sau de user NV002 c? quyen thuc thi chinh sach
    GRANT EXECUTE ON DBMS_FGA TO NV006;
    GRANT SELECT ON DBA_FGA_AUDIT_TRAIL TO NV006;
    --Lenh tao chinh sach
    BEGIN
        DBMS_FGA.ADD_POLICY(
            object_schema => 'NV001',
            object_name => 'CONGNO',
            policy_name => 'FGA_NV001_CONGNO',
            --audit_condition => 'TrangThai = "Chua thanh toan"',
            audit_column => 'TrangThai',
            statement_types => 'INSERT,UPDATE,DELETE,SELECT',
            audit_trail => DBMS_FGA.DB+DBMS_FGA.EXTENDED);
    END;
    --Lenh xoa chinh sach
    BEGIN
        DBMS_FGA.DROP_POLICY(
            object_schema => 'NV001',
            object_name => 'CONGNO',
            policy_name => 'FGA_NV001_CONGNO');
    END;
    /
    --Xoa tat ca cac bang ghi giam sat cua FGA
    DELETE FROM FGA_LOG$;
    COMMIT;
    --Xem bang ghi giam sat cua FGA
    SELECT * FROM SYS.DBA_FGA_AUDIT_TRAIL;
    SELECT * FROM DBA_FGA_AUDIT_TRAIL;
    --Xem cac chinh sach da duoc tao
    SELECT object_owner, object_name, policy_name
    FROM dba_policies
    WHERE object_owner  LIKE 'NV%';
    
    SELECT *
    FROM dba_audit_policies;
--Standard Audit
    --Lenh ngung dam sat
    NOAUDIT ALL;
    NOAUDIT ALL PRIVILEGES;
    --Giam sat cac hoat dong nhu grant, revoke
    AUDIT ROLE;
    AUDIT ROLE WHENEVER SUCCESSFUL;
    --Giam sat cac hoat dong ma user n?o thao tac tren bang 
    AUDIT ALTER, GRANT, INSERT, UPDATE, DELETE ON NV001.DangKyKhauPhanAn;
    --Giam sat tat ca cac hoat dong select, update, delete, insert c?a user NV002 tung thao tac qua cac bang trong csdl m? ko gioi han ve so bang 
    AUDIT SELECT TABLE, UPDATE TABLE, DELETE TABLE, INSERT TABLE BY NV002;
    --Lenh de xem view ghi lai nhat ky giam sat
    SELECT username, timestamp, obj_name, action_name, COUNT(*) AS so_luong
    FROM dba_audit_trail
    WHERE username = 'NV002'
    GROUP BY username, timestamp, obj_name, action_name;
    --Xoa tat ca ban ghi giam sat cua Standard Auditing
    DELETE FROM SYS.AUD$
    COMMIT;
    
--------------------------------------------------------------------------------------------------------------------------------
-- PHAN QUYEN, DIEU KHIEN TRUY CAP (KET HOP VPD, OLS)

    -- Tao user ket hop profile da duoc tao truoc do
    CREATE USER NV002 IDENTIFIED BY NV002 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV002;
    CREATE USER NV003 IDENTIFIED BY NV003 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV003;
    CREATE USER NV004 IDENTIFIED BY NV004 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV004;
    CREATE USER NV005 IDENTIFIED BY NV005 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV005;
    CREATE USER NV006 IDENTIFIED BY NV006 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV006;
    CREATE USER NV007 IDENTIFIED BY NV007 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV007;
    CREATE USER NV008 IDENTIFIED BY NV008 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV008;
    
    CREATE USER HS001 IDENTIFIED BY HS001;--da tao
    GRANT CREATE SESSION TO HS001;
    CREATE USER HS002 IDENTIFIED BY HS002;
    GRANT CREATE SESSION TO HS002;
    CREATE USER HS003 IDENTIFIED BY HS003;
    GRANT CREATE SESSION TO HS003;
    CREATE USER HS004 IDENTIFIED BY HS004;
    GRANT CREATE SESSION TO HS004;
    CREATE USER HS005 IDENTIFIED BY HS005;
    GRANT CREATE SESSION TO HS005;
    CREATE USER HS006 IDENTIFIED BY HS006;--da tao
    GRANT CREATE SESSION TO HS006;
    CREATE USER HS007 IDENTIFIED BY HS007;
    GRANT CREATE SESSION TO HS007;
    CREATE USER HS008 IDENTIFIED BY HS008;
    GRANT CREATE SESSION TO HS008;
    CREATE USER HS009 IDENTIFIED BY HS009;
    GRANT CREATE SESSION TO HS009;
    CREATE USER HS010 IDENTIFIED BY HS010;
    GRANT CREATE SESSION TO HS010;
    CREATE USER HS011 IDENTIFIED BY HS011;
    GRANT CREATE SESSION TO HS011;
    CREATE USER HS012 IDENTIFIED BY HS012;
    GRANT CREATE SESSION TO HS012;
    CREATE USER HS013 IDENTIFIED BY HS013;
    GRANT CREATE SESSION TO HS013;
    CREATE USER HS014 IDENTIFIED BY HS014;
    GRANT CREATE SESSION TO HS014;
    CREATE USER HS015 IDENTIFIED BY HS015;
    GRANT CREATE SESSION TO HS015;
    --Tao cac nhom quyen cho user
    CREATE ROLE QuanTriVien_ROLE;
    CREATE ROLE QuanLy_ROLE;
    CREATE ROLE NhanVienThuNgan_ROLE;
    CREATE ROLE GiaoVien_ROLE;
    CREATE ROLE HocSinh_ROLE;
    
    --Lenh xoa cac nhom quyen
    DROP ROLE QuanTriVien_ROLE;
    DROP ROLE QuanLy_ROLE;
    DROP ROLE NhanVienThuNgan_ROLE;
    DROP ROLE GiaoVien_ROLE;
    DROP ROLE HocSinh_ROLE;
    
    -- Gan quyen SELECT, INSERT, UPDATE v? DELETE cho nhom QuanTriVien_ROLE tren bang 
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.NhanVien          TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.HocSinh           TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.PhuHuynh          TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.Lop               TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.CongNo            TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DangKyKhauPhanAn  TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DiemDanh          TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.ComBoMonAn        TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.ComBoMonAnHienThi TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.GiamSatUser       TO QuanTriVien_ROLE;--chua tao ban
    GRANT SELECT                         ON DBA_FGA_AUDIT_TRAIL     TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.MonHoc            TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.CauHoi            TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DeThi             TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.ChiTietDeThi      TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.KetQua            TO QuanTriVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.TAIKHOAN          TO QuanTriVien_ROLE;
    
    GRANT EXECUTE ON CapQuyenUser                                   TO QuanTriVien_ROLE;
    GRANT EXECUTE ON ThuHoiQuyenUser                                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON PROC_ACCOUNT_UNLOCK                            TO QuanTriVien_ROLE;
    GRANT EXECUTE ON PROC_ACCOUNT_LOCK                              TO QuanTriVien_ROLE;
    GRANT EXECUTE ON CNQ                                            TO QuanTriVien_ROLE;
    GRANT EXECUTE ON THNQ                                           TO QuanTriVien_ROLE;
    GRANT EXECUTE ON ADD_FGA_POLICY                                 TO QuanTriVien_ROLE;
    GRANT EXECUTE ON DROP_FGA_POLICY                                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON CREATE_DIRECTORY_PROC                          TO QuanTriVien_ROLE;
    GRANT EXECUTE ON USER_TAB_PRIVS_SelectAll                       TO QuanTriVien_ROLE;
    GRANT EXECUTE ON USER_TAB_PRIVS_Where_MaUser                    TO QuanTriVien_ROLE;
    --GRANT EXECUTE ON ThemHoacCapNhatDiemDanh                        TO QuanTriVien_ROLE;
    GRANT EXECUTE ON CapNhatThongTinNhanVien                        TO QuanTriVien_ROLE;
    GRANT EXECUTE ON KetQua_SelectAll                               TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.CONGNO_SELECTALL                         TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.MonHoc_SELECTALL                         TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.DeThi_Where_MaMonHoc                     TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.CauHoi_Where_MaDeThi                     TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.DeThi_SelectAll                          TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.CauHoi_Select_DapAnDung                  TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.ThoiGianThi_Where_MaDeThi                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.CreateProfile                            TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.DropProfile                              TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.UpdateProfile                            TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.SelectAll_Profile                        TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.SelectAll_User_Profile                   TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.SelectWhereProfile                       TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.AlterProfileToUser                       TO QuanTriVien_ROLE;    
    GRANT EXECUTE ON NV001.Update_TrangThaiTruyCapDe                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.Table_SelectAll                          TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.Cot_Where_Table                          TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.UserName_TK_SelectAll                    TO QuanTriVien_ROLE;
    GRANT EXECUTE ON NV001.dba_stmt_audit_opts_SelectAll            TO QuanTriVien_ROLE; 
    GRANT EXECUTE ON SYS.DemSessionsUser                            TO QuanTriVien_ROLE;
    GRANT EXECUTE ON LayPassWord                                    TO QuanTriVien_ROLE;
    GRANT EXECUTE ON DES_DECRYPT                                    TO QuanTriVien_ROLE;
    GRANT EXECUTE ON CREATE_USER_PROC                               TO QuanTriVien_ROLE;
    GRANT EXECUTE ON create_role_proc                               TO QuanTriVien_ROLE;
    GRANT EXECUTE ON drop_role_proc                                 TO QuanTriVien_ROLE;
    GRANT EXECUTE ON GrantRoleToUser                                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON RevokeRoleFromUser                             TO QuanTriVien_ROLE;
    GRANT EXECUTE ON CapNhatNhanVien                                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON ThemNhanVien                                   TO QuanTriVien_ROLE;
    GRANT EXECUTE ON XoaUser                                        TO QuanTriVien_ROLE;
    
    -- Gan quyen SELECT, INSERT, UPDATE v? DELETE cho nhom GiaoVien_ROLE tren bang
    GRANT SELECT                         ON NV001.HocSinh           TO GiaoVien_ROLE;
    GRANT SELECT, UPDATE                 ON NV001.NhanVien          TO GiaoVien_ROLE;
    GRANT SELECT, UPDATE                 ON NV001.Lop               TO GiaoVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DiemDanh          TO GiaoVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DiemDanh          TO GiaoVien_ROLE;
    
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.MonHoc            TO GiaoVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.CauHoi            TO GiaoVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DeThi             TO GiaoVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.ChiTietDeThi      TO GiaoVien_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.KetQua            TO GiaoVien_ROLE;
    
    GRANT EXECUTE ON ThemDiemDanh                                   TO GiaoVien_ROLE;--co the da bo
    GRANT EXECUTE ON UpdateDiemDanh                                 TO GiaoVien_ROLE;
    
    GRANT EXECUTE ON E1_XoaDeThi                                    TO GiaoVien_ROLE;
    GRANT EXECUTE ON E1_SuaDeThi                                    TO GiaoVien_ROLE;
    GRANT EXECUTE ON E1_ThemDeThi                                   TO GiaoVien_ROLE;
    GRANT EXECUTE ON E1_ThemCauHoi                                  TO GiaoVien_ROLE;
    GRANT EXECUTE ON E1_SuaCauHoi                                   TO GiaoVien_ROLE;
    GRANT EXECUTE ON E1_XoaCauHoi                                   TO GiaoVien_ROLE;
    GRANT EXECUTE ON E1_ThemChiTietDeThi                            TO GiaoVien_ROLE;
    GRANT EXECUTE ON v1_XoaAllTheoMaDeThi                           TO GiaoVien_ROLE;
    GRANT EXECUTE ON KetQua_SelectAll                               TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.MonHoc_SELECTALL                         TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.DeThi_Where_MaMonHoc                     TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.CauHoi_Where_MaDeThi                     TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.DeThi_SelectAll                          TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.CauHoi_Select_DapAnDung                  TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.ThoiGianThi_Where_MaDeThi                TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.v1_DiemDanh_SelectAll                    TO GiaoVien_ROLE;
    GRANT EXECUTE ON LayPassWord                                    TO GiaoVien_ROLE;
    GRANT EXECUTE ON DES_DECRYPT                                    TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.LayMaLop                                 TO GiaoVien_ROLE;
    GRANT EXECUTE ON NV001.LayTenHS                                 TO GiaoVien_ROLE;
    -- Gan quyen SELECT, INSERT, UPDATE v? DELETE cho nhom HocSinh_ROLE tren bang
    GRANT SELECT, UPDATE                 ON NV001.HocSinh           TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.Lop               TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.DiemDanh          TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.ComBoMonAn        TO HocSinh_ROLE;
    
    GRANT SELECT                         ON NV001.MonHoc            TO HocSinh_ROLE; 
    GRANT SELECT                         ON NV001.CauHoi            TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.DeThi             TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.ChiTietDeThi      TO HocSinh_ROLE;
    GRANT SELECT, INSERT                 ON NV001.KetQua            TO HocSinh_ROLE;
    -- Gan quyen SELECT, INSERT, UPDATE v? DELETE cho nhom NhanVienQuanLy_ROLE tren bang
    GRANT SELECT                         ON NV001.NhanVien          TO QuanLy_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.ComBoMonAn        TO QuanLy_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.ComBoMonAnHienThi TO QuanLy_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DangKyKhauPhanAn  TO QuanLy_ROLE;
    
    GRANT EXECUTE ON XoaThongTinComBoMonAn                          TO QuanLy_ROLE;
    GRANT EXECUTE ON ThemDuLieuVaoComBoMonAn                        TO QuanLy_ROLE;
    GRANT EXECUTE ON XoaThongTinComBoMonAnHienThi                   TO QuanLy_ROLE;
    GRANT EXECUTE ON XoaMotKP_ComBoMon                              TO QuanLy_ROLE;
    GRANT EXECUTE ON XoaMotKP_ComBoMonHienThi                       TO QuanLy_ROLE;
    GRANT EXECUTE ON ThemDuLieuVaoComBoMonAnHienThi                 TO QuanLy_ROLE;
    GRANT EXECUTE ON UpdateComBoMonAn                               TO QuanLy_ROLE;
    GRANT EXECUTE ON CapNhatThongTinNhanVien                        TO QuanLy_ROLE;
    GRANT EXECUTE ON NV001.GetComboMonan                            TO QuanLy_ROLE;
    GRANT EXECUTE ON NV001.LayThongTinDangKiCur                     TO QuanLy_ROLE;
    GRANT EXECUTE ON NV001.GetKhauPhanDaDangKi                      TO QuanLy_ROLE;
    GRANT EXECUTE ON LayPassWord                                    TO QuanLy_ROLE;
    GRANT EXECUTE ON DES_DECRYPT                                    TO QuanLy_ROLE;
    GRANT EXECUTE ON XoaDangKy_MotKhauPhanAn                        TO QuanLy_ROLE;
    -- Gan quyen SELECT, INSERT, UPDATE v? DELETE cho nhom NhanVienThuNgan_ROLE tren bang
    GRANT SELECT                         ON NV001.NhanVien          TO NhanVienThuNgan_ROLE;
    GRANT SELECT                         ON NV001.HocSinh           TO NhanVienThuNgan_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.CongNo            TO NhanVienThuNgan_ROLE;
    
    REVOKE  UPDATE                       ON NV001.CongNo            FROM NhanVienThuNgan_ROLE;
    
    GRANT EXECUTE ON CapNhatCongNo                                  TO NhanVienThuNgan_ROLE;--co the thay the bang thu tuc ben duoi
    
    GRANT EXECUTE ON CapNhatThongTinNhanVien                        TO NhanVienThuNgan_ROLE;
    GRANT EXECUTE ON E1_ThemCongNo                                  TO NhanVienThuNgan_ROLE;
    GRANT EXECUTE ON E1_SuaCongNo                                   TO NhanVienThuNgan_ROLE;
    GRANT EXECUTE ON E1_XoaCongNo                                   TO NhanVienThuNgan_ROLE;
    GRANT EXECUTE ON NV001.CONGNO_SELECTALL                         TO NhanVienThuNgan_ROLE;
    GRANT EXECUTE ON LayPassWord                                    TO NhanVienThuNgan_ROLE;
    GRANT EXECUTE ON DES_DECRYPT                                    TO NhanVienThuNgan_ROLE;
    -- Gan quyen SELECT, INSERT, UPDATE v? DELETE cho nhom HocSinh_ROLE tren bang 
    GRANT SELECT                         ON NV001.NhanVien          TO HocSinh_ROLE;
    GRANT SELECT, UPDATE                 ON NV001.HocSinh           TO HocSinh_ROLE;
    GRANT SELECT, UPDATE                 ON NV001.PhuHuynh          TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.CongNo            TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.DiemDanh          TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.ComBoMonAn        TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.ComBoMonAnHienThi TO HocSinh_ROLE;
    GRANT SELECT, INSERT, UPDATE, DELETE ON NV001.DangKyKhauPhanAn  TO HocSinh_ROLE;
    
    GRANT SELECT                         ON NV001.MonHoc            TO HocSinh_ROLE; 
    GRANT SELECT                         ON NV001.CauHoi            TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.DeThi             TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.ChiTietDeThi      TO HocSinh_ROLE;
    GRANT SELECT, INSERT                 ON NV001.KetQua            TO HocSinh_ROLE;
    
    GRANT EXECUTE ON GetUserName                                    TO HocSinh_ROLE;
    GRANT EXECUTE ON InsertDangKyKhauPhanAn                         TO HocSinh_ROLE;
    GRANT EXECUTE ON XoaThongTinDangKi                              TO HocSinh_ROLE;
    GRANT EXECUTE ON XoaDangKy_MotKhauPhanAn                        TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.ThemKetQua                               TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.KETQUA_SELECTALL                         TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.CONGNO_SELECTALL                         TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.MonHoc_SELECTALL                         TO HocSinh_ROLE;    
    GRANT EXECUTE ON NV001.DeThi_Where_MaMonHoc                     TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.CauHoi_Where_MaDeThi                     TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.DeThi_SelectAll                          TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.CauHoi_Select_DapAnDung                  TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.ThoiGianThi_Where_MaDeThi                TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.v1_DiemDanh_SelectAll                    TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.GetComboMonan                            TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.LayThongTinDangKiCur                     TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.GetKhauPhanDaDangKi                      TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.LayTongCongNo                            TO HocSinh_ROLE;  
    GRANT EXECUTE ON NV001.GetMaCongNoByMaHS                        TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.GetTrangThaiByMaHS                       TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.UpdateTrangThaiCongNo                    TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.ThoiGianThi_Where_MaDeThi                TO HocSinh_ROLE;--CHUA
    GRANT EXECUTE ON NV001.TenHS_Where_MaHS                         TO HocSinh_ROLE;
    GRANT EXECUTE ON LayPassWord                                    TO HocSinh_ROLE;
    GRANT EXECUTE ON DES_DECRYPT                                    TO HocSinh_ROLE;  
    GRANT EXECUTE ON NV001.LayMaLop                                 TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.LayTenHS                                 TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.LayTongCongNoChuaThanhToan               TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.KiemTraTrangThaiCongNo                   TO HocSinh_ROLE;
    GRANT EXECUTE ON NV001.UpdateTrangThaiCongNo                    TO HocSinh_ROLE;
    --Cap quyen cho user 
    GRANT QuanTriVien_ROLE      TO NV001;--Da cap quyen
    GRANT QuanTriVien_ROLE      TO NV006;--Nguoi tao ra cac chinh sach
    
    GRANT GiaoVien_ROLE         TO NV002;--Da cap quyen
    GRANT GiaoVien_ROLE         TO NV005;
    
    GRANT QuanLy_ROLE           TO NV003;--Da cap quyen
    GRANT QuanLy_ROLE           TO NV007;
    
    GRANT NhanVienThuNgan_ROLE  TO NV004;--Da cap quyen
    GRANT NhanVienThuNgan_ROLE  TO NV008;
    
    GRANT HocSinh_ROLE          TO HS001;--Da cap quyen
    GRANT HocSinh_ROLE          TO HS002;
    GRANT HocSinh_ROLE          TO HS003;
    GRANT HocSinh_ROLE          TO HS004;
    GRANT HocSinh_ROLE          TO HS005;
    GRANT HocSinh_ROLE          TO HS006;
    GRANT HocSinh_ROLE          TO HS007;
    GRANT HocSinh_ROLE          TO HS008;
    GRANT HocSinh_ROLE          TO HS009;
    GRANT HocSinh_ROLE          TO HS010;
    GRANT HocSinh_ROLE          TO HS011;
    GRANT HocSinh_ROLE          TO HS012;
    GRANT HocSinh_ROLE          TO HS013;
    GRANT HocSinh_ROLE          TO HS014;
    GRANT HocSinh_ROLE          TO HS015;
    COMMIT;
--------------------------------------------------------------------------------------------------------------------------------
 -- Tao chinh sach vpd
    CREATE OR REPLACE FUNCTION No_TrangThai (
        p_schema IN VARCHAR2,
        p_object IN VARCHAR2)
    RETURN VARCHAR2
    AS
    BEGIN
        RETURN 'TrangThai <> ''Da thanh toan''';
    END;
    /
    
    -- CHAY ROI
    BEGIN
        DBMS_RLS.add_policy
        (object_schema => 'NV001',
        object_name => 'CONGNO',
        policy_name => 'kothichnhieuthu',
        policy_function => 'No_TrangThai');
    END;
    /
    
    BEGIN
        DBMS_RLS.drop_policy
        (object_schema => 'NV001',
        object_name => 'CONGNO',
        policy_name => 'kothichnhieuthu');
    END;
    /  
    
    -- CHAY ROI
    -- DE THI
    CREATE OR REPLACE FUNCTION No_TrangThai_DETHI (
        p_schema IN VARCHAR2,
        p_object IN VARCHAR2)
    RETURN VARCHAR2
    AS
    BEGIN
        RETURN 'TrangThaiTruyCapDe <> ''Dóng''';
    END;
    /
    
    BEGIN
        DBMS_RLS.add_policy
        (object_schema => 'NV001',
        object_name => 'DETHI',
        policy_name => 'thuckhuyametqua',
        policy_function => 'No_TrangThai_DETHI');
    END;
    /
    
    BEGIN
        DBMS_RLS.drop_policy
        (object_schema => 'NV001',
        object_name => 'DETHI',
        policy_name => 'thuckhuyametqua');
    END;
    /  
    --NHANVIEN
    CREATE OR REPLACE FUNCTION huypro_policy_function (
      schema_var IN VARCHAR2,
      table_var  IN VARCHAR2
    )
    RETURN VARCHAR2
    AS
      v_predicate VARCHAR2(100);
    BEGIN
        IF SYS_CONTEXT('USERENV', 'SESSION_USER') IN ('NV001', 'NV006') THEN
            v_predicate := '1=1';
        ELSE
            v_predicate := 'MaNV = SYS_CONTEXT(''USERENV'', ''SESSION_USER'')';
        END IF;
        
        RETURN v_predicate;
    END;
    /
    
    BEGIN
      DBMS_RLS.ADD_POLICY(
        object_schema   => 'NV001',
        object_name     => 'NHANVIEN',
        policy_name     => 'HuyproPolicy',
        function_schema => 'NV006',
        policy_function => 'huypro_policy_function',
        statement_types => 'SELECT, INSERT, UPDATE',
        update_check    => TRUE
      );
    END;
    /
    COMMIT;
    
    BEGIN
        DBMS_RLS.drop_policy
        (object_schema => 'NV001',
        object_name => 'NHANVIEN',
        policy_name => 'HuyproPolicy');
    END;
    /
    SELECT * FROM NV001.NHANVIEN;
    
    --HOCSINH
    CREATE OR REPLACE FUNCTION huypro_policy_hocsinh (
      schema_var IN VARCHAR2,
      table_var  IN VARCHAR2
    )
    RETURN VARCHAR2
    AS
      v_predicate VARCHAR2(100);
    BEGIN
      v_predicate := 'MaHS = SYS_CONTEXT(''USERENV'', ''SESSION_USER'')';
      RETURN v_predicate;
    END;
    /
    

    BEGIN
      DBMS_RLS.ADD_POLICY(
        object_schema   => 'NV001',
        object_name     => 'HOCSINH',
        policy_name     => 'HuyproPolicyHocsinh',
        function_schema => 'NV006',
        policy_function => 'huypro_policy_hocsinh',
        statement_types => 'SELECT, INSERT, UPDATE',
        update_check    => TRUE
      );
    END;
    /
    BEGIN
      DBMS_RLS.ADD_POLICY(
        object_schema   => 'NV001',
        object_name     => 'DangKyKhauPhanAn',
        policy_name     => 'HuyproPolicyHocsinh_KPA',
        function_schema => 'NV006',
        policy_function => 'huypro_policy_hocsinh',
        statement_types => 'SELECT, INSERT, UPDATE',
        update_check    => TRUE
      );
    END;
    /
    BEGIN
      DBMS_RLS.ADD_POLICY(
        object_schema   => 'NV001',
        object_name     => 'KetQua',
        policy_name     => 'HuyproPolicyHocsinh_KetQua',
        function_schema => 'NV006',
        policy_function => 'huypro_policy_hocsinh_KQ',
        statement_types => 'SELECT, INSERT, UPDATE',
        update_check    => TRUE
      );
    END;
    /
    
    -- CHAY LOI
    --CHINH SACH TAO RA DE HAN CHE VIEC XEM DE THI CUA HOC SINH (CHI XEM DC DE DANG TRANG THAI MO)
    BEGIN
      DBMS_RLS.ADD_POLICY(
        object_schema   => 'NV001',
        object_name     => 'DeThi',
        policy_name     => 'HuyproPolicyHocsinh_DeThi',
        function_schema => 'NV006',
        policy_function => 'huypro_policy_hocsinh_DeThi',
        statement_types => 'SELECT, INSERT, UPDATE',
        update_check    => TRUE
      );
    END;
    /
    COMMIT;
    BEGIN
        DBMS_RLS.drop_policy
        (object_schema => 'NV001',
        object_name => 'DeThi',
        policy_name => 'HuyproPolicyHocsinh_DeThi');
    END;
    /
    SELECT * FROM NV001.HOCSINH
    SELECT * FROM NV001.KETQUA;
--------------------------------------------------------------------------------------------------------------------------------
-- THU TUC
CREATE OR REPLACE PROCEDURE TenHS_Where_MaHS
(
    maHS IN NVARCHAR2,    -- Input parameter for MaHS
    C_cursor OUT SYS_REFCURSOR   -- Output cursor
)
AS
    v_MaHS NVARCHAR2(50);
BEGIN
    v_MaHS := maHS;
    
    OPEN C_cursor FOR
    SELECT TenHS FROM NV001.HocSinh 
    WHERE MaHS = v_MaHS;
END TenHS_Where_MaHS;
/

CREATE OR REPLACE PROCEDURE GetKhauPhanDaDangKi
(
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_huy BOOLEAN;
BEGIN
    -- Ki?m tra n?u tên ng??i dùng b?t ??u b?ng "NV"
    v_huy := (SYS_CONTEXT('USERENV', 'SESSION_USER') LIKE 'NV%');

    -- M? con tr? d?a trên chính sách VPD
    IF v_huy THEN
        OPEN C_cursor FOR
        SELECT * FROM NV001.DangKyKhauPhanAn;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.DangKyKhauPhanAn WHERE MaHS = SYS_CONTEXT('USERENV', 'SESSION_USER');
    END IF;
END GetKhauPhanDaDangKi;
/
CREATE OR REPLACE PROCEDURE CapNhatCongNo (
    p_MaCongNo VARCHAR2,
    p_MaHS VARCHAR2,
    p_NgayTaoCongNo DATE,
    p_TienHocPhi DECIMAL,
    p_TienAn DECIMAL,
    p_TienPhuThu DECIMAL,
    p_TongCongNo DECIMAL,
    p_TrangThai VARCHAR2,
    p_MaNV VARCHAR2
) AS
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CONGNO') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CONGNO');
    
    IF v_HasPermission > 0 THEN
        UPDATE CongNo
        SET
            MaHS = p_MaHS,
            NgayTaoCongNo = p_NgayTaoCongNo,
            TienHocPhi = p_TienHocPhi,
            TienAn = p_TienAn,
            TienPhuThu = p_TienPhuThu,
            TongCongNo = p_TongCongNo,
            TrangThai = p_TrangThai
        WHERE
            MaCongNo = p_MaCongNo;
            
        COMMIT;
    ELSE
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
END CapNhatCongNo;
/
CREATE OR REPLACE PROCEDURE Update_TrangThaiTruyCapDe (
    p_MaDeThi IN VARCHAR2,
    p_TrangThaiTruyCapDe IN VARCHAR2
) AS
BEGIN
    UPDATE DeThi
    SET TrangThaiTruyCapDe = p_TrangThaiTruyCapDe
    WHERE MaDeThi = p_MaDeThi;
    COMMIT;
    -- Ki?m tra s? l??ng b?n ghi ?ã ???c c?p nh?t
    IF SQL%ROWCOUNT = 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'MaDeThi không t?n t?i.');
    END IF;
END;
CREATE OR REPLACE PROCEDURE LayTongCongNo (
    p_mahs IN VARCHAR2,
    p_tongconno OUT FLOAT
) AS
    CURSOR cur_congno IS
        SELECT TONGCONGNO FROM NV001.CONGNO WHERE mahs = p_mahs;
    v_no NV001.CONGNO.TONGCONGNO%TYPE;
    v_tongconno FLOAT := 0;
BEGIN
    OPEN cur_congno;
    LOOP
        FETCH cur_congno INTO v_no;
        EXIT WHEN cur_congno%NOTFOUND;
        v_tongconno := v_tongconno + v_no;
    END LOOP;
    CLOSE cur_congno;
    
    p_tongconno := v_tongconno;
END LayTongCongNo;
/
CREATE OR REPLACE PROCEDURE UpdateTrangThaiCongNo (
    p_MaCongNo NV001.CONGNO.MACONGNO%TYPE
) IS
BEGIN
    UPDATE NV001.CONGNO
    SET TRANGTHAI = 'Da thanh toan'
    WHERE MACONGNO = p_MaCongNo;
    
    COMMIT;
END;
/
CREATE OR REPLACE PROCEDURE GetMaCongNoByMaHS (
    p_MaHS IN NV001.CONGNO.MAHS%TYPE,
    p_MaCongNo OUT NV001.CONGNO.MACONGNO%TYPE
) IS
    CURSOR congno_cursor IS
        SELECT MACONGNO
        FROM NV001.CONGNO
        WHERE MAHS = p_MaHS;

    v_MaCongNo NV001.CONGNO.MACONGNO%TYPE;
BEGIN
    OPEN congno_cursor;
    FETCH congno_cursor INTO v_MaCongNo;

    IF congno_cursor%FOUND THEN
        p_MaCongNo := v_MaCongNo;
    ELSE
        p_MaCongNo := NULL;
        DBMS_OUTPUT.PUT_LINE('No record found for the given MAHS.');
    END IF;

    CLOSE congno_cursor;
END;
/
CREATE OR REPLACE PROCEDURE GetTrangThaiByMaHS (
    p_MaHS IN NV001.CONGNO.MAHS%TYPE,
    p_TrangThai OUT NV001.CONGNO.TRANGTHAI%TYPE
) IS
    CURSOR congno_cursor IS
        SELECT TRANGTHAI
        FROM NV001.CONGNO
        WHERE MAHS = p_MaHS;

    v_TrangThai NV001.CONGNO.TRANGTHAI%TYPE;
BEGIN
    OPEN congno_cursor;
    FETCH congno_cursor INTO v_TrangThai;

    IF congno_cursor%FOUND THEN
        p_TrangThai := v_TrangThai;
    ELSE
        p_TrangThai := NULL;
        DBMS_OUTPUT.PUT_LINE('No record found for the given MAHS.');
    END IF;

    CLOSE congno_cursor;
END;
/
CREATE OR REPLACE PROCEDURE DoiMatKhau(
    p_username IN VARCHAR2,
    p_new_password IN VARCHAR2
)
IS
BEGIN

    EXECUTE IMMEDIATE 'ALTER USER ' || p_username || ' IDENTIFIED BY ' || p_new_password;

    UPDATE TAIKHOAN
    SET Password_TK = p_new_password
    WHERE UserName_TK = p_username;
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END DoiMatKhau;
/
CREATE OR REPLACE PROCEDURE Table_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT table_name FROM all_tables WHERE owner = 'NV001';
END Table_SelectAll;
COMMIT;

CREATE OR REPLACE PROCEDURE Cot_Where_Table
(
    tenbang IN NVARCHAR2,
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_Table NVARCHAR2(50);
BEGIN
    v_Table := tenbang;
    
    OPEN C_cursor FOR
    SELECT column_name --, data_type, data_length
    FROM all_tab_columns
    WHERE table_name = v_Table AND owner = 'NV001';
END Cot_Where_Table;
/
CREATE OR REPLACE PROCEDURE UserName_TK_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT UserName_TK FROM NV001.TAIKHOAN;
END UserName_TK_SelectAll;
COMMIT;

CREATE OR REPLACE PROCEDURE dba_stmt_audit_opts_SelectAll 
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT user_name, audit_option, success, failure FROM dba_stmt_audit_opts;
END dba_stmt_audit_opts_SelectAll;
COMMIT;


CREATE OR REPLACE PROCEDURE SelectAll_Profile
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT PROFILE, RESOURCE_NAME, RESOURCE_TYPE, LIMIT
    FROM DBA_PROFILES
    WHERE PROFILE != 'DEFAULT' AND PROFILE != 'ORA_STIG_PROFILE'
    ORDER BY PROFILE, RESOURCE_NAME;
END SelectAll_Profile;
COMMIT;
CREATE OR REPLACE PROCEDURE SelectAll_User_Profile
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT USERNAME, PROFILE
    FROM DBA_USERS
    WHERE PROFILE != 'DEFAULT';
END SelectAll_User_Profile;
COMMIT;
CREATE OR REPLACE PROCEDURE SelectWhereProfile
(
    p_profile_name IN VARCHAR2,
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT PROFILE, RESOURCE_NAME, RESOURCE_TYPE, LIMIT
    FROM DBA_PROFILES
    WHERE PROFILE = p_profile_name
    ORDER BY PROFILE, RESOURCE_NAME;
END SelectWhereProfile;

CREATE OR REPLACE PROCEDURE AlterProfileToUser (
    p_username IN VARCHAR2,
    p_new_profile IN VARCHAR2
)
IS
BEGIN
    EXECUTE IMMEDIATE 'ALTER USER ' || p_username || ' PROFILE ' || p_new_profile;
    DBMS_OUTPUT.PUT_LINE('Profile ' || p_new_profile || ' duoc cap cho ' || p_username || ' thanh cong.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('That bai vs loi: ' || SQLERRM);
END AlterProfileToUser;

CREATE OR REPLACE PROCEDURE CreateProfile (
    p_profile_name          IN VARCHAR2,
    p_failed_login_attempts IN NUMBER,
    p_sessions_per_user     IN NUMBER,
    p_connect_time          IN NUMBER,
    p_idle_time             IN NUMBER,
    p_password_lock_time    IN NUMBER
) IS
BEGIN
    EXECUTE IMMEDIATE 'CREATE PROFILE ' || p_profile_name || ' LIMIT ' ||
                      'FAILED_LOGIN_ATTEMPTS ' || p_failed_login_attempts || ' ' ||
                      'SESSIONS_PER_USER ' || p_sessions_per_user || ' ' ||
                      'CONNECT_TIME ' || p_connect_time || ' ' ||
                      'IDLE_TIME ' || p_idle_time || ' ' ||
                      'PASSWORD_LOCK_TIME ' || p_password_lock_time;
    
    DBMS_OUTPUT.PUT_LINE('Profile ' || p_profile_name || ' created successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END CreateProfile;
/
BEGIN
    CreateProfile('NhanVienTEST',3,3,45,5,5);
END;
/
CREATE OR REPLACE PROCEDURE DropProfile (
    p_profile_name IN VARCHAR2
) AS
BEGIN
    EXECUTE IMMEDIATE 'DROP PROFILE ' || p_profile_name;
    DBMS_OUTPUT.PUT_LINE('Profile ' || p_profile_name || ' dropped successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END DropProfile;
/
CREATE OR REPLACE PROCEDURE UpdateProfile (
    p_profile_name          IN VARCHAR2,
    p_failed_login_attempts IN NUMBER,
    p_sessions_per_user     IN NUMBER,
    p_connect_time          IN NUMBER,
    p_idle_time             IN NUMBER,
    p_password_lock_time    IN NUMBER
) IS
BEGIN
    EXECUTE IMMEDIATE 'ALTER PROFILE ' || p_profile_name || ' LIMIT ' ||
                      'FAILED_LOGIN_ATTEMPTS ' || p_failed_login_attempts || ' ' ||
                      'SESSIONS_PER_USER ' || p_sessions_per_user || ' ' ||
                      'CONNECT_TIME ' || p_connect_time || ' ' ||
                      'IDLE_TIME ' || p_idle_time || ' ' ||
                      'PASSWORD_LOCK_TIME ' || p_password_lock_time;            
    DBMS_OUTPUT.PUT_LINE('Profile ' || p_profile_name || ' cap nhat thanh cong.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('That bai vs loi: ' || SQLERRM);
END UpdateProfile;
/

DROP PROCEDURE CapQuyenUser;

--Thu tuc cap quyen
CREATE OR REPLACE PROCEDURE CapQuyenUser (
    p_tenBang IN VARCHAR2,
    p_manv IN VARCHAR2,
    p_permission IN VARCHAR2
)
AS
BEGIN
    IF p_permission = 'SELECT' THEN
        EXECUTE IMMEDIATE 'GRANT SELECT ON NV001.' || p_tenBang || ' TO ' || p_manv;
    ELSIF p_permission = 'UPDATE' THEN
        EXECUTE IMMEDIATE 'GRANT UPDATE ON NV001.' || p_tenBang || ' TO ' || p_manv;
    ELSIF p_permission = 'INSERT' THEN
        EXECUTE IMMEDIATE 'GRANT INSERT ON NV001.' || p_tenBang || ' TO ' || p_manv;
    ELSIF p_permission = 'DELETE' THEN
        EXECUTE IMMEDIATE 'GRANT DELETE ON NV001.' || p_tenBang || ' TO ' || p_manv;
    ELSE
        DBMS_OUTPUT.PUT_LINE('Invalid permission');
    END IF;
END;
/

BEGIN
    CapQuyenUser('your_table_name', 'your_user_id', 'SELECT');
END;
/   

--Thu tuc thu hoi quyen
DROP PROCEDURE ThuHoiQuyenUser

CREATE OR REPLACE PROCEDURE ThuHoiQuyenUser (
    p_tenBang IN VARCHAR2,
    p_manv IN VARCHAR2,
    p_permission IN VARCHAR2
)
AS
BEGIN
    IF p_permission = 'SELECT' THEN
        EXECUTE IMMEDIATE 'REVOKE SELECT ON NV001.' || p_tenBang || ' FROM ' || p_manv;
    ELSIF p_permission = 'UPDATE' THEN
        EXECUTE IMMEDIATE 'REVOKE UPDATE ON NV001.' || p_tenBang || ' FROM ' || p_manv;
    ELSIF p_permission = 'INSERT' THEN
        EXECUTE IMMEDIATE 'REVOKE INSERT ON NV001.' || p_tenBang || ' FROM ' || p_manv;
    ELSIF p_permission = 'DELETE' THEN
        EXECUTE IMMEDIATE 'REVOKE DELETE ON NV001.' || p_tenBang || ' FROM ' || p_manv;
    ELSE
        DBMS_OUTPUT.PUT_LINE('Invalid permission');
    END IF;
END;
/
BEGIN
    ThuHoiQuyenUser('your_table_name', 'your_user_id', 'SELECT');
END;
/


DROP PROCEDURE PROC_ACCOUNT_UNLOCK

--Thu tuc mo khoa tai khoan
CREATE OR REPLACE PROCEDURE PROC_ACCOUNT_UNLOCK (
    p_manv IN VARCHAR2
)
AS
BEGIN
    EXECUTE IMMEDIATE 'ALTER USER ' || p_manv || ' ACCOUNT UNLOCK';
END;
/

DROP PROCEDURE PROC_ACCOUNT_LOCK

--Thu tuc khoa tai khoan
CREATE OR REPLACE PROCEDURE PROC_ACCOUNT_LOCK (
    p_manv IN VARCHAR2
)
AS
BEGIN
    EXECUTE IMMEDIATE 'ALTER USER ' || p_manv || ' ACCOUNT LOCK';
END;
/

SELECT username, TO_CHAR(timestamp, 'YYYY-MM-DD HH24:MI:SS') AS formatted_timestamp, obj_name, action_name 
FROM dba_audit_trail 
WHERE username = 'NV001' OR obj_name = 'NHANVIEN'
GROUP BY username, timestamp, obj_name, action_name
/
SELECT GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE 
FROM USER_TAB_PRIVS 
WHERE GRANTEE != 'SYS' AND PRIVILEGE IN ('SELECT', 'UPDATE', 'DELETE', 'INSERT')
/


DROP PROCEDURE CNQ
--Thu tuc cap nhom quyen cho nhan vien
CREATE OR REPLACE PROCEDURE CNQ (p_role IN VARCHAR2, p_manv IN VARCHAR2) AS
BEGIN
  IF p_role = 'QT' THEN
    EXECUTE IMMEDIATE 'GRANT QuanTriVien_ROLE TO ' || p_manv;
  ELSIF p_role = 'TN' THEN
    EXECUTE IMMEDIATE 'GRANT NhanVienThuNgan_ROLE TO ' || p_manv;
  ELSIF p_role = 'QL' THEN
    EXECUTE IMMEDIATE 'GRANT QuanLy_ROLE TO ' || p_manv;
  ELSIF p_role = 'GV' THEN
    EXECUTE IMMEDIATE 'GRANT GiaoVien_ROLE TO ' || p_manv;
  ELSE
    DBMS_OUTPUT.PUT_LINE('Chuc nang khong hop le');
  END IF;
END CNQ;
/


DROP PROCEDURE THNQ
--Thu tuc thu hoi nhom quyen cho nhan vien
CREATE OR REPLACE PROCEDURE THNQ (p_role IN VARCHAR2, p_manv IN VARCHAR2) AS
BEGIN
  IF p_role = 'QT' THEN
    EXECUTE IMMEDIATE 'REVOKE QuanTriVien_ROLE FROM ' || p_manv;
  ELSIF p_role = 'TN' THEN
    EXECUTE IMMEDIATE 'REVOKE NhanVienThuNgan_ROLE FROM ' || p_manv;
  ELSIF p_role = 'QL' THEN
    EXECUTE IMMEDIATE 'REVOKE QuanLy_ROLE FROM ' || p_manv;
  ELSIF p_role = 'GV' THEN
    EXECUTE IMMEDIATE 'REVOKE GiaoVien_ROLE FROM ' || p_manv;
  ELSE
    DBMS_OUTPUT.PUT_LINE('Chuc nang khong hop le');
  END IF;
END THNQ;
/

DROP PROCEDURE ADD_FGA_POLICY

CREATE OR REPLACE PROCEDURE ADD_FGA_POLICY(
    p_object_schema IN VARCHAR2,
    p_object_name   IN VARCHAR2,
    p_policy_name   IN VARCHAR2,
    p_audit_column  IN VARCHAR2,
    p_statement_type IN VARCHAR2
) IS
BEGIN
    CASE p_statement_type
        WHEN 'SELECT' THEN
            DBMS_FGA.ADD_POLICY(
                object_schema  => p_object_schema,
                object_name    => p_object_name,
                policy_name    => p_policy_name,
                audit_column   => p_audit_column,
                statement_types => 'SELECT',
                audit_trail    => DBMS_FGA.DB + DBMS_FGA.EXTENDED
            );

        WHEN 'INSERT' THEN
            DBMS_FGA.ADD_POLICY(
                object_schema  => p_object_schema,
                object_name    => p_object_name,
                policy_name    => p_policy_name,
                audit_column   => p_audit_column,
                statement_types => 'INSERT',
                audit_trail    => DBMS_FGA.DB + DBMS_FGA.EXTENDED
            );

        WHEN 'UPDATE' THEN
            DBMS_FGA.ADD_POLICY(
                object_schema  => p_object_schema,
                object_name    => p_object_name,
                policy_name    => p_policy_name,
                audit_column   => p_audit_column,
                statement_types => 'UPDATE',
                audit_trail    => DBMS_FGA.DB + DBMS_FGA.EXTENDED
            );

        WHEN 'DELETE' THEN
            DBMS_FGA.ADD_POLICY(
                object_schema  => p_object_schema,
                object_name    => p_object_name,
                policy_name    => p_policy_name,
                audit_column   => p_audit_column,
                statement_types => 'DELETE',
                audit_trail    => DBMS_FGA.DB + DBMS_FGA.EXTENDED
            );

        ELSE
            DBMS_OUTPUT.PUT_LINE('Invalid statement type');
    END CASE;
END ADD_FGA_POLICY;
/

CREATE OR REPLACE PROCEDURE DROP_FGA_POLICY(
    p_object_schema IN VARCHAR2,
    p_object_name   IN VARCHAR2,
    p_policy_name   IN VARCHAR2
) IS
BEGIN
    DBMS_FGA.DROP_POLICY(
        object_schema => p_object_schema,
        object_name   => p_object_name,
        policy_name   => p_policy_name
    );
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END DROP_FGA_POLICY;
/
--Thu tuc tao doi tuong thu muc
CREATE OR REPLACE PROCEDURE CREATE_DIRECTORY_PROC (
    p_directory_name IN VARCHAR2,
    p_directory_path IN VARCHAR2
)
IS
BEGIN
    EXECUTE IMMEDIATE 'CREATE DIRECTORY ' || p_directory_name || ' AS ' || '''' || p_directory_path || '''';
    
    DBMS_OUTPUT.PUT_LINE('Directory ' || p_directory_name || ' created successfully.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END CREATE_DIRECTORY_PROC;
/
BEGIN
    NV001.CREATE_DIRECTORY_PROC('HUYNE', 'C:\Users\TAN HUY\Downloads\test');
END;
/

DROP PROCEDURE LAYCHUCVU

CREATE OR REPLACE PROCEDURE LAYCHUCVU(
    p_MaNV VARCHAR2,
    p_ChucVu OUT VARCHAR2
) AS
BEGIN
    SELECT ChucVu
    INTO p_ChucVu
    FROM NV001.NhanVien
    WHERE MaNV = p_MaNV;
END LAYCHUCVU;
/

SET SERVEROUTPUT ON;
DECLARE
    v_ChucVu VARCHAR2(50);
BEGIN
    LAYCHUCVU('NV001', v_ChucVu);
    DBMS_OUTPUT.PUT_LINE('Ch?c v?: ' || v_ChucVu);
END;
/

DROP PROCEDURE ThemDiemDanh

CREATE OR REPLACE PROCEDURE ThemDiemDanh (
    p_NgayDiemDanh DATE,
    p_MaHS VARCHAR2,
    p_TenHS VARCHAR2,
    p_TrangThaiDiemDanh VARCHAR2,
    p_GhiChu VARCHAR2,
    p_MaNV VARCHAR2,
    p_MaLop VARCHAR2
) AS
    v_HasPermission NUMBER;
BEGIN
    -- Kiem tra quyen truy cap
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'DIEMDANH') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'DIEMDANH');
    
    IF v_HasPermission > 0 THEN
        -- Thuc hien them ban ghi
        INSERT INTO NV001.DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop) 
        VALUES (p_NgayDiemDanh, p_MaHS, p_TenHS, p_TrangThaiDiemDanh, p_GhiChu, p_MaNV, p_MaLop);
        
        COMMIT;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
END ThemDiemDanh;
/

DROP PROCEDURE UpdateDiemDanh

CREATE OR REPLACE PROCEDURE UpdateDiemDanh (
    p_NgayDiemDanh DATE,
    p_MaHS VARCHAR2,
    p_TenHS VARCHAR2,
    p_TrangThaiDiemDanh VARCHAR2,
    p_GhiChu VARCHAR2,
    p_MaNV VARCHAR2,
    p_MaLop VARCHAR2
) AS
    v_HasPermission NUMBER;
BEGIN
    -- Kiem tra quyen truy cap
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'DIEMDANH') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'DIEMDANH');
    
    IF v_HasPermission > 0 THEN
        -- Thuc hien them ban ghi
        UPDATE NV001.DiemDanh
        SET TrangThaiDiemDanh = p_TrangThaiDiemDanh,
            GhiChu = p_GhiChu,
            MaNV = p_MaNV,
            MaLop = p_MaLop
        WHERE NgayDiemDanh = p_NgayDiemDanh
            AND MaHS = p_MaHS;
        
        COMMIT;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
END UpdateDiemDanh;
/

-- NHAN VIEN (DK KHAU PHAN AN)

DROP PROCEDURE XoaThongTinComBoMonAn

CREATE OR REPLACE PROCEDURE XoaThongTinComBoMonAn(
    p_MaNV VARCHAR2
)AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'QUANLY_ROLE' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'COMBOMONAN') OR
          (GRANTEE = 'p_MaNV' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'COMBOMONAN');
    IF v_HasPermission > 0 THEN        
        DELETE FROM ComBoMonAn;
        COMMIT;
    ELSE
        -- Thông báo n?u không có quy?n truy c?p
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
END XoaThongTinComBoMonAn;
/

EXEC XoaThongTinComBoMonAn;

-- CHAY LOI
--th m d? li?u v o b?ng combo m n ?n 2 *
DROP PROCEDURE ThemDuLieuVaoComBoMonAn

CREATE OR REPLACE PROCEDURE ThemDuLieuVaoComBoMonAn (
    p_TenMon1 VARCHAR2,
    p_TenMon2 VARCHAR2,
    p_TenMon3 VARCHAR2,
    p_TenMon4 VARCHAR2,
    p_LoaiComBoMon VARCHAR2,
    p_ThuTrongTuan NUMBER,
    p_GiaTien FLOAT,
    p_QRCode VARCHAR2
) AS
BEGIN
    INSERT INTO ComBoMonAn (
        TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode
    ) VALUES (
        p_TenMon1, p_TenMon2, p_TenMon3, p_TenMon4, p_LoaiComBoMon, p_ThuTrongTuan, p_GiaTien, p_QRCode
    );
    COMMIT;
END ThemDuLieuVaoComBoMonAn;
/
EXEC ThemDuLieuVaoComBoMonAn('CB001', 'Th?t kho', 'Canh chua ca l c', 'Rau c?i lu?c', 'M?n', 'M?n', 2, 25000);

-- xoa bang combo mon an hien thi 3
DROP PROCEDURE XoaThongTinComBoMonAnHienThi

CREATE OR REPLACE PROCEDURE XoaThongTinComBoMonAnHienThi AS
BEGIN
    DELETE FROM ComBoMonAnHienThi;
    COMMIT;
END XoaThongTinComBoMonAnHienThi;
/
EXEC XoaThongTinComBoMonAnHienThi;


--xoa 1 khau phan cua bang combo mon an 4 
DROP PROCEDURE XoaMotKP_ComBoMon

CREATE OR REPLACE PROCEDURE XoaMotKP_ComBoMon(p_MaComBoMon VARCHAR2) IS
BEGIN
    DELETE FROM ComBoMonAn WHERE MaComBoMon = p_MaComBoMon;
    COMMIT;
END XoaMotKP_ComBoMon;
/
--xoa 1 khau phan cua bang combo mon an hien thi 5
DROP PROCEDURE XoaMotKP_ComBoMonHienThi

CREATE OR REPLACE PROCEDURE XoaMotKP_ComBoMonHienThi(p_MaComBoMon VARCHAR2) IS
BEGIN
    DELETE FROM ComBoMonAnHienThi WHERE MaComBoMon = p_MaComBoMon;
    COMMIT;
END XoaMotKP_ComBoMonHienThi;
/
BEGIN
    XoaMotKP_ComBoMonHienThi('CB016');
END;
/
--Them khau phan an combomonhienthi 6
DROP PROCEDURE ThemDuLieuVaoComBoMonAnHienThi

CREATE OR REPLACE PROCEDURE ThemDuLieuVaoComBoMonAnHienThi (
    p_Macb VARCHAR2,
    p_TenMon1 VARCHAR2,
    p_TenMon2 VARCHAR2,
    p_TenMon3 VARCHAR2,
    p_TenMon4 VARCHAR2,
    p_LoaiComBoMon VARCHAR2,
    p_ThuTrongTuan NUMBER,
    p_GiaTien FLOAT,
    p_QRCode VARCHAR2
) AS
BEGIN
    INSERT INTO ComBoMonAnHienThi (
        MaComBoMon, TenMon1, TenMon2, TenMon3, TenMon4, LoaiComBoMon, ThuTrongTuan, GiaTien, QRCode 
    ) VALUES (
        p_Macb, p_TenMon1, p_TenMon2, p_TenMon3, p_TenMon4, p_LoaiComBoMon, 
        p_ThuTrongTuan, p_GiaTien, p_QRCode
    );
    COMMIT;
END ThemDuLieuVaoComBoMonAnHienThi;
/
BEGIN
    ThemDuLieuVaoComBoMonAnHienThi('CB023', 'Mon1', 'Mon2', 'Mon3', 'Mon4', 'Loai1', 2, 10, 'QRCode1');
END;
/


-- chinh sua bang combo mon an 7 *
DROP PROCEDURE UpdateComBoMonAn

CREATE OR REPLACE PROCEDURE UpdateComBoMonAn (
    p_MaComBoMon VARCHAR2,
    p_TenMon1 VARCHAR2,
    p_TenMon2 VARCHAR2,
    p_TenMon3 VARCHAR2,
    p_TenMon4 VARCHAR2,
    p_LoaiComBoMon VARCHAR2,
    p_ThuTrongTuan NUMBER,
    p_GiaTien FLOAT, 
    p_QRCode VARCHAR2
) AS
BEGIN
    UPDATE ComBoMonAn
    SET
        TenMon1 = p_TenMon1,
        TenMon2 = p_TenMon2,
        TenMon3 = p_TenMon3,
        TenMon4 = p_TenMon4,
        LoaiComBoMon = p_LoaiComBoMon,
        ThuTrongTuan = p_ThuTrongTuan,
        GiaTien = p_GiaTien,
        QRCode = p_QRCode
    WHERE MaComBoMon = p_MaComBoMon;

    COMMIT;
END UpdateComBoMonAn;
/
EXEC UpdateComBoMonAn('CB001', 'NewMon1', 'NewMon2', 'NewMon3', 'NewMon4', 'NewLoai', 5, 30000);

--HOC SINH( DK KHAU PHAN AN)

DROP PROCEDURE InsertDangKyKhauPhanAn

CREATE OR REPLACE PROCEDURE InsertDangKyKhauPhanAn(
    p_MaHS VARCHAR2,
    p_NgayDangKyKP VARCHAR2,
    p_ThuTrongTuan int,
    p_Gia float,
    p_MaComBoMon VARCHAR2, 
    p_QRCode VARCHAR2,
    p_MaNV VARCHAR2
) AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'QUANLY_ROLE' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DANGKYKHAUPHANAN') OR
          (GRANTEE = 'p_MaNV' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DANGKYKHAUPHANAN');
    IF v_HasPermission > 0 THEN
        INSERT INTO DangKyKhauPhanAn (MaHS, NgayDangKyKP,ThuTrongTuan, Gia, MaComBoMon, QRCode)
        VALUES (p_MaHS, p_NgayDangKyKP, p_ThuTrongTuan, p_Gia, p_MaComBoMon, p_QRCode);
        COMMIT;
    ELSE
        -- Thông báo n?u không có quy?n truy c?p
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
END InsertDangKyKhauPhanAn;
/
BEGIN
    InsertDangKyKhauPhanAn('HS001', '2023-11-22', '2', '20000', 'CB023', 'aaaaa');
END;
/

--- kt user dang thuc thi 9
DROP PROCEDURE GetUserName

CREATE OR REPLACE PROCEDURE GetUserName(p_username OUT VARCHAR2)
IS
BEGIN
  SELECT USER INTO p_username FROM DUAL;
END GetUserName;
/
DECLARE
  v_username VARCHAR2(30);
BEGIN
  -- G?i th? t?c v? nh?n gi? tr? tr? v?
  GetUserName(v_username);
  -- In t?n ng??i d?ng
  DBMS_OUTPUT.PUT_LINE(v_username);
END;
/

---- xoa thong tin dang ki 10
DROP PROCEDURE XoaThongTinDangKi

CREATE OR REPLACE PROCEDURE XoaThongTinDangKi(
    p_MaNV VARCHAR2
)AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'QUANLY_ROLE' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DANGKYKHAUPHANAN') OR
          (GRANTEE = 'p_MaNV' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DANGKYKHAUPHANAN');
    IF v_HasPermission > 0 THEN
        DELETE FROM dangkykhauphanan;
        COMMIT;
    ELSE
        -- Thông báo n?u không có quy?n truy c?p
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
END XoaThongTinDangKi;
/



---- xoa thong tin dang ki cua mot khau phan
DROP PROCEDURE XoaDangKy_MotKhauPhanAn

CREATE OR REPLACE PROCEDURE XoaDangKy_MotKhauPhanAn(
    p_MaHS IN VARCHAR2,
    p_NgayDangKyKP IN VARCHAR2,
    p_ThuTrongTuan IN INT,
    p_MaNV VARCHAR2
) AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'QUANLY_ROLE' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'COMBOMONAN') OR
          (GRANTEE = 'p_MaNV' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'COMBOMONAN');
    IF v_HasPermission > 0 THEN
        DELETE FROM DangKyKhauPhanAn
        WHERE MaHS = p_MaHS
          AND NgayDangKyKP = p_NgayDangKyKP
          AND ThuTrongTuan = p_ThuTrongTuan;
        COMMIT;
    ELSE
        -- Thông báo n?u không có quy?n truy c?p
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
END XoaDangKy_MotKhauPhanAn;
/
BEGIN
    XoaDangKy_MotKhauPhanAn('HS001', '2023-11-27', '3');
END;
/

-- trigger rang buoc viec chinh san thong tin dang ki
-- cho phep chinh sua truoc t7 hang tuan
DROP TRIGGER rangBuocChinhSuaDangKy

CREATE OR REPLACE TRIGGER rangBuocChinhSuaDangKy
BEFORE DELETE ON DangKyKhauPhanAn
FOR EACH ROW
DECLARE
    v_CurrentDayOfWeek INT;
BEGIN
    -- L?y ng?y hi?n t?i v? chuy?n ??i th?nh s? ng?y trong tu?n
    v_CurrentDayOfWeek := TO_NUMBER(TO_CHAR(SYSDATE, 'D'));

    -- Ki?m tra n?u ng?y hi?n t?i kh?ng n?m trong kho?ng t? th? 3 ??n th? 6
    IF v_CurrentDayOfWeek NOT BETWEEN 2 AND 6 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Ch? ???c ph?p x?a d? li?u v?o th? 3, 4, 5, ho?c 6.');
    END IF;
END rangBuocChinhSuaDangKy;
/
-- chinh sua bang combohien thi
DROP PROCEDURE UpdateComBoMonAnHienThi

CREATE OR REPLACE PROCEDURE UpdateComBoMonAnHienThi (
    p_MaComBoMon VARCHAR2,
    p_TenMon1 VARCHAR2,
    p_TenMon2 VARCHAR2,
    p_TenMon3 VARCHAR2,
    p_TenMon4 VARCHAR2,
    p_LoaiComBoMon VARCHAR2,
    p_ThuTrongTuan NUMBER,
    p_GiaTien FLOAT,
    p_QRCode VARCHAR2
) AS
BEGIN
    UPDATE ComBoMonAnHienThi
    SET
        TenMon1 = p_TenMon1,
        TenMon2 = p_TenMon2,
        TenMon3 = p_TenMon3,
        TenMon4 = p_TenMon4,
        LoaiComBoMon = p_LoaiComBoMon,
        ThuTrongTuan = p_ThuTrongTuan,
        GiaTien = p_GiaTien,
        QRCode = p_QRCode
    WHERE MaComBoMon = p_MaComBoMon;

    COMMIT;
END UpdateComBoMonAnHienThi;
/


SET SERVEROUTPUT ON
DECLARE
    v_TwoChars VARCHAR2(2);
BEGIN
    LayHaiKiTuDau('NV001', v_TwoChars);
    DBMS_OUTPUT.PUT_LINE('First Two Characters: ' || v_TwoChars);
END;
/

DROP PROCEDURE LayHaiKiTuDau

CREATE OR REPLACE PROCEDURE LayHaiKiTuDau(
    p_Chuoi IN VARCHAR2,
    p_TwoChars OUT VARCHAR2
)
IS
BEGIN
    IF LENGTH(p_Chuoi) >= 2 THEN
        p_TwoChars := SUBSTR(p_Chuoi, 1, 2);
    ELSE
        p_TwoChars := p_Chuoi;
    END IF;
END LayHaiKiTuDau;
/

DROP PROCEDURE CapNhatThongTinNhanVien

CREATE OR REPLACE PROCEDURE CapNhatThongTinNhanVien (
    p_MaNV IN VARCHAR2,
    p_TenNV IN VARCHAR2,
    p_GioiTinh IN VARCHAR2,
    p_NgaySinh IN DATE,
    p_ChucVu IN VARCHAR2,
    p_DiaChi IN VARCHAR2,
    p_Email IN VARCHAR2,
    p_SoDT IN VARCHAR2,
    p_TrinhDo IN VARCHAR2,
    p_ChuyenMon IN VARCHAR2,
    p_NoiDaoTao IN VARCHAR2,
    p_NamTotNghiep IN NUMBER
    --p_HinhAnh IN BLOB
)
AS
BEGIN
    UPDATE NhanVien
    SET
        TenNV = p_TenNV,
        GioiTinh = p_GioiTinh,
        NgaySinh = p_NgaySinh,
        ChucVu = p_ChucVu,
        DiaChi = p_DiaChi,
        Email = p_Email,
        SoDT = p_SoDT,
        TrinhDo = p_TrinhDo,
        ChuyenMon = p_ChuyenMon,
        NoiDaoTao = p_NoiDaoTao,
        NamTotNghiep = p_NamTotNghiep
        --HinhAnh = p_HinhAnh
    WHERE MaNV = p_MaNV;
    
    COMMIT;
END CapNhatThongTinNhanVien;
/

COMMIT;



--
DROP PROCEDURE E1_ThemCauHoi

CREATE OR REPLACE PROCEDURE E1_ThemCauHoi (
    p_MaCauHoi VARCHAR2,
    p_CauHoi VARCHAR2,
    p_DapAn_A VARCHAR2,
    p_DapAn_B VARCHAR2,
    p_DapAn_C VARCHAR2,
    p_DapAn_D VARCHAR2,
    p_DapAnDung VARCHAR2,
    p_MaMH VARCHAR2
) AS
    v_Count NUMBER;
BEGIN
    -- Kiem tra xem co ma cau hoi trung lap khong
    SELECT COUNT(*) INTO v_Count FROM NV001.CauHoi WHERE MaCauHoi = p_MaCauHoi;

    IF v_Count > 0 THEN
        -- Neu ton tai ma cau hoi trung lap, thong bao loi
        DBMS_OUTPUT.PUT_LINE('Loi: Ma cau hoi da ton tai.');
    ELSE
        -- Them cau hoi vao bang CauHoi neu khong co ma cau hoi trung lap
        INSERT INTO NV001.CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
        VALUES (p_MaCauHoi, p_CauHoi, p_DapAn_A, p_DapAn_B, p_DapAn_C, p_DapAn_D, p_DapAnDung, p_MaMH);

        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Them cau hoi thanh cong.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;   -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_ThemCauHoi;
/

DROP PROCEDURE E1_ThemChiTietDeThi

CREATE OR REPLACE PROCEDURE E1_ThemChiTietDeThi (
    p_MaDeThi VARCHAR2,
    p_MaCauHoi VARCHAR2,
    p_NgayThi DATE,
    p_ThoiGianThi VARCHAR2,
    p_ThoiGianBatDau VARCHAR2,
    p_ThoiGianKetThuc VARCHAR2
) AS
    v_Count_DeThi NUMBER;
    v_Count_CauHoi NUMBER;
BEGIN
    -- Kiem tra xem ma de thi da ton tai chua
    SELECT COUNT(*) INTO v_Count_DeThi FROM DeThi WHERE MaDeThi = p_MaDeThi;
    IF v_Count_DeThi = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Loi: Ma de thi khong ton tai.');
        RETURN;
    END IF;

    -- Kiem tra xem ma cau hoi da ton tai chua
    SELECT COUNT(*) INTO v_Count_CauHoi FROM CauHoi WHERE MaCauHoi = p_MaCauHoi;
    IF v_Count_CauHoi = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Loi: Ma cau hoi khong ton tai.');
        RETURN;
    END IF;

    -- Them chi tiet de thi vao bang ChiTietDeThi
    INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
    VALUES (p_MaDeThi, p_MaCauHoi, p_NgayThi, p_ThoiGianThi, p_ThoiGianBatDau, p_ThoiGianKetThuc);

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Them chi tiet de thi thanh cong.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK; -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_ThemChiTietDeThi;
/

DROP PROCEDURE ThemKetQua

CREATE OR REPLACE PROCEDURE ThemKetQua(
    p_MaHocSinh IN VARCHAR2,
    p_MaDeThi IN VARCHAR2,
    p_SoCauDung IN VARCHAR2,
    p_DiemSo IN VARCHAR2
)
AS
BEGIN
    INSERT INTO KetQua (MaHocSinh, MaDeThi, SoCauDung, DiemSo, ThoiGianLucNop)
    VALUES (p_MaHocSinh, p_MaDeThi, p_SoCauDung, p_DiemSo, CURRENT_TIMESTAMP);
    
    COMMIT;
    
    DBMS_OUTPUT.PUT_LINE('Ket qua da duoc them thanh cong.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Da xay ra loi khi them ket qua.');
END;
/


BEGIN
    ThemKetQua('HS006', 'DT001-TOAN', '6', '6.5');
END;
/

--30/04/2024
CREATE OR REPLACE PROCEDURE E1_ThemCauHoi (
    p_MaCauHoi VARCHAR2,
    p_CauHoi VARCHAR2,
    p_DapAn_A VARCHAR2,
    p_DapAn_B VARCHAR2,
    p_DapAn_C VARCHAR2,
    p_DapAn_D VARCHAR2,
    p_DapAnDung VARCHAR2,
    p_MaMH VARCHAR2,
    p_MaNV VARCHAR2
) AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'CAUHOI') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'CAUHOI');
          
    -- Kiem tra xem co ma cau hoi trung lap khong
    SELECT COUNT(*) INTO v_Count FROM NV001.CauHoi WHERE MaCauHoi = p_MaCauHoi;

    IF v_Count > 0 THEN
        -- Neu ton tai ma cau hoi trung lap, thong bao loi
        DBMS_OUTPUT.PUT_LINE('Loi: Ma cau hoi da ton tai.');
    ELSE
        -- Them cau hoi vao bang CauHoi neu khong co ma cau hoi trung lap
        IF v_HasPermission > 0 THEN
            INSERT INTO NV001.CauHoi (MaCauHoi, CauHoi, DapAn_A, DapAn_B, DapAn_C, DapAn_D, DapAnDung, MaMH)
            VALUES (p_MaCauHoi, p_CauHoi, p_DapAn_A, p_DapAn_B, p_DapAn_C, p_DapAn_D, p_DapAnDung, p_MaMH);
    
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Them cau hoi thanh cong.');
        ELSE
            DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
        END IF;
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;   -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_ThemCauHoi;
/

set serveroutput on
EXEC E1_ThemCauHoi('CH001', 'C?u h?i 1', '??p ?n A', '??p ?n B', '??p ?n C', '??p ?n D', 'A', 'MH001-TOAN');

select * from NV001.CauHoi
delete from NV001.CauHoi
commit

--------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE E1_SuaCauHoi

CREATE OR REPLACE PROCEDURE E1_SuaCauHoi (
    p_MaCauHoi VARCHAR2,
    p_CauHoi VARCHAR2,
    p_DapAn_A VARCHAR2,
    p_DapAn_B VARCHAR2,
    p_DapAn_C VARCHAR2,
    p_DapAn_D VARCHAR2,
    p_DapAnDung VARCHAR2,
    p_MaMH VARCHAR2,
    p_MaNV VARCHAR2
) AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    -- Ki?m tra quy?n truy c?p
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CAUHOI') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CAUHOI');

    IF v_HasPermission > 0 THEN      
        -- Ki?m tra xem mã câu h?i ?ã t?n t?i ch?a
        SELECT COUNT(*) INTO v_Count FROM NV001.CauHoi WHERE MaCauHoi = p_MaCauHoi;

        IF v_Count = 0 THEN
            -- Thông báo n?u mã câu h?i không t?n t?i
            DBMS_OUTPUT.PUT_LINE('L?i: Mã câu h?i không t?n t?i.');
        ELSE
            -- C?p nh?t thông tin câu h?i n?u mã câu h?i t?n t?i
            UPDATE NV001.CauHoi 
            SET CauHoi = p_CauHoi,
                DapAn_A = p_DapAn_A,
                DapAn_B = p_DapAn_B,
                DapAn_C = p_DapAn_C,
                DapAn_D = p_DapAn_D,
                DapAnDung = p_DapAnDung,
                MaMH = p_MaMH
            WHERE MaCauHoi = p_MaCauHoi;
        
            -- Th?c hi?n commit
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('S?a câu h?i thành công');            
        END IF;
    ELSE
        -- Thông báo n?u không có quy?n truy c?p
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p và th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        -- Rollback n?u có l?i x?y ra
        ROLLBACK; 
        DBMS_OUTPUT.PUT_LINE('L?i: ' || SQLERRM);
END E1_SuaCauHoi;
/

set serveroutput on
EXEC E1_SuaCauHoi('1', 'C?u h?i m?i ?? ???c s?a', '??p ?n A m?i', '??p ?n B m?i', '??p ?n C m?i', '??p ?n D m?i', 'B', 'MH001-TOAN');

select * from NV001.CAUHOI

-----------------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE E1_XoaCauHoi

CREATE OR REPLACE PROCEDURE E1_XoaCauHoi (
    p_MaCauHoi VARCHAR2,
    p_MaNV VARCHAR2
)AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'CAUHOI') OR
          (GRANTEE = 'p_MaNV' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'CAUHOI');  
    IF v_HasPermission > 0 THEN        
        -- Kiem tra xem ma cau hoi da ton tai chua
        SELECT COUNT(*) INTO v_Count FROM NV001.CauHoi WHERE MaCauHoi = p_MaCauHoi;
    
        IF v_Count = 0 THEN
            -- Neu khong ton tai thong bao loi
            DBMS_OUTPUT.PUT_LINE('Loi: Ma cau hoi khong ton tai.');
        ELSE
            -- Xoa cac ban ghi trong bang ChiTietDeThi lien quan den cau hoi
                DELETE FROM NV001.ChiTietDeThi WHERE MaCauHoi = p_MaCauHoi;
    
            -- Xoa cau hoi sau khi da xoa cac lien ket trong ChiTietDeThi       
                DELETE FROM NV001.CauHoi WHERE MaCauHoi = p_MaCauHoi;
        
                COMMIT;
                DBMS_OUTPUT.PUT_LINE('Xoa cau hoi va cac lien ket thanh cong.');
        END IF;
    ELSE
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK; -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_XoaCauHoi;
/
COMMIT;
EXEC E1_XoaCauHoi('1');

--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE E1_ThemChiTietDeThi (
    p_MaDeThi VARCHAR2,
    p_MaCauHoi VARCHAR2,
    p_NgayThi DATE,
    p_ThoiGianThi VARCHAR2,
    p_ThoiGianBatDau VARCHAR2,
    p_ThoiGianKetThuc VARCHAR2,
    p_MaNV VARCHAR2
) AS
    v_Count_DeThi NUMBER;
    v_Count_CauHoi NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'CHITIETDETHI') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'CHITIETDETHI');
          
    -- Kiem tra xem ma de thi da ton tai chua
    SELECT COUNT(*) INTO v_Count_DeThi FROM DeThi WHERE MaDeThi = p_MaDeThi;
    IF v_Count_DeThi = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Loi: Ma de thi khong ton tai.');
        RETURN;
    END IF;

    -- Kiem tra xem ma cau hoi da ton tai chua
    SELECT COUNT(*) INTO v_Count_CauHoi FROM CauHoi WHERE MaCauHoi = p_MaCauHoi;
    IF v_Count_CauHoi = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Loi: Ma cau hoi khong ton tai.');
        RETURN;
    END IF;

    -- Them chi tiet de thi vao bang ChiTietDeThi
    IF v_HasPermission > 0 THEN
        INSERT INTO ChiTietDeThi (MaDeThi, MaCauHoi, NgayThi, ThoiGianThi, ThoiGianBatDau, ThoiGianKetThuc)
        VALUES (p_MaDeThi, p_MaCauHoi, p_NgayThi, p_ThoiGianThi, p_ThoiGianBatDau, p_ThoiGianKetThuc);
    
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Them chi tiet de thi thanh cong.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK; -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_ThemChiTietDeThi;
/

set serveroutput on
EXEC E1_ThemChiTietDeThi('DETHI-0004', 'CH031', TO_DATE('2024-04-17', 'YYYY-MM-DD'), '90 ph?t', '8:00:00', '8:30:00','NV001');
DROP TRIGGER UpdateDeThiTrangThai;

--------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE E1_ThemCongNo

CREATE OR REPLACE PROCEDURE E1_ThemCongNo (
    p_MaCongNo IN VARCHAR2,
    p_MaHS IN VARCHAR2,
    p_NgayTaoCongNo IN DATE,
    p_TienHocPhi IN DECIMAL,
    p_TienAn IN DECIMAL,
    p_TienPhuThu IN DECIMAL,
    p_TongCongNo IN DECIMAL,
    p_TrangThai IN VARCHAR2,
    p_MaNV VARCHAR2
) AS
    l_CountCongNo NUMBER;
    l_CountHocSinh NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'CONGNO') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'CONGNO');
    --  Kiem tra xem Ma cong no da ton tai chua
    SELECT COUNT(*) INTO l_CountCongNo FROM NV001.CongNo WHERE MaCongNo = p_MaCongNo;
    IF l_CountCongNo > 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Ma cong no da ton tai.');
    END IF;
    
    -- Kiem tra xem Ma hoc sinh da ton tai chua
    SELECT COUNT(*) INTO l_CountHocSinh FROM NV001.HocSinh WHERE MaHS = p_MaHS;
    IF l_CountHocSinh = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Ma hoc sinh khong ton tai.');
    END IF;
    
    -- Kiem tra tien hoc phi tien an tien phu thu tong cong no phai lon hon 0
    IF p_TienHocPhi < 0 OR p_TienAn < 0 OR p_TienPhuThu < 0 OR p_TongCongNo < 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Tien hoc phi, tien an, tien phu thu, tong cong no phai lon hon 0.');
    END IF;

    -- Them ban ghi moi vao bang CongNo
    IF v_HasPermission > 0 THEN
        INSERT INTO NV001.CongNo (MaCongNo, MaHS, NgayTaoCongNo, TienHocPhi, TienAn, TienPhuThu, TongCongNo, TrangThai)
        VALUES (p_MaCongNo, p_MaHS, p_NgayTaoCongNo, p_TienHocPhi, p_TienAn, p_TienPhuThu, p_TongCongNo, p_TrangThai);
        
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Da them cong no moi thanh cong.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Loi khi them cong no: ' || SQLERRM);
END;
/

--------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE E1_SuaCongNo

CREATE OR REPLACE PROCEDURE E1_SuaCongNo (
    p_MaCongNo IN VARCHAR2,
    p_MaHS IN VARCHAR2,
    p_NgayTaoCongNo IN DATE,
    p_TienHocPhi IN DECIMAL,
    p_TienAn IN DECIMAL,
    p_TienPhuThu IN DECIMAL,
    p_TongCongNo IN DECIMAL,
    p_TrangThai IN VARCHAR2,
    p_MaNV VARCHAR2
) AS
    l_CountCongNo NUMBER;
    l_CountHocSinh NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CONGNO') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CONGNO');
          
    --  Kiem tra xem Ma cong no da ton tai hay khong
    SELECT COUNT(*) INTO l_CountCongNo FROM NV001.CongNo WHERE MaCongNo = p_MaCongNo;
    IF l_CountCongNo = 0 THEN
        -- Neu khong ton tai thong bao loi
        RAISE_APPLICATION_ERROR(-20001, 'Ma cong no khong ton tai.');
    END IF;
    
    -- Kiem tra xem Ma hoc sinh da ton tai chua
    SELECT COUNT(*) INTO l_CountHocSinh FROM NV001.HocSinh WHERE MaHS = p_MaHS;
    IF l_CountHocSinh = 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 'Ma hoc sinh khong ton tai.');
    END IF;

    -- Kiem tra cac gia tri dau vao
    IF p_TienHocPhi < 0 OR p_TienAn < 0 OR p_TienPhuThu < 0 OR p_TongCongNo < 0 THEN
        RAISE_APPLICATION_ERROR(-20003, 'Gia tri tien hoc phi tien an tien phu thu tong cong no phai lon hon hoac bang 0.');
    END IF;

    -- Sua thong tin cong no
    IF v_HasPermission > 0 THEN
        UPDATE NV001.CongNo
        SET MaHS = p_MaHS,
            NgayTaoCongNo = p_NgayTaoCongNo,
            TienHocPhi = p_TienHocPhi,
            TienAn = p_TienAn,
            TienPhuThu = p_TienPhuThu,
            TongCongNo = p_TongCongNo,
            TrangThai = p_TrangThai
        WHERE MaCongNo = p_MaCongNo;    
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Da cap nhat thong tin cong no thanh cong.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Loi khi cap nhat thong tin cong no: ' || SQLERRM);
END;
/

--------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE E1_XoaCongNo

CREATE OR REPLACE PROCEDURE E1_XoaCongNo (
    p_MaCongNo IN VARCHAR2,
    p_MaNV VARCHAR2
) AS
    l_CountCongNo NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'CONGNO') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'CONGNO');
    -- Kiem tra xem Ma cong no co ton tai khong
    SELECT COUNT(*) INTO l_CountCongNo FROM NV001.CongNo WHERE MaCongNo = p_MaCongNo;
    IF l_CountCongNo = 0 THEN
        -- Neu khong ton tai thong bao loi
        RAISE_APPLICATION_ERROR(-20001, 'Ma cong no khong ton tai.');
    END IF;

    -- Xoa ban ghi cong no
    IF v_HasPermission > 0 THEN
        DELETE FROM NV001.CongNo WHERE MaCongNo = p_MaCongNo;
        COMMIT;
        DBMS_OUTPUT.PUT_LINE('Da xoa cong no thanh cong.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Loi khi xoa cong no: ' || SQLERRM);
END;
/

--------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE v1_XoaAllTheoMaDeThi

CREATE OR REPLACE PROCEDURE v1_XoaAllTheoMaDeThi (
    p_MaDeThi IN VARCHAR2,
    p_MaNV VARCHAR2
)
AS
    v_HasPermission NUMBER;
BEGIN
    -- Ki?m tra quy?n h?n
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_XOADETHI') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DETHI');
    
    IF v_HasPermission > 0 THEN     
        -- Xác ??nh các dòng trong CauHoi tham chi?u ??n ChiTietDeThi
        FOR chitiet IN (SELECT MaCauHoi FROM NV001.ChiTietDeThi WHERE MaDeThi = p_MaDeThi) LOOP
            -- Xóa các dòng t? CauHoi không tham chi?u ??n ChiTietDeThi
            DELETE FROM NV001.CauHoi
            WHERE MaCauHoi = chitiet.MaCauHoi
            AND NOT EXISTS (
                SELECT 1
                FROM NV001.ChiTietDeThi
                WHERE CauHoi.MaCauHoi = ChiTietDeThi.MaCauHoi
                AND ChiTietDeThi.MaDeThi = p_MaDeThi
            );
        END LOOP;

        -- Xóa t?t c? các dòng t? ChiTietDeThi
        DELETE FROM NV001.ChiTietDeThi WHERE MaDeThi = p_MaDeThi;

        -- Xóa t?t c? các câu h?i không ???c tham chi?u t? b?ng ChiTietDeThi
        DELETE FROM NV001.CauHoi WHERE MaCauHoi NOT IN (SELECT MaCauHoi FROM NV001.ChiTietDeThi);
        
        COMMIT;    
        -- Thông báo khi th?c hi?n xong
        DBMS_OUTPUT.PUT_LINE('?ã xóa d? li?u thành công.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        -- X? lý ngo?i l? và rollback
        DBMS_OUTPUT.PUT_LINE('L?i: ' || SQLERRM);
        ROLLBACK;
END;
/


commit

----------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE E1_ThemDeThi (
    p_MaDeThi VARCHAR2,
    p_NgaySoanDe DATE,
    p_SoLuongCauHoi VARCHAR2,
    p_TrangThai VARCHAR2,
    p_MaNV VARCHAR2,
    p_MaMH VARCHAR2
) AS
    v_HasPermission NUMBER;
BEGIN
        SELECT COUNT(*) INTO v_HasPermission
        FROM USER_TAB_PRIVS
        WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'DETHI') OR
              (GRANTEE = p_MaNV AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'DETHI');
        IF v_HasPermission > 0 THEN          
            -- Them ban ghi
            INSERT INTO NV001.DeThi (MaDeThi, NgaySoanDe, SoLuongCauHoi, TrangThai, MaNV, MaMH) 
            VALUES (p_MaDeThi, p_NgaySoanDe, p_SoLuongCauHoi, p_TrangThai, p_MaNV, p_MaMH);
            
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Them du lieu thanh cong.');
        ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK; -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_ThemDeThi;
/


EXEC E1_ThemDeThi('DETHI001', TO_DATE('2024-04-15', 'YYYY-MM-DD'), '5', '?ang So?n', 'NV002', 'MH001-TOAN');

--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE E1_SuaDeThi (
    p_MaDeThi VARCHAR2,
    p_NgaySoanDe DATE,
    p_SoLuongCauHoi VARCHAR2,
    p_TrangThai VARCHAR2,
    p_MaNV VARCHAR2,
    p_MaMH VARCHAR2
) AS
    v_KiemTraTonTai NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'DETHI') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'DETHI');
    IF v_HasPermission > 0 THEN     
        -- Cap nhat ban ghi
        UPDATE NV001.DeThi
        SET 
            NgaySoanDe = p_NgaySoanDe,
            SoLuongCauHoi = p_SoLuongCauHoi,
            TrangThai = p_TrangThai,
            MaNV = p_MaNV,
            MaMH = p_MaMH
        WHERE MaDeThi = p_MaDeThi;
        
        v_KiemTraTonTai := SQL%ROWCOUNT;
        
        IF v_KiemTraTonTai = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Ma de thi khong ton tai hoac khong co gi duoc cap nhat.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Cap nhat du lieu thanh cong.');
        END IF;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;   -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_SuaDeThi;
/

EXEC E1_SuaDeThi('DETHI-TOAN-0011', TO_DATE('2024-04-16', 'YYYY-MM-DD'), '5', '?ang So?n', 'NV001', 'MH001-TOAN');
commit
--------------------------------------------------------------------------------------------------------------------------------
-- T?o th? t?c E1_XoaDeThi
CREATE OR REPLACE PROCEDURE E1_XoaDeThi (
    p_MaDeThi VARCHAR2,
    p_MaNV VARCHAR2
) AS
    v_KiemTraTonTai NUMBER;
    v_HasPermission NUMBER;
BEGIN
    -- Ki?m tra quy?n h?n
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DETHI') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DETHI');
          
    IF v_HasPermission > 0 THEN     
        -- Xóa các b?n ghi liên quan
        DELETE FROM NV001.ChiTietDeThi
        WHERE MaDeThi = p_MaDeThi;
        
        DELETE FROM NV001.CauHoi
        WHERE MaMH IN (SELECT DISTINCT MaMH FROM NV001.DeThi WHERE MaDeThi = p_MaDeThi)
        AND NOT EXISTS (
            SELECT 1 FROM NV001.ChiTietDeThi WHERE NV001.CauHoi.MaCauHoi = NV001.ChiTietDeThi.MaCauHoi
        );
    
        DELETE FROM NV001.DeThi
        WHERE MaDeThi = p_MaDeThi;
        
        v_KiemTraTonTai := SQL%ROWCOUNT;
        
        IF v_KiemTraTonTai = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Mã ?? thi không t?n t?i ho?c không có gì ???c c?p nh?t.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Xóa d? li?u thành công.');
        END IF;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK; -- Rollback n?u có l?i x?y ra
        DBMS_OUTPUT.PUT_LINE('L?i: ' || SQLERRM);
END E1_XoaDeThi;
/

-- Th?c thi th? t?c E1_XoaDeThi v?i các tham s?
BEGIN 
    E1_XoaDeThi('DETHI-0004', 'NV001'); 
END;
/


commit

--------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------
-- LONG: Lay ma lop
DROP PROCEDURE LayMaLop

CREATE OR REPLACE PROCEDURE LayMaLop (
    mahs_in IN VARCHAR2,
    malop_out OUT VARCHAR2
)
AS
BEGIN
    SELECT MALOP INTO malop_out
    FROM NV001.HOCSINH
    WHERE MAHS = mahs_in;
    
--    SELECT MALOP
--    FROM HOCSINH
--    WHERE MAHS = 'HS001';
END LayMaLop;
/


-- Lay ten hoc sinh
DROP PROCEDURE LayTenHS

CREATE OR REPLACE PROCEDURE LayTenHS (
    mahs_in IN VARCHAR2,
    tenHS_out OUT VARCHAR2
)
AS
BEGIN
    SELECT tenhs INTO tenHS_out
    FROM NV001.HOCSINH
    WHERE MAHS = mahs_in;
    
--    SELECT tenhs
--    FROM HOCSINH
--    WHERE MAHS = 'HS001';
END LayTenHS;
/

DROP PROCEDURE NhanVien_SelectAll; -- chua chay chua dung toi
CREATE OR REPLACE PROCEDURE NhanVien_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    Select * from NV001.NHANVIEN;
END NhanVien_SelectAll;



CREATE OR REPLACE PROCEDURE KetQua_SelectAll--bo roi nha
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    Select * from NV001.KETQUA;
END KetQua_SelectAll;
COMMIT;

DROP PROCEDURE KetQua_SelectAll

CREATE OR REPLACE PROCEDURE KetQua_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_huy BOOLEAN;
BEGIN
    -- Ki?m tra n?u t?n ng??i d?ng b?t ??u b?ng "NV"
    v_huy := (SYS_CONTEXT('USERENV', 'SESSION_USER') LIKE 'NV%');

    -- M? con tr? d?a tr?n ch?nh s?ch VPD
    IF v_huy THEN
        OPEN C_cursor FOR
        SELECT * FROM NV001.KETQUA;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.KETQUA WHERE MaHocSinh = SYS_CONTEXT('USERENV', 'SESSION_USER');
    END IF;
END KetQua_SelectAll;
/

COMMIT;

DROP PROCEDURE CongNo_SelectAll

CREATE OR REPLACE PROCEDURE CongNo_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_huy BOOLEAN;
BEGIN
    -- Ki?m tra n?u t?n ng??i d?ng b?t ??u b?ng "NV"
    v_huy := (SYS_CONTEXT('USERENV', 'SESSION_USER') LIKE 'NV%');

    -- M? con tr? d?a tr?n ch?nh s?ch VPD
    IF v_huy THEN
        OPEN C_cursor FOR
        SELECT * FROM NV001.CONGNO;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.CONGNO WHERE MaHS = SYS_CONTEXT('USERENV', 'SESSION_USER');
    END IF;
END CongNo_SelectAll;
/

COMMIT;

DROP PROCEDURE MonHoc_SelectAll

CREATE OR REPLACE PROCEDURE MonHoc_SelectAll--DANG DUNG BEN WEB 
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    Select * from NV001.MONHOC;
END MonHoc_SelectAll;
/

DROP PROCEDURE DeThi_SelectAll

CREATE OR REPLACE PROCEDURE DeThi_SelectAll--DANG DUNG BEN WEB 
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    Select * from NV001.DETHI;
END DeThi_SelectAll;
/

COMMIT;
--Xuat du lieu
SET SERVEROUTPUT ON

VARIABLE my_cursor REFCURSOR
EXEC MonHoc_SelectAll(:my_cursor)

PRINT my_cursor


DROP FUNCTION DES_DECRYPT

create or replace FUNCTION DES_DECRYPT(
    p_encryptedText VARCHAR2,
    priKey VARCHAR2 DEFAULT 'TuiTenLongNeDenTuDinhQuanDongNai'
) RETURN VARCHAR2 DETERMINISTIC
AS
    encryption_type PLS_INTEGER := DBMS_CRYPTO.ENCRYPT_DES + DBMS_CRYPTO.CHAIN_CBC + DBMS_CRYPTO.PAD_PKCS5;
    encryption_key RAW(32) := UTL_RAW.CAST_TO_RAW(priKey);
    encrypted_raw RAW(2000);
    decrypted_raw RAW(2000);
BEGIN
    -- Convert the HEX string to RAW
    encrypted_raw := HEXTORAW(p_encryptedText);

    -- Decrypt the RAW data
    decrypted_raw := DBMS_CRYPTO.DECRYPT(
        src => encrypted_raw,
        typ => encryption_type,
        key => encryption_key
    );

    -- Convert the RAW data to VARCHAR2
    RETURN UTL_RAW.CAST_TO_VARCHAR2(decrypted_raw);
END DES_DECRYPT;
/


DROP FUNCTION DES_ENCRYPT

create or replace FUNCTION DES_ENCRYPT(p_plainText VARCHAR2) RETURN RAW DETERMINISTIC
AS
    encryption_type PLS_INTEGER := DBMS_CRYPTO.ENCRYPT_DES + DBMS_CRYPTO.CHAIN_CBC + DBMS_CRYPTO.PAD_PKCS5;
    encryption_key RAW(32) := UTL_RAW.CAST_TO_RAW('TuiTenLongNeDenTuDinhQuanDongNai');
    encrypted_raw RAW(2000);
BEGIN
    encrypted_raw := DBMS_CRYPTO.ENCRYPT(
        src => UTL_RAW.CAST_TO_RAW(p_plainText),
        typ => encryption_type,
        key => encryption_key
    );
    RETURN encrypted_raw;
END DES_ENCRYPT;
/

--------------------------------------------------------------------------------------------------------------------------------
-- Chèn d? li?u vào b?ng DiemDanh (du lieu test)
INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-10', 'YYYY-MM-DD'), 'HS001', 'Nguy?n V?n An', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-10', 'YYYY-MM-DD'), 'HS002', 'Hu?nh Th? Tính', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-10', 'YYYY-MM-DD'), 'HS003', 'Võ Th? H?ng Ánh', 'V?ng m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-10', 'YYYY-MM-DD'), 'HS004', 'Nguy?n Qu?nh Chi', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-10', 'YYYY-MM-DD'), 'HS005', 'Võ Lê Ánh Tuy?t', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-11', 'YYYY-MM-DD'), 'HS001', 'Nguy?n V?n An', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-11', 'YYYY-MM-DD'), 'HS002', 'Hu?nh Th? Tính', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-11', 'YYYY-MM-DD'), 'HS003', 'Võ Th? H?ng Ánh', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-11', 'YYYY-MM-DD'), 'HS004', 'Nguy?n Qu?nh Chi', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-11', 'YYYY-MM-DD'), 'HS005', 'Võ Lê Ánh Tuy?t', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-12', 'YYYY-MM-DD'), 'HS001', 'Nguy?n V?n An', 'V?ng m?t', 'Xin ngh?', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-12', 'YYYY-MM-DD'), 'HS002', 'Hu?nh Th? Tính', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-12', 'YYYY-MM-DD'), 'HS003', 'Võ Th? H?ng Ánh', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-12', 'YYYY-MM-DD'), 'HS004', 'Nguy?n Qu?nh Chi', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-12', 'YYYY-MM-DD'), 'HS005', 'Võ Lê Ánh Tuy?t', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-13', 'YYYY-MM-DD'), 'HS001', 'Nguy?n V?n An', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-13', 'YYYY-MM-DD'), 'HS002', 'Hu?nh Th? Tính', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-13', 'YYYY-MM-DD'), 'HS003', 'Võ Th? H?ng Ánh', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-13', 'YYYY-MM-DD'), 'HS004', 'Nguy?n Qu?nh Chi', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-13', 'YYYY-MM-DD'), 'HS005', 'Võ Lê Ánh Tuy?t', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-14', 'YYYY-MM-DD'), 'HS001', 'Nguy?n V?n An', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-14', 'YYYY-MM-DD'), 'HS002', 'Hu?nh Th? Tính', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-14', 'YYYY-MM-DD'), 'HS003', 'Võ Th? H?ng Ánh', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-14', 'YYYY-MM-DD'), 'HS004', 'Nguy?n Qu?nh Chi', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-14', 'YYYY-MM-DD'), 'HS005', 'Võ Lê Ánh Tuy?t', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-15', 'YYYY-MM-DD'), 'HS001', 'Nguy?n V?n An', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-15', 'YYYY-MM-DD'), 'HS002', 'Hu?nh Th? Tính', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-15', 'YYYY-MM-DD'), 'HS003', 'Võ Th? H?ng Ánh', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-15', 'YYYY-MM-DD'), 'HS004', 'Nguy?n Qu?nh Chi', 'Có m?t', '', 'NV002', 'L5A');

INSERT INTO DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop)
VALUES (TO_DATE('2024-06-15', 'YYYY-MM-DD'), 'HS005', 'Võ Lê Ánh Tuy?t', 'Có m?t', '', 'NV002', 'L5A');

delete from NV001.DiemDanh

select * from NV001.DiemDanh

commit;

--------------------------------------------------------------------------------------------------------------------------------
DROP PROCEDURE v1_DiemDanh_SelectAll

CREATE OR REPLACE PROCEDURE v1_DiemDanh_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_kiemtra BOOLEAN;
BEGIN
    -- Kiem tra neu ten nguoi dung bat dau bang 'NV'
    v_kiemtra := (SYS_CONTEXT('USERENV', 'SESSION_USER') LIKE 'NV%');

    IF v_kiemtra THEN
        OPEN C_cursor FOR
        SELECT * FROM NV001.DIEMDANH;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.DIEMDANH WHERE MaHS = SYS_CONTEXT('USERENV', 'SESSION_USER');
    END IF;
END v1_DiemDanh_SelectAll;
/

COMMIT;

VARIABLE cur REFCURSOR;
BEGIN
    v1_DiemDanh_SelectAll(:cur);
END;
/
PRINT cur;

commit;

DROP PROCEDURE DeThi_Where_MaMonHoc

CREATE OR REPLACE PROCEDURE DeThi_Where_MaMonHoc
(
    maMonHoc IN NVARCHAR2,
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_huy BOOLEAN;
BEGIN
    v_huy := (SYS_CONTEXT('USERENV', 'SESSION_USER') LIKE 'NV%');
    
    IF v_huy THEN
        OPEN C_cursor FOR
        SELECT * FROM NV001.DeThi WHERE MaMH = maMonHoc;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.DeThi WHERE MaMH = maMonHoc AND TrangThaiTruyCapDe = 'M?';
    END IF;
END DeThi_Where_MaMonHoc;
/

COMMIT;

CREATE OR REPLACE PROCEDURE DeThi_SelectAll--DANG DUNG BEN WEB 
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    Select * from NV001.DeThi;
END DeThi_SelectAll;
/

COMMIT;


DROP PROCEDURE CauHoi_Where_MaDeThi;

CREATE OR REPLACE PROCEDURE CauHoi_Where_MaDeThi
(
    maDeThi IN NVARCHAR2, -- Tham s? ??u v?o cho MaDeThi
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_MaDeThi NVARCHAR2(50); -- Bi?n t?m th?i
BEGIN
    v_MaDeThi := maDeThi;
    
    OPEN C_cursor FOR

    SELECT ch.* FROM NV001.CauHoi ch INNER JOIN NV001.ChiTietDeThi ctdt ON ch.MaCauHoi = ctdt.MaCauHoi 
    WHERE ctdt.MaDeThi = v_MaDeThi;
END CauHoi_Where_MaDeThi;
/

SELECT * FROM NV001.CauHoi WHERE MaMH IN (SELECT MaMH FROM NV001.DeThi WHERE MADETHI = 'DETHI-0001');--test

commit

DROP PROCEDURE ThoiGianThi_Where_MaDeThi

CREATE OR REPLACE PROCEDURE ThoiGianThi_Where_MaDeThi
(
    maDeThi IN NVARCHAR2, -- Input parameter for MaDeThi
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_MaDeThi NVARCHAR2(50); -- Bi?n t?m th?i
BEGIN
    v_MaDeThi := maDeThi;
    
    OPEN C_cursor FOR
    SELECT DISTINCT ThoiGianThi FROM ChiTietDeThi WHERE MaDeThi = v_MaDeThi;
END ThoiGianThi_Where_MaDeThi;
/

DROP PROCEDURE CauHoi_Select_DapAnDung

CREATE OR REPLACE PROCEDURE CauHoi_Select_DapAnDung
(
    maCauHoi IN NVARCHAR2, -- Input parameter for MaCauHoi
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_MaCauHoi NVARCHAR2(50); -- Bi?n t?m th?i
BEGIN
    v_MaCauHoi := maCauHoi;
    
    OPEN C_cursor FOR
    SELECT DapAnDung FROM NV001.CauHoi WHERE MaCauHoi = v_MaCauHoi;
END CauHoi_Select_DapAnDung;
/

COMMIT;

DROP PROCEDURE GetKhauPhanDaDangKi

CREATE OR REPLACE PROCEDURE GetKhauPhanDaDangKi
(
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_huy BOOLEAN;
BEGIN
    -- Ki?m tra n?u t?n ng??i d?ng b?t ??u b?ng "NV"
    v_huy := (SYS_CONTEXT('USERENV', 'SESSION_USER') LIKE 'NV%');

    -- M? con tr? d?a tr?n ch?nh s?ch VPD
    IF v_huy THEN
        OPEN C_cursor FOR
        SELECT * FROM NV001.DangKyKhauPhanAn;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.DangKyKhauPhanAn WHERE MaHS = SYS_CONTEXT('USERENV', 'SESSION_USER');
    END IF;
END GetKhauPhanDaDangKi;
/

commit

DROP PROCEDURE LayThongTinDangKiCur

CREATE OR REPLACE PROCEDURE LayThongTinDangKiCur(
    p_macombo IN VARCHAR2,
    p_thutrongtuan OUT INT,
    p_giatien OUT FLOAT,
    p_qrcode OUT VARCHAR2
) AS
    CURSOR combo_cursor IS
        SELECT thutrongtuan, giatien, qrcode
        FROM NV001.combomonan
        WHERE macombomon = p_macombo;
    
    l_thutrongtuan NV001.combomonan.thutrongtuan%TYPE;
    l_giatien NV001.combomonan.giatien%TYPE;
    l_qrcode NV001.combomonan.qrcode%TYPE;
BEGIN
    OPEN combo_cursor;
    FETCH combo_cursor INTO l_thutrongtuan, l_giatien, l_qrcode;

    IF combo_cursor%NOTFOUND THEN
        -- Handle the case when no data is found
        p_thutrongtuan := NULL;
        p_giatien := NULL;
        p_qrcode := NULL;
    ELSE
        -- Assign the fetched values to the OUT parameters
        p_thutrongtuan := l_thutrongtuan;
        p_giatien := l_giatien;
        p_qrcode := l_qrcode;
    END IF;
    
    CLOSE combo_cursor;
END LayThongTinDangKiCur;
/

COMMIT;

DROP PROCEDURE GetComboMonan

CREATE OR REPLACE PROCEDURE GetComboMonan (
    comboMonan OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN comboMonan FOR
    SELECT macombomon, loaicombomon, tenmon1, tenmon2, tenmon3, tenmon4, thutrongtuan, giatien, qrcode
    FROM NV001.combomonanhienthi;
END;
/

COMMIT;

--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE USER_TAB_PRIVS_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE FROM USER_TAB_PRIVS 
    WHERE GRANTEE != 'SYS' AND PRIVILEGE IN ('SELECT', 'UPDATE', 'DELETE', 'INSERT');
END USER_TAB_PRIVS_SelectAll;
COMMIT;

SET SERVEROUTPUT ON
VARIABLE my_cursor REFCURSOR
EXEC USER_TAB_PRIVS_SelectAll( :my_cursor);
PRINT my_cursor
--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE USER_TAB_PRIVS_Where_MaUser
(
    maUser IN NVARCHAR2,
    C_cursor OUT SYS_REFCURSOR
)
AS
    v_MaUser NVARCHAR2(50);
BEGIN
    v_MaUser := maUser;
    
    OPEN C_cursor FOR
    SELECT GRANTEE, OWNER, TABLE_NAME, GRANTOR, PRIVILEGE  FROM USER_TAB_PRIVS 
    WHERE GRANTEE = v_MaUser AND GRANTEE != 'SYS' AND PRIVILEGE IN ('SELECT', 'UPDATE', 'DELETE', 'INSERT');
END USER_TAB_PRIVS_Where_MaUser;
COMMIT;
SET SERVEROUTPUT ON
VARIABLE my_cursor REFCURSOR
EXEC USER_TAB_PRIVS_Where_MaUser('NV009', :my_cursor);
PRINT my_cursor
--------------------------------------------------------------------------------------------------------------------------------
--trigger ma hoa truong password trong bang taikhoan
CREATE OR REPLACE TRIGGER encrypt_password_trigger
BEFORE INSERT ON TaiKhoan
FOR EACH ROW
BEGIN
    :NEW.Password_TK := RAWTOHEX(DES_ENCRYPT(:NEW.Password_TK));
END;
/
--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION LayPassWord(
		p_UserName_TK VARCHAR2
	) RETURN VARCHAR2
	AS
		v_Password_TK VARCHAR2(100);
	BEGIN
		SELECT Password_TK INTO v_Password_TK
		FROM TaiKhoan
		WHERE UserName_TK = p_UserName_TK;

		RETURN v_Password_TK;
	END LayPassWord;
	/

    set serveroutput on
	DECLARE
		v_PasswordResult VARCHAR2(100);
	BEGIN
		-- G?i h?m v? l?u k?t qu? v?o bi?n
		v_PasswordResult := LayPassWord('HS001');
    
		-- Xu?t k?t qu? b?ng DBMS_OUTPUT
		DBMS_OUTPUT.PUT_LINE('Password Result: ' || v_PasswordResult);
	END;
--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE create_user_proc (
    p_username IN VARCHAR2,
    p_password IN VARCHAR2
) AS
BEGIN
    -- T?o ng??i dùng m?i trong Oracle Database
    EXECUTE IMMEDIATE 'CREATE USER ' || p_username || ' IDENTIFIED BY ' || p_password;

    -- C?p quy?n c? b?n cho ng??i dùng m?i
    EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO ' || p_username;
    EXECUTE IMMEDIATE 'GRANT EXECUTE ON SYS.DemSessionsUser TO ' || p_username;

    -- Chèn thông tin ng??i dùng m?i vào b?ng TAIKHOAN
    INSERT INTO TAIKHOAN (UserName_TK, Password_TK) VALUES (p_username, p_password);
END;
--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE create_role_proc (
    p_role_name IN VARCHAR2
) AS
BEGIN
    EXECUTE IMMEDIATE 'CREATE ROLE ' || p_role_name;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
CREATE OR REPLACE PROCEDURE drop_role_proc (
    p_role_name IN VARCHAR2
) AS
BEGIN
    EXECUTE IMMEDIATE 'DROP ROLE ' || p_role_name;

EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
CREATE OR REPLACE PROCEDURE GrantRoleToUser(
    p_role_name IN VARCHAR2,
    p_user_name IN VARCHAR2
)
AS
BEGIN
    EXECUTE IMMEDIATE 'GRANT ' || p_role_name || ' TO ' || p_user_name;
    
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error ');
END GrantRoleToUser;
/
CREATE OR REPLACE PROCEDURE RevokeRoleFromUser(
    p_role_name IN VARCHAR2,
    p_user_name IN VARCHAR2
)
AS
BEGIN
    EXECUTE IMMEDIATE 'REVOKE ' || p_role_name || ' FROM ' || p_user_name;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error');
END RevokeRoleFromUser;
/
--------------------------------------------------------------------------------------------------------------------------------
CREATE SEQUENCE CongNoSeq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE OR REPLACE TRIGGER trg_insert_congno_after_insert
AFTER INSERT ON DANGKYKHAUPHANAN
FOR EACH ROW
DECLARE
    v_ma_cong_no VARCHAR2(50);
BEGIN
    -- T?o m? c?ng n? t? ??ng
    SELECT 'CN' || LPAD(CongNoSeq.NEXTVAL, 3, '0') INTO v_ma_cong_no FROM DUAL;

    -- Ch?n b?n ghi m?i v?o b?ng CongNo
    INSERT INTO CongNo (
        MaCongNo,
        MaHS,
        NgayTaoCongNo,
        TienHocPhi,
        TienAn,
        TienPhuThu,
        TongCongNo,
        TrangThai
    ) VALUES (
        v_ma_cong_no,
        :NEW.MaHS,
        SYSDATE,
        0,
        :NEW.Gia,
        0,
        :NEW.Gia,
        'Chua thanh toan'
    );
END;
/

CREATE OR REPLACE TRIGGER trg_DangKyKhauPhanAn_Delete
AFTER DELETE ON DangKyKhauPhanAn
FOR EACH ROW
DECLARE
    v_tien_an DECIMAL(10, 2);
    v_macongno NVARCHAR2(50);
BEGIN
    -- L?y s? ti?n ?n t? b?ng ComBoMonAn
    SELECT GiaTien INTO v_tien_an
    FROM ComBoMonAn
    WHERE MaComBoMon = :OLD.MaComBoMon;

    -- T?o m? c?ng n? v?i chu?i "CN" v? s? th? t? t? sequence
    v_macongno := 'CN' || LPAD(CongNoSeq.NEXTVAL, 3, '0');

    -- Th?m d?ng d? li?u v?o b?ng CongNo
    INSERT INTO CongNo (MaCongNo, MaHS, NgayTaoCongNo, TienHocPhi, TienAn, TienPhuThu, TongCongNo, TrangThai)
    VALUES (v_macongno, :OLD.MaHS, SYSDATE, 0, -v_tien_an, 0, -v_tien_an, 'Chua thanh toan');
END;
/

CREATE OR REPLACE PROCEDURE UpdateTrangThaiCongNo (
 p_mahs NVARCHAR2
) IS
BEGIN
    UPDATE NV001.CONGNO
    SET TRANGTHAI = 'Da thanh toan'
    WHERE MAHS = p_mahs;

    COMMIT;
END UpdateTrangThaiCongNo;
/

CREATE OR REPLACE PROCEDURE KiemTraTrangThaiCongNo (
    p_mahs IN VARCHAR2,
    p_ketqua OUT NUMBER
) AS
    -- tr? v? 1: co it nhat mot dong chua thanh toan
    CURSOR cur_congno IS
        SELECT TrangThai
        FROM CongNo
        WHERE MaHS = p_mahs;
    
    v_trangthai CongNo.TrangThai%TYPE;
    v_daThanhToanCount INTEGER := 0;
    v_chuaThanhToanCount INTEGER := 0;
BEGIN
    OPEN cur_congno;
    LOOP
        FETCH cur_congno INTO v_trangthai;
        EXIT WHEN cur_congno%NOTFOUND;
        
        IF v_trangthai = 'Chua thanh toan' THEN
            v_chuaThanhToanCount := v_chuaThanhToanCount + 1;
        ELSIF v_trangthai = 'Da thanh toan' THEN
            v_daThanhToanCount := v_daThanhToanCount + 1;
        END IF;
    END LOOP;
    CLOSE cur_congno;
    
    IF v_chuaThanhToanCount > 0 THEN
        p_ketqua := 1;
    ELSE
        p_ketqua := 0;
    END IF;
END KiemTraTrangThaiCongNo;
/

CREATE OR REPLACE PROCEDURE LayTongCongNoChuaThanhToan (
    p_mahs IN VARCHAR2,
    p_tongconno OUT FLOAT
) AS
    CURSOR cur_congno IS
        SELECT TONGCONGNO 
        FROM CongNo 
        WHERE MaHS = p_mahs AND TrangThai = 'Chua thanh toan';
    v_no CongNo.TONGCONGNO%TYPE;
    v_tongconno FLOAT := 0;
    v_count INTEGER := 0;
BEGIN
    OPEN cur_congno;
    LOOP
        FETCH cur_congno INTO v_no;
        EXIT WHEN cur_congno%NOTFOUND;
        v_tongconno := v_tongconno + v_no;
        v_count := v_count + 1;
    END LOOP;
    CLOSE cur_congno;

    IF v_count = 0 THEN
        p_tongconno := -1;
    ELSE
        p_tongconno := v_tongconno;
    END IF;
END LayTongCongNoChuaThanhToan;
/

--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE CapNhatNhanVien(
    p_MaNV         IN VARCHAR2,
    p_TenNV        IN VARCHAR2,
    p_GioiTinh     IN VARCHAR2,
    p_NgaySinh     IN DATE,
    p_ChucVu       IN VARCHAR2,
    p_DiaChi       IN VARCHAR2,
    p_Email        IN VARCHAR2,
    p_SoDT         IN VARCHAR2,
    p_TrinhDo      IN VARCHAR2,
    p_ChuyenMon    IN VARCHAR2,
    p_NoiDaoTao    IN VARCHAR2,
    p_NamTotNghiep IN NUMBER,
    p_user         IN VARCHAR2
)
IS
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'QUANTRIVIEN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'NHANVIEN') OR
          (GRANTEE = p_user AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'NHANVIEN');
    
    IF v_HasPermission > 0 THEN
        UPDATE NhanVien
        SET TenNV = p_TenNV,
            GioiTinh = p_GioiTinh,
            NgaySinh = p_NgaySinh,
            ChucVu = p_ChucVu,
            DiaChi = p_DiaChi,
            Email = p_Email,
            SoDT = p_SoDT,
            TrinhDo = p_TrinhDo,
            ChuyenMon = p_ChuyenMon,
            NoiDaoTao = p_NoiDaoTao,
            NamTotNghiep = p_NamTotNghiep,
            HinhAnh = EMPTY_BLOB()
        WHERE MaNV = p_MaNV;

    COMMIT;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END CapNhatNhanVien;
/
--------------------------------------------------------------------------------------------------------------------------------
-- Rang buoc so luong hoc sinh trong lop khong am
ALTER TABLE NV001.Lop
ADD CONSTRAINT CHK_Lop_SiSo CHECK (SiSo >= 0);

-- Kiem tra rang buoc
INSERT INTO Lop (MaLop, TenLop, SiSo, NamHoc) VALUES ('L001', 'Lop 1', -1, 2024);

select * from NV001.Lop

-- Rang buoc gia tien combomonan > 0
ALTER TABLE NV001.ComBoMonAn
ADD CONSTRAINT CHK_ComBoMonAn_GiaTien CHECK (GiaTien > 0);

-- Kiem tra rang buoc
INSERT INTO ComBoMonAn (MaComBoMon, TenMon1, GiaTien, MaNV) VALUES ('CB001', 'Mon An 1', -100, 'NV001');
--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE ThemNhanVien(
    p_MaNV         IN VARCHAR2,
    p_TenNV        IN VARCHAR2,
    p_GioiTinh     IN VARCHAR2,
    p_NgaySinh     IN DATE,
    p_ChucVu       IN VARCHAR2,
    p_DiaChi       IN VARCHAR2,
    p_Email        IN VARCHAR2,
    p_SoDT         IN VARCHAR2,
    p_TrinhDo      IN VARCHAR2,
    p_ChuyenMon    IN VARCHAR2,
    p_NoiDaoTao    IN VARCHAR2,
    p_NamTotNghiep IN NUMBER,
    p_user         IN VARCHAR2
)
IS
    v_HasPermission NUMBER;
BEGIN
    -- Check if the user has INSERT permission
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'QUANTRIVIEN_ROLE' AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'NHANVIEN') OR
          (GRANTEE = p_user AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'NHANVIEN');
    
    IF v_HasPermission > 0 THEN
        -- Insert a new record into NhanVien table
        INSERT INTO NhanVien (MaNV,TenNV,GioiTinh,NgaySinh,ChucVu,DiaChi,Email,SoDT,TrinhDo,ChuyenMon,NoiDaoTao,NamTotNghiep,HinhAnh) 
        VALUES (p_MaNV,p_TenNV,p_GioiTinh,p_NgaySinh,p_ChucVu,p_DiaChi,p_Email,p_SoDT,p_TrinhDo,p_ChuyenMon,p_NoiDaoTao,p_NamTotNghiep,EMPTY_BLOB());
        
        EXECUTE IMMEDIATE 'CREATE USER ' || p_MaNV || ' IDENTIFIED BY ' || p_MaNV;
        EXECUTE IMMEDIATE 'GRANT CREATE SESSION TO ' || p_MaNV;
        INSERT INTO TAIKHOAN (UserName_TK, Password_TK) VALUES (p_MaNV, p_MaNV);
        EXECUTE IMMEDIATE 'GRANT EXECUTE ON SYS.DemSessionsUser TO ' || p_MaNV;
        COMMIT;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
        ROLLBACK;
END ThemNhanVien;
/
--------------------------------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE XoaUser (
    p_username IN VARCHAR2
) AS
BEGIN
    EXECUTE IMMEDIATE 'DROP USER ' || p_username;

    DELETE FROM TAIKHOAN WHERE username_tk = p_username;
END;

CREATE OR REPLACE PROCEDURE ThongtinNguoidung_SelectAll
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    SELECT USERNAME, ACCOUNT_STATUS,CREATED FROM DBA_USERS
    WHERE USERNAME LIKE 'HS%' OR USERNAME LIKE 'NV%';
END ThongtinNguoidung_SelectAll;
--------------------------------------------------------------------------------------------------------------------------------

--------------------------------------------------------------------------------------------------------------------------------

