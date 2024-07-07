    --CÔNG N?
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
    --NHÂN VIÊN
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
     --H?C SINH
    CREATE OR REPLACE FUNCTION huypro_policy_hocsinh (
        schema_var IN VARCHAR2,
        table_var  IN VARCHAR2
    )
    RETURN VARCHAR2
    AS
        v_predicate VARCHAR2(200);
    BEGIN
        -- Thêm ?i?u ki?n cho phép các tài kho?n b?t ??u b?ng "NV" xem d? li?u
        v_predicate := '(MaHS = SYS_CONTEXT(''USERENV'', ''SESSION_USER'')) OR (SYS_CONTEXT(''USERENV'', ''SESSION_USER'') LIKE ''NV%'' ESCAPE ''\'')';
        
        RETURN v_predicate;
    END;
    /
    CREATE OR REPLACE FUNCTION huypro_policy_hocsinh_KQ (
        schema_var IN VARCHAR2,
        table_var  IN VARCHAR2
    )
    RETURN VARCHAR2
    AS
        v_predicate VARCHAR2(200);
    BEGIN
        -- Thêm ?i?u ki?n cho phép các tài kho?n b?t ??u b?ng "NV" xem d? li?u
        v_predicate := '(MaHocSinh = SYS_CONTEXT(''USERENV'', ''SESSION_USER'')) OR (SYS_CONTEXT(''USERENV'', ''SESSION_USER'') LIKE ''NV%'' ESCAPE ''\'')';
        
        RETURN v_predicate;
    END;
    /
    --CHINH SACH TAO RA DE HAN CHE VIEC XEM DE THI CUA HOC SINH (CHI XEM DC DE DANG TRANG THAI MO)
    DROP FUNCTION huypro_policy_hocsinh_DeThi
    CREATE OR REPLACE FUNCTION huypro_policy_hocsinh_DeThi (
        schema_var IN VARCHAR2,
        table_var  IN VARCHAR2
    )
    RETURN VARCHAR2
    AS
        v_predicate VARCHAR2(200);
    BEGIN
        v_predicate := CASE 
                          WHEN SUBSTR(SYS_CONTEXT('USERENV', 'SESSION_USER'), 1, 2) = 'HS' THEN
                            'TrangThaiTruyCapDe = ''M?'''
                          ELSE
                            '1=1' -- Khi không ph?i là tài kho?n b?t ??u b?ng "HS", cho phép xem t?t c? d? li?u
                       END;
        
        RETURN v_predicate;
    END;
    /


    GRANT EXEMPT ACCESS POLICY TO NV001;
    
    SELECT * FROM NV001.HOCSINH;
    SELECT * FROM NV001.NHANVIEN;
    SELECT * FROM NV001.KETQUA;
    COMMIT;
    
    
    
    
    
    
    
    
    
    