const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');

const dayjs = require('dayjs'); //date format
const customParseFormat = require('dayjs/plugin/customParseFormat');
dayjs.extend(customParseFormat);

const app = express();
app.use(cors());
app.use(express.json());

//Encrypt password
const bcrypt = require('bcrypt');
const saltRounds = 10;

// MySQL connection
const db = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '1622003',
    database: 'quanlytiendien_final', // Your DB name
    dateStrings: true
});

db.connect((err) => {
    if (err) {
        console.error('MySQL connection error:', err);
        return;
    }
    console.log('✅ Connected to MySQL');
});

app.get('/nhanvien', (req, res) => {
    db.query('SELECT * FROM nhanvien', (err, results) => {
        if (err) return res.status(500).json({ error: err });
        res.json(results);
    });
});

// Sign up
app.post('/register', (req, res) => {
    const { tendangnhap, hoten, sdt, email, matkhau } = req.body;
    console.log("Received:", req.body);
    db.query('SELECT * FROM nhanvien WHERE tendangnhap = ?', [tendangnhap], (err, isNew) => {
        if (err) return res.status(500).json({ error: err });
        if (isNew.length !== 0) return res.status(409).json({ message: 'Tên nhân viên đã tồn tại' });

        var email1 = email == '' ? null : email;
        var sdt1 = sdt == '' ? null : sdt;

        if (email != '') {
            db.query('SELECT * FROM nhanvien WHERE email = ?', [email], (err, isNew) => {
                if (err) return res.status(500).json({ error: err });
                if (isNew.length !== 0) return res.status(409).json({ message: 'Email đã được sử dụng' });

                register();
            });
        } else register();

        function register() {
            bcrypt.hash(matkhau, saltRounds, (err, matkhau_hash) => {
                if (err) return res.status(500).json({ error: 'Lỗi băm mật khẩu' });

                db.query('INSERT INTO nhanvien (tendangnhap, matkhau_hash, hoten, email, sdt) VALUES (?, ?, ?, ?, ?)',
                    [tendangnhap, matkhau_hash, hoten, email1, sdt1], (err, result) => {
                        if (err) return res.status(500).json({ error: err });
                        return res.status(200).json({ message: 'Đăng ký tài khoản thành công' });
                    }
                );
            });
        }
    });
});

// Login 
app.post('/login', (req, res) => {
    const { tendangnhap, matkhau } = req.body;

    db.query('SELECT * FROM nhanvien WHERE tendangnhap = ?', [tendangnhap], (err, results) => {
        if (err) return res.status(500).json({ error: err });
        if (results.length === 0) return res.status(401).json({ message: 'Tên đăng nhập không đúng' });

        const nhanvien = results[0];

        // Compare the hash
        bcrypt.compare(matkhau, nhanvien.matkhau_hash, (err, isMatch) => {
            if (err) return res.status(500).json({ error: 'Lỗi đối chiếu' });
            if (!isMatch) return res.status(401).json({ message: 'Mật khẩu không hợp lệ' });

            // Auth success
            res.send(nhanvien.manv.toString());
        });
    });
});


// GET all khachhang
app.get('/khachhang', (req, res) => {
    db.query('SELECT * FROM khachhang', (err, results) => {
        if (err) return res.status(500).json({ error: 'Lỗi truy vấn dữ liệu' });
        res.json(results);
    });
});

// GET all dienke
app.get('/dienke', (req, res) => {
    db.query('SELECT * FROM dienke', (err, result) => {
        if (err) return res.status(500).json({ error: 'Lỗi truy vấn dữ liệu' });

        // Format date fields
        const formatted = result.map(item => ({
            ...item,
            ngaysx: item.ngaysx ? dayjs(item.ngaysx).format('DD/MM/YYYY') : null,
            ngaylap: item.ngaylap ? dayjs(item.ngaylap).format('DD/MM/YYYY') : null
        }));
        res.json(formatted);
    });
});

// GET all giadien
app.get('/giadien', (req, res) => {
    db.query('SELECT * FROM giadien', (err, result) => {
        if (err) return res.status(500).json({ error: 'Lỗi truy vấn dữ liệu' });

        // Format date fields
        const formatted = result.map(item => ({
            ...item,
            ngayapdung: item.ngayapdung ? dayjs(item.ngayapdung).format('DD/MM/YYYY') : null,
        }));
        res.json(formatted);
    });
});

// Add new khachhang
app.post('/khachhang', (req, res) => {
    const { makh, tenkh, dt, diachi, cmnd } = req.body;

    db.query('SELECT * FROM khachhang WHERE makh = ?', [makh], (err, results) => {
        if (err) return res.status(500).json({ error: err });
        if (results.length !== 0) return res.status(409).json({ message: 'Mã nhân viên này đã được sử dụng' });

        db.query('SELECT * FROM khachhang WHERE cmnd = ?', [cmnd], (err, results) => {
            if (err) return res.status(500).json({ error: err });
            if (results.length !== 0) return res.status(409).json({ message: 'CMND này đã được sử dụng' });

            db.query('INSERT INTO khachhang (makh, tenkh, dt, diachi, cmnd) VALUES (?, ?, ?, ?, ?)',
                [makh, tenkh, dt, diachi, cmnd], (err, result) => {
                    if (err) return res.status(500).json({ error: err });
                    return res.status(200).json({ message: 'Thêm khách hàng thành công' });
                });
        });
    });
});

// Update khachhang
app.put('/khachhang/:makh', (req, res) => {
    const makh = req.params.makh;
    const { tenkh, dt, diachi, cmnd } = req.body;

    db.query('SELECT * FROM khachhang WHERE cmnd = ?', [cmnd], (err, results) => {
        if (err) return res.status(500).json({ error: err });
        if (results.length !== 0) return res.status(409).json({ message: 'CMND này đã được sử dụng' });

        const query = `
        UPDATE khachhang 
        SET tenkh = ?, dt = ?, diachi = ?, cmnd = ? 
        WHERE makh = ?`;

        db.query(query, [tenkh, dt, diachi, cmnd, makh], (err, result) => {
            if (err) return res.status(500).json({ error: err });

            if (result.affectedRows === 0) {
                return res.status(404).json({ message: 'Không tìm thấy khách hàng' });
            }

            res.status(200).json({ message: 'Cập nhật khách hàng thành công' });
        });
    });
});

// Add new dienke
app.post('/dienke', (req, res) => {
    const { madk, makh, ngaysx, ngaylap, diachi_lapdat, mota, trangthai } = req.body;

    db.query('SELECT * FROM dienke WHERE madk = ?', [madk], (err, results) => {
        if (err) return res.status(500).json({ error: err });
        if (results.length !== 0) return res.status(409).json({ message: 'Mã điện kế đã tồn tại' });

        db.query('SELECT * FROM khachhang WHERE makh = ?', [makh], (err, results) => {
            if (err) return res.status(500).json({ error: err });
            if (results.length === 0) return res.status(404).json({ message: 'Không tìm thấy khách hàng' });
    
            const format = 'DD/MM/YYYY';
            const parsedNgaySX = dayjs(ngaysx, format);
            const parsedNgayLap = dayjs(ngaylap, format);
            // Parse and format with dayjs
            if (!parsedNgaySX.isValid() || !parsedNgayLap.isValid()) {
                return res.status(400).json({ message: 'Ngày không hợp lệ. Định dạng đúng là dd/MM/yyyy' });
            }

            const formattedNgaySX = parsedNgaySX.format('YYYY-MM-DD HH:mm:ss');
            const formattedNgayLap = parsedNgayLap.format('YYYY-MM-DD HH:mm:ss');

            const query = `
                INSERT INTO dienke (madk, makh, ngaysx, ngaylap, diachi_lapdat, mota, trangthai)
                VALUES (?, ?, ?, ?, ?, ?, ?)`;

            db.query(query, [madk, makh, formattedNgaySX, formattedNgayLap, diachi_lapdat || null, mota || null, trangthai || 0], (err, result) => {
                    if (err) return res.status(500).json({ error: err });

                res.status(200).json({ message: 'Thêm điện kế thành công' });
            });
        });
    });
});

app.listen(3000, '0.0.0.0', () => {
    console.log('🚀 Server running on http://0.0.0.0:3000');
});
