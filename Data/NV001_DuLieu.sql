


--TAO USER NV001 CHIU TRACH NHIEM QUAN LY DU LIEU
CREATE USER NV001 IDENTIFIED BY NV001;
GRANT CREATE SESSION TO NV001;
GRANT DBA TO NV001;
GRANT ALL PRIVILEGES TO NV001;

--TAO CO SO DU LIEU
--Drop co so du lieu
DROP TABLE NHANVIEN;
DROP TABLE PHUHUYNH;
DROP TABLE LOP;
DROP TABLE HOCSINH;
DROP TABLE CONGNO;
DROP TABLE DANGKYKHAUPHANAN;
DROP TABLE COMBOMONAN;
DROP TABLE DIEMDANH ;

--- [ Tao bang ] ---
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
GRANT SELECT, INSERT, UPDATE, DELETE ON NhanVien TO hr;

CREATE TABLE Lop 
(
    MaLop VARCHAR2(50) PRIMARY KEY,
    TenLop VARCHAR2(100),
    SiSo NUMBER(5),
    NamHoc NUMBER(5)
);
GRANT SELECT, INSERT, UPDATE, DELETE ON Lop TO hr;

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
GRANT SELECT, INSERT, UPDATE, DELETE ON PhuHuynh TO hr;

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
GRANT SELECT, INSERT, UPDATE, DELETE ON HocSinh TO hr;

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
GRANT SELECT, INSERT, UPDATE, DELETE ON CongNo TO hr;

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
GRANT SELECT, INSERT, UPDATE, DELETE ON DiemDanh TO hr;


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
    QRCode VARCHAR2(100)
);

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

ALTER TABLE DangKyKhauPhanAn
ADD CONSTRAINT FK_DangKyKhauPhanAn_CbMonAn FOREIGN KEY (MaComBoMon) REFERENCES ComBoMonAn(MaComBoMon);

--DU LIEU TIEP TUC PHAT TRIEN
CREATE TABLE MonHoc 
(
    MaMH VARCHAR2(50) PRIMARY KEY,
    TenMH VARCHAR2(100),
    MaLop VARCHAR2(50),
    CONSTRAINT FK_MonHoc_Lop FOREIGN KEY (MaLop) REFERENCES Lop(MaLop)
);

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

CREATE TABLE DeThi
(
    MaDeThi VARCHAR2(50) PRIMARY KEY,
    NgaySoanDe DATE,
    SoLuongCauHoi VARCHAR2(100),
    TrangThai VARCHAR2(100),
    MaNV VARCHAR2(50),
    MaMH VARCHAR2(50),
    CONSTRAINT FK_DeThi_NhanVien FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV),
    CONSTRAINT FK_DeThi_MonHoc FOREIGN KEY (MaMH) REFERENCES MonHoc(MaMH)
);

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

CREATE TABLE KetQua
(
    MaHocSinh VARCHAR2(50),
    MaDeThi VARCHAR2(50),
    SoCauDung VARCHAR2(100),
    DiemSo VARCHAR2(100),
    ThoiGianLucNop TIMESTAMP,
    CONSTRAINT PK_KetQua PRIMARY KEY (MaHocSinh, MaDeThi),
    CONSTRAINT FK_KetQua_DeThi FOREIGN KEY (MaDeThi) REFERENCES DeThi(MaDeThi),
    CONSTRAINT FK_KetQua FOREIGN KEY (MaHocSinh) REFERENCES HocSinh(MaHS)
);

--THIET LAP PROFILE
DROP PROFILE NhanVien;

CREATE PROFILE NhanVien LIMIT 
    FAILED_LOGIN_ATTEMPTS 3
    SESSIONS_PER_USER 3  
    CONNECT_TIME 45 
    IDLE_TIME 5
    PASSWORD_LOCK_TIME 5;
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
    -- Trigger giám sát ??ng nh?p
    DROP TRIGGER GiamSatUserLogin_trigger;
    CREATE OR REPLACE TRIGGER GiamSatUserLogin_trigger
    AFTER LOGON ON DATABASE
    BEGIN
        IF USER != 'SYS' THEN
            INSERT INTO GiamSatUser (key_id, username, login_time, hoat_dong)
            VALUES (GiamSatDangNhap.NEXTVAL, USER, SYSTIMESTAMP, 'Online');
        END IF;
    END GiamSatUserLogin_trigger;
    
    -- Trigger giám sát ??ng xu?t
    DROP TRIGGER GiamSatUserLogout_trigger;
    CREATE OR REPLACE TRIGGER GiamSatUserLogout_trigger
    BEFORE LOGOFF ON DATABASE
    BEGIN
        IF USER != 'SYS' THEN
            INSERT INTO GiamSatUser (key_id, username, logout_time, hoat_dong)
            VALUES (GiamSatDangNhap.NEXTVAL, USER, SYSTIMESTAMP, 'Offline');
        END IF;
    END GiamSatUserLogout_trigger;
    --Xem du lieu giam sat
    SELECT * FROM GiamSatUser;

--FGA
    --Chinh sach FGA do user NV002 là nguoi co quyen quan ly khoi tao nen ta cap cac quyen sau de user NV002 có quyen thuc thi chinh sach
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
    --Giam sat cac hoat dong ma user nào thao tac tren bang 
    AUDIT ALTER, GRANT, INSERT, UPDATE, DELETE ON NV001.DangKyKhauPhanAn;
    --Giam sat tat ca cac hoat dong select, update, delete, insert c?a user NV002 tung thao tac qua cac bang trong csdl mà ko gioi han ve so bang 
    AUDIT SELECT TABLE, UPDATE TABLE, DELETE TABLE, INSERT TABLE BY NV002;
    --Lenh de xem view ghi lai nhat ky giam sat
    SELECT username, timestamp, obj_name, action_name, COUNT(*) AS so_luong
    FROM dba_audit_trail
    WHERE username = 'NV002'
    GROUP BY username, timestamp, obj_name, action_name;
    --Xoa tat ca ban ghi giam sat cua Standard Auditing
    DELETE FROM SYS.AUD$
    COMMIT;
--PHAN QUYEN, DIEU KHIEN TRUY CAP (KET HOP VPD, OLS)

    --Tao user ket hop profile da duoc tao truoc do
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
    CREATE USER NV009 IDENTIFIED BY NV009 PROFILE NhanVien;--da tao
    GRANT CREATE SESSION TO NV009;
    
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
    -- Gan quyen SELECT, INSERT, UPDATE và DELETE cho nhom QuanTriVien_ROLE tren bang 
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
    
    GRANT EXECUTE ON CapQuyenUser                                   TO QuanTriVien_ROLE;
    GRANT EXECUTE ON ThuHoiQuyenUser                                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON PROC_ACCOUNT_UNLOCK                            TO QuanTriVien_ROLE;
    GRANT EXECUTE ON PROC_ACCOUNT_LOCK                              TO QuanTriVien_ROLE;
    GRANT EXECUTE ON CNQ                                            TO QuanTriVien_ROLE;
    GRANT EXECUTE ON THNQ                                           TO QuanTriVien_ROLE;
    GRANT EXECUTE ON ADD_FGA_POLICY                                 TO QuanTriVien_ROLE;
    GRANT EXECUTE ON DROP_FGA_POLICY                                TO QuanTriVien_ROLE;
    GRANT EXECUTE ON CREATE_DIRECTORY_PROC                          TO QuanTriVien_ROLE;
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

    -- Gan quyen SELECT, INSERT, UPDATE và DELETE cho nhom GiaoVien_ROLE tren bang
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
    -- Gan quyen SELECT, INSERT, UPDATE và DELETE cho nhom HocSinh_ROLE tren bang
    GRANT SELECT, UPDATE                 ON NV001.HocSinh           TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.Lop               TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.DiemDanh          TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.ComBoMonAn        TO HocSinh_ROLE;
    
    GRANT SELECT                         ON NV001.MonHoc            TO HocSinh_ROLE; 
    GRANT SELECT                         ON NV001.CauHoi            TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.DeThi             TO HocSinh_ROLE;
    GRANT SELECT                         ON NV001.ChiTietDeThi      TO HocSinh_ROLE;
    GRANT SELECT, INSERT                 ON NV001.KetQua            TO HocSinh_ROLE;
    -- Gan quyen SELECT, INSERT, UPDATE và DELETE cho nhom NhanVienQuanLy_ROLE tren bang
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
    -- Gan quyen SELECT, INSERT, UPDATE và DELETE cho nhom NhanVienThuNgan_ROLE tren bang
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
    -- Gan quyen SELECT, INSERT, UPDATE và DELETE cho nhom HocSinh_ROLE tren bang 
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
    COMMIT;
    
    --Tao chinh sach vpd
    CREATE OR REPLACE FUNCTION No_TrangThai (
        p_schema IN VARCHAR2,
        p_object IN VARCHAR2)
    RETURN VARCHAR2
    AS
    BEGIN
        RETURN 'TrangThai <> ''Da thanh toan''';
    END;
    /
    
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
    --?? THI
    CREATE OR REPLACE FUNCTION No_TrangThai_DETHI (
        p_schema IN VARCHAR2,
        p_object IN VARCHAR2)
    RETURN VARCHAR2
    AS
    BEGIN
        RETURN 'TrangThaiTruyCapDe <> ''?óng''';
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
        object_name => 'KetQua',
        policy_name => 'HuyproPolicyHocsinh_KetQua');
    END;
    /
    SELECT * FROM NV001.HOCSINH
    SELECT * FROM NV001.KETQUA;
    
--SAO LUU PHUC HOI CO SO DU LIEU

    --Tao mot doi tuong thu muc
    CREATE DIRECTORY TanHuy AS 'E:\HOC_KI_1_NAM_4\DoAnChuyenNganh';
    DROP DIRECTORY HUYNE;
    --Export co so du lieu tu user NV001
    expdp NV001/NV001@DB_HSBT DIRECTORY=TanHuy DUMPFILE=bakup.dmp SCHEMAS=NV001
    --Import co so du lu
    impdp NV001/NV001@DB_HSBT DIRECTORY=TanHuy DUMPFILE=bakup.dmp SCHEMAS=NV001

    
--Thu tuc cap nhat cong no
CREATE OR REPLACE PROCEDURE CapNhatCongNo (
    p_MaCongNo VARCHAR2,
    p_MaHS VARCHAR2,
    p_NgayTaoCongNo DATE,
    p_TienHocPhi DECIMAL,
    p_TienAn DECIMAL,
    p_TienPhuThu DECIMAL,
    p_TongCongNo DECIMAL,
    p_TrangThai VARCHAR2
) AS
BEGIN
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
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END CapNhatCongNo;
/
--Ban update
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
SELECT *
FROM USER_TAB_PRIVS
WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CONGNO') OR
      (GRANTEE = 'NV001' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CONGNO');

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
--Thu tuc mo khoa tai khoan
CREATE OR REPLACE PROCEDURE PROC_ACCOUNT_UNLOCK (
    p_manv IN VARCHAR2
)
AS
BEGIN
    EXECUTE IMMEDIATE 'ALTER USER ' || p_manv || ' ACCOUNT UNLOCK';
END;
/
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
    DBMS_OUTPUT.PUT_LINE('Ch?c n?ng không h?p l?');
  END IF;
END CNQ;
/

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
    DBMS_OUTPUT.PUT_LINE('Ch?c n?ng không h?p l?');
  END IF;
END THNQ;
/


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
    -- Ki?m tra quy?n truy c?p
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'DIEMDANH') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'INSERT' AND TABLE_NAME = 'DIEMDANH');
    
    IF v_HasPermission > 0 THEN
        -- Th?c hi?n thêm b?n ghi
        INSERT INTO NV001.DiemDanh (NgayDiemDanh, MaHS, TenHS, TrangThaiDiemDanh, GhiChu, MaNV, MaLop) 
        VALUES (p_NgayDiemDanh, p_MaHS, p_TenHS, p_TrangThaiDiemDanh, p_GhiChu, p_MaNV, p_MaLop);
        
        COMMIT;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
END ThemDiemDanh;
/
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
    -- Ki?m tra quy?n truy c?p
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'DIEMDANH') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'DIEMDANH');
    
    IF v_HasPermission > 0 THEN
        -- Th?c hi?n thêm b?n ghi
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

CREATE OR REPLACE PROCEDURE XoaThongTinComBoMonAn AS
BEGIN
    DELETE FROM ComBoMonAn;
    COMMIT;
END XoaThongTinComBoMonAn;
/
EXEC XoaThongTinComBoMonAn;

--th m d? li?u v o b?ng combo m n ?n 2 *
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
CREATE OR REPLACE PROCEDURE XoaThongTinComBoMonAnHienThi AS
BEGIN
    DELETE FROM ComBoMonAnHienThi;
    COMMIT;
END XoaThongTinComBoMonAnHienThi;
/
EXEC XoaThongTinComBoMonAnHienThi;

--xoa 1 khau phan cua bang combo mon an 4 
CREATE OR REPLACE PROCEDURE XoaMotKP_ComBoMon(p_MaComBoMon VARCHAR2) IS
BEGIN
    DELETE FROM ComBoMonAn WHERE MaComBoMon = p_MaComBoMon;
    COMMIT;
END XoaMotKP_ComBoMon;
/
--xoa 1 khau phan cua bang combo mon an hien thi 5
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

CREATE OR REPLACE PROCEDURE InsertDangKyKhauPhanAn(
    p_MaHS VARCHAR2,
    p_NgayDangKyKP VARCHAR2,
    p_ThuTrongTuan int,
    p_Gia float,
    p_MaComBoMon VARCHAR2, 
    p_QRCode VARCHAR2
) AS
BEGIN
    INSERT INTO DangKyKhauPhanAn (MaHS, NgayDangKyKP,ThuTrongTuan, Gia, MaComBoMon, QRCode)
    VALUES (p_MaHS, p_NgayDangKyKP, p_ThuTrongTuan, p_Gia, p_MaComBoMon, p_QRCode);
    COMMIT;
END InsertDangKyKhauPhanAn;
/
BEGIN
    InsertDangKyKhauPhanAn('HS001', '2023-11-22', '2', '20000', 'CB023', 'aaaaa');
END;
/

--- kt user dang thuc thi 9
CREATE OR REPLACE PROCEDURE GetUserName(p_username OUT VARCHAR2)
IS
BEGIN
  SELECT USER INTO p_username FROM DUAL;
END GetUserName;
/
DECLARE
  v_username VARCHAR2(30);
BEGIN
  -- G?i th? t?c và nh?n giá tr? tr? v?
  GetUserName(v_username);
  -- In tên ng??i dùng
  DBMS_OUTPUT.PUT_LINE(v_username);
END;
/

---- xoa thong tin dang ki 10
CREATE OR REPLACE PROCEDURE XoaThongTinDangKi AS
BEGIN
    DELETE FROM dangkykhauphanan;
    COMMIT;
END XoaThongTinDangKi;
/
EXEC XoaThongTinDangKi;


---- xoa thong tin dang ki cua mot khau phan
CREATE OR REPLACE PROCEDURE XoaDangKy_MotKhauPhanAn(
    p_MaHS IN VARCHAR2,
    p_NgayDangKyKP IN VARCHAR2,
    p_ThuTrongTuan IN INT
) AS
BEGIN
    DELETE FROM DangKyKhauPhanAn
    WHERE MaHS = p_MaHS
      AND NgayDangKyKP = p_NgayDangKyKP
      AND ThuTrongTuan = p_ThuTrongTuan;
    COMMIT;
END XoaDangKy_MotKhauPhanAn;
/
BEGIN
    XoaDangKy_MotKhauPhanAn('HS001', '2023-11-27', '3');
END;
/

-- trigger rang buoc viec chinh san thong tin dang ki
-- cho phep chinh sua truoc t7 hang tuan
CREATE OR REPLACE TRIGGER rangBuocChinhSuaDangKy
BEFORE DELETE ON DangKyKhauPhanAn
FOR EACH ROW
DECLARE
    v_CurrentDayOfWeek INT;
BEGIN
    -- L?y ngày hi?n t?i và chuy?n ??i thành s? ngày trong tu?n
    v_CurrentDayOfWeek := TO_NUMBER(TO_CHAR(SYSDATE, 'D'));

    -- Ki?m tra n?u ngày hi?n t?i không n?m trong kho?ng t? th? 3 ??n th? 6
    IF v_CurrentDayOfWeek NOT BETWEEN 2 AND 6 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Ch? ???c phép xóa d? li?u vào th? 3, 4, 5, ho?c 6.');
    END IF;
END rangBuocChinhSuaDangKy;
/
-- chinh sua bang combohien thi
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
        DBMS_OUTPUT.PUT_LINE('L?i: ' || SQLERRM);
END E1_ThemCauHoi;
/

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
        DBMS_OUTPUT.PUT_LINE('L?i: ' || SQLERRM);
END E1_ThemChiTietDeThi;
/

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
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_THEMCAUHOI') OR
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
EXEC E1_ThemCauHoi('CH001', 'Câu h?i 1', '?áp án A', '?áp án B', '?áp án C', '?áp án D', 'A', 'MH001-TOAN');

select * from NV001.CauHoi
delete from NV001.CauHoi
commit

--------------------------------------------------------------------------------------------------------------------------------
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
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_SUACAUHOI') OR
          (GRANTEE = 'p_MaNV' AND PRIVILEGE = 'UPDATE' AND TABLE_NAME = 'CAUHOI');

    IF v_HasPermission > 0 THEN      
        -- Ki?m tra xem mã câu h?i ?ã t?n t?i ch?a
        SELECT COUNT(*) INTO v_Count FROM NV001.CauHoi WHERE MaCauHoi = p_MaCauHoi;

        IF v_Count = 0 THEN
            -- Thông báo n?u mã câu h?i không t?n t?i
            DBMS_OUTPUT.PUT_LINE('Loi: Ma cau hoi khong ton tai.');
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
            DBMS_OUTPUT.PUT_LINE('Sua cau hoi thanh cong');            
        END IF;
    ELSE
        -- Thông báo n?u không có quy?n truy c?p
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap va thuc hien thu tuc nay.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        -- Rollback n?u có l?i x?y ra
        ROLLBACK; 
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_SuaCauHoi;
/
set serveroutput on
EXEC E1_SuaCauHoi('1', 'Câu h?i m?i ?ã ???c s?a', '?áp án A m?i', '?áp án B m?i', '?áp án C m?i', '?áp án D m?i', 'B', 'MH001-TOAN');

select * from NV001.CAUHOI

-----------------------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE E1_XoaCauHoi (
    p_MaCauHoi VARCHAR2,
    p_MaNV VARCHAR2
)AS
    v_Count NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_XOACAUHOI') OR
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
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_THEMCHITIETDETHI') OR
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
EXEC E1_ThemChiTietDeThi('DETHI-0004', 'CH031', TO_DATE('2024-04-17', 'YYYY-MM-DD'), '90 phút', '8:00:00', '8:30:00','NV001');
DROP TRIGGER UpdateDeThiTrangThai;

--------------------------------------------------------------------------------------------------------------------------------
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
    WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_THEMCONGNO') OR
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
    WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_SUACONGNO') OR
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
CREATE OR REPLACE PROCEDURE E1_XoaCongNo (
    p_MaCongNo IN VARCHAR2,
    p_MaNV VARCHAR2
) AS
    l_CountCongNo NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'NHANVIENTHUNGAN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_XOACONGNO') OR
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
CREATE OR REPLACE PROCEDURE v1_XoaAllTheoMaDeThi(
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
        DBMS_OUTPUT.PUT_LINE('Da xoa du lieu thanh cong.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Ban khong co quyen truy cap de thuc hien thu tec nay.');
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
        WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_THEMDETHI') OR
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
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_SUADETHI') OR
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
CREATE OR REPLACE PROCEDURE E1_XoaDeThi (
    p_MaDeThi VARCHAR2,
    p_MaNV VARCHAR2
) AS
    v_KiemTraTonTai NUMBER;
    v_HasPermission NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_HasPermission
    FROM USER_TAB_PRIVS
    WHERE (GRANTEE = 'GIAOVIEN_ROLE' AND PRIVILEGE = 'EXECUTE' AND TABLE_NAME = 'E1_XOADETHI') OR
          (GRANTEE = p_MaNV AND PRIVILEGE = 'DELETE' AND TABLE_NAME = 'DETHI');
    IF v_HasPermission > 0 THEN     
        -- Xóa b?n ghi
        DELETE FROM NV001.ChiTietDeThi
        WHERE MaDeThi = p_MaDeThi;
        
        DELETE FROM NV001.CauHoi
        WHERE MaMH IN (SELECT DISTINCT MaMH FROM DeThi WHERE MaDeThi = p_MaDeThi)
        AND NOT EXISTS (
            SELECT 1 FROM ChiTietDeThi WHERE CauHoi.MaCauHoi = ChiTietDeThi.MaCauHoi
        );
    
        DELETE FROM NV001.DeThi
        WHERE MaDeThi = p_MaDeThi;
        
        v_KiemTraTonTai := SQL%ROWCOUNT;
        
        IF v_KiemTraTonTai = 0 THEN
            DBMS_OUTPUT.PUT_LINE('Ma de thi khong ton tai hoac khong co gi duoc cap nhat.');
        ELSE
            COMMIT;
            DBMS_OUTPUT.PUT_LINE('Xoa du lieu thanh cong.');
        END IF;
    ELSE
        DBMS_OUTPUT.PUT_LINE('B?n không có quy?n truy c?p ?? th?c hi?n th? t?c này.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK; -- Rollback neu co loi xay ra
        DBMS_OUTPUT.PUT_LINE('Loi: ' || SQLERRM);
END E1_XoaDeThi;
/

BEGIN 
    E1_XoaDeThi('DETHI-0004', 'NV001'); 
END;
/

commit

--------------------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------------------
-- LONG: Lay ma lop
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

CREATE OR REPLACE PROCEDURE KetQua_SelectAll
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
        SELECT * FROM NV001.KETQUA;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.KETQUA WHERE MaHocSinh = SYS_CONTEXT('USERENV', 'SESSION_USER');
    END IF;
END KetQua_SelectAll;
COMMIT;
CREATE OR REPLACE PROCEDURE CongNo_SelectAll
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
        SELECT * FROM NV001.CONGNO;
    ELSE
        OPEN C_cursor FOR
        SELECT * FROM NV001.CONGNO WHERE MaHS = SYS_CONTEXT('USERENV', 'SESSION_USER');
    END IF;
END CongNo_SelectAll;
COMMIT;
CREATE OR REPLACE PROCEDURE MonHoc_SelectAll--DANG DUNG BEN WEB 
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    Select * from NV001.MONHOC;
END MonHoc_SelectAll;
CREATE OR REPLACE PROCEDURE DeThi_SelectAll--DANG DUNG BEN WEB 
(
    C_cursor OUT SYS_REFCURSOR
)
AS
BEGIN
    OPEN C_cursor FOR
    Select * from NV001.DETHI;
END DeThi_SelectAll;

COMMIT;
--Xuat du lieu
SET SERVEROUTPUT ON

VARIABLE my_cursor REFCURSOR
EXEC MonHoc_SelectAll(:my_cursor)

PRINT my_cursor



--THU TUC DANG XUAT
CREATE OR REPLACE PROCEDURE DangXuat (
    p_ma_nhan_vien IN VARCHAR2
) AS
BEGIN
    FOR nth IN (SELECT sid, serial#
              FROM v$session
              WHERE username = p_ma_nhan_vien) LOOP
        EXECUTE IMMEDIATE 'ALTER SYSTEM KILL SESSION ''' || nth.sid || ',' || nth.serial# || ''' IMMEDIATE';
    END LOOP;
END;
/

GRANT EXECUTE ON SYS.DangXuat              TO NV001;

BEGIN
    SYS.DangXuat('NV002');
END;
/

DECLARE
  CURSOR nth IS
    SELECT sid, serial#
    FROM v$session
    WHERE username = 'NV002';
BEGIN
  FOR h IN nth LOOP
    EXECUTE IMMEDIATE 'ALTER SYSTEM KILL SESSION ''' || h.sid || ',' || h.serial# || ''' IMMEDIATE';
  END LOOP;
END;
/

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




