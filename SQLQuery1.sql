-- Bảng học sinh
CREATE TABLE HocSinh (
    MaHocSinh INT PRIMARY KEY,
    HoTen VARCHAR(100),
    NgaySinh DATE,
    GioiTinh VARCHAR(10)
);

-- Bảng lớp học
CREATE TABLE Lop (
    MaLop INT PRIMARY KEY,
    TenLop VARCHAR(50)
);

-- Bảng điểm
CREATE TABLE Diem (
    MaDiem INT PRIMARY KEY,
    MaHocSinh INT,
    MaLop INT,
    MonHoc VARCHAR(50),
    Diem FLOAT,
    FOREIGN KEY (MaHocSinh) REFERENCES HocSinh(MaHocSinh),
    FOREIGN KEY (MaLop) REFERENCES Lop(MaLop)
);

-- Bảng lịch học
CREATE TABLE LichHoc (
    MaLichHoc INT PRIMARY KEY,
    MaLop INT,
    MonHoc VARCHAR(50),
    NgayHoc VARCHAR(20),
    GioHoc VARCHAR(20),
    FOREIGN KEY (MaLop) REFERENCES Lop(MaLop)
);
INSERT INTO HocSinh (MaHocSinh, hoten, NgaySinh, GioiTinh)
VALUES
(1,'Nguyễn Văn A', '2005-09-15', 'Nam'),
(2,'Trần Thị B', '2006-03-20', 'Nữ'),
(3,'Lê Văn C', '2005-11-30', 'Nam'),
(4,'Phạm Minh D', '2005-06-05', 'Nam'),
(5,'Đặng Thị E', '2006-01-25', 'Nữ'),
(6,'Bùi Thanh F', '2005-12-12', 'Nam'),
(7,'Cao Thị G', '2005-04-18', 'Nữ');
INSERT INTO Diem (madiem, MaHocSinh, MaLop, MonHoc, Diem)
VALUES
(1, 1, 1, 'Toán', 8.5),
(2, 2, 2, 'Lý', 9.0),
(3, 3, 3, 'Hóa', 7.5),
(4, 4, 4, 'Toán', 6.0),
(5, 5, 5, 'Lý', 7.0),
(6, 6, 6, 'Toán', 8.0),
(7, 7, 7, 'Hóa', 9.0);
INSERT INTO LichHoc (malichhoc, MaLop, MonHoc, NgayHoc, GioHoc)
VALUES
(1,1, 'Toán', 'Thứ Hai', '08:00 - 09:00'),
(2,2, 'Lý', 'Thứ Ba', '09:00 - 10:00'),
(3, 3, 'Hóa', 'Thứ Tư', '10:00 - 11:00'),
(4, 4, 'Toán', 'Thứ Năm', '08:00 - 09:00'),
(5, 5, 'Lý', 'Thứ Sáu', '09:00 - 10:00'),
(6, 6, 'Hóa', 'Thứ Bảy', '10:00 - 11:00'),
(7, 7, 'Toán', 'Chủ Nhật', '08:00 - 09:00');
INSERT INTO Lop (malop,TenLop)
VALUES
(1,'10A1'),
(2,'10A2'),
(3,'10A3'),
(4,'11B1'),
(5,'11B2'),
(6,'12C1'),
(7,'12C2');

SELECT 
    hs.HoTen,
    hs.NgaySinh,
    hs.GioiTinh,
    l.TenLop,
    d.MonHoc,
    d.Diem
FROM 
    HocSinh hs
JOIN 
    Diem d ON hs.MaHocSinh = d.MaHocSinh
JOIN 
    Lop l ON d.MaLop = l.MaLop;
SELECT 
    l.TenLop,
    lh.MonHoc,
    lh.NgayHoc,
    lh.GioHoc
FROM 
    LichHoc lh
JOIN 
    Lop l ON lh.MaLop = l.MaLop;
SELECT 
    hs.HoTen,
    l.TenLop,
    lh.MonHoc,
    lh.NgayHoc,
    lh.GioHoc
FROM 
    HocSinh hs
JOIN 
    Diem d ON hs.MaHocSinh = d.MaHocSinh
JOIN 
    Lop l ON d.MaLop = l.MaLop
JOIN 
    LichHoc lh ON l.MaLop = lh.MaLop;
SELECT 
    hs.HoTen,
    hs.NgaySinh,
    hs.GioiTinh,
    d.MonHoc,
    d.Diem,
    lh.NgayHoc,
    lh.GioHoc
FROM 
    HocSinh hs
JOIN 
    Diem d ON hs.MaHocSinh = d.MaHocSinh
JOIN 
    LichHoc lh ON d.MaLop = lh.MaLop
WHERE 
    d.MaLop = 1;  -- Lọc theo lớp 10A1

