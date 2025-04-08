const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');

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
    database: 'quanlytiendien_final' // Your DB name
});

db.connect((err) => {
    if (err) {
        console.error('MySQL connection error:', err);
        return;
    }
    console.log('✅ Connected to MySQL');
});

app.get('/getAllNhanvien', (req, res) => {
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

app.listen(3000, '0.0.0.0', () => {
    console.log('🚀 Server running on http://0.0.0.0:3000');
});
