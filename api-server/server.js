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
    database: 'quanlytiendien_v3_1', // Your DB name
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
    db.query('SELECT * FROM muc_gia_chi_tiet', (err, result) => {
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

// Get all hoadon
app.get('/hoadon', (req, res) => {
    const sql = 'SELECT * FROM hoadon';

    db.query(sql, (err, result) => {
        if (err) {
            return res.status(500).json({ message: 'Lỗi truy vấn dữ liệu hóa đơn' });
        }

        // Format date fields
        const formatted = result.map(hd => ({
            ...hd,
            tungay: dayjs(hd.tungay).format('DD/MM/YYYY'),
            denngay: dayjs(hd.denngay).format('DD/MM/YYYY'),
            ngaylaphd: dayjs(hd.ngaylaphd).format('DD/MM/YYYY')
        }));

        res.status(200).json(formatted);
    });
});

//get chisocuoi of madk
app.get('/dienke/:madk/chisodau', (req, res) => {
    const madk = req.params.madk;

    const query = `
        SELECT chisocuoi 
        FROM hoadon 
        WHERE madk = ? 
        ORDER BY ngaylapHD DESC 
        LIMIT 1
    `;

    db.query(query, [madk], (err, result) => {
        if (err) {
            console.error('DB error:', err);
            return res.status(500).json({ message: 'Lỗi truy vấn CSDL' });
        }

        if (result.length === 0) {
            // No previous invoice → start from 0
            return res.status(200).json(0);
        }

        const chisodau = result[0].chisocuoi;
        return res.status(200).json(chisodau);
    });
});

// tinh tien dien
app.post('/tinh-tien', (req, res) => {
    const { sokwh } = req.body;

    if (!sokwh || sokwh <= 0) {
        return res.status(400).json({ message: 'Số kWh không hợp lệ' });
    }

    const query = 'SELECT * FROM muc_gia_chi_tiet ORDER BY tu_kwh ASC';

    db.query(query, (err, tiers) => {
        if (err) return res.status(500).json({ message: 'Lỗi truy vấn bảng giá điện' });

        let remaining = sokwh;
        let total = 0;

        for (const tier of tiers) {
            const min = tier.tu_kwh;
            const max = tier.den_kwh ?? Infinity; // handle NULL as no limit
            const rate = parseFloat(tier.don_gia);

            if (remaining <= 0) break;

            const range = Math.min(remaining, max - min + 1);
            total += range * rate;
            remaining -= range;
        }

        res.status(200).json(Math.round(total));
    });
});

// Add new hoadon
app.post('/hoadon', (req, res) => {
    const {
        mahd, madk, ky, tungay, denngay, chisodau, chisocuoi,
        ngaylaphd, tinhtrang
    } = req.body;

    db.query('SELECT * FROM muc_gia_chi_tiet ORDER BY tu_kwh ASC', (err, tiers) => {
        if (err) return res.status(500).json({ message: 'Lỗi lấy bảng giá' });

        let remaining = chisocuoi - chisodau; // số kWh tiêu thụ
        let tongthanhtien = 0;
        let cthoadonList = [];

        for (const tier of tiers) {
            if (remaining <= 0) break;

            const min = tier.tu_kwh;
            const max = tier.den_kwh ?? Infinity;
            const price = parseFloat(tier.don_gia);

            const available = Math.min(remaining, max - min + 1);
            const tien = available * price;

            cthoadonList.push({
                id_mucgia: tier.id_mucgia,
                dntt_bac: available,
                dongia_apdung: price,
                thanhtien_bac: tien
            });

            tongthanhtien += tien;
            remaining -= available;
        }

        // Step 1: insert hoadon
        const insertHoaDon = `
            INSERT INTO hoadon 
            (mahd, madk, ky, tungay, denngay, chisodau, chisocuoi, tongthanhtien, ngaylaphd, tinhtrang)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`;

        db.query(insertHoaDon, [
            mahd, madk, ky, tungay, denngay, chisodau, chisocuoi,
            tongthanhtien, ngaylaphd, tinhtrang
        ], (err) => {
            if (err) return res.status(500).json({ message: 'Lỗi thêm hoadon', error: err });

            // Step 2: insert cthoadon
            const insertCT = `
                INSERT INTO cthoadon (mahd, id_mucgia, dntt_bac, dongia_apdung, thanhtien_bac)
                VALUES ?`;

            const values = cthoadonList.map(ct => [
                mahd, ct.id_mucgia, ct.dntt_bac, ct.dongia_apdung, ct.thanhtien_bac
            ]);

            db.query(insertCT, [values], (err2) => {
                if (err2) return res.status(500).json({ message: 'Lỗi thêm chi tiết hóa đơn', error: err2 });

                res.status(200).json('Thêm hoá đơn thành công');
            });
        });
    });
});

// update khachhang
app.put('/khachhang/:makh', (req, res) => {
    const makh = req.params.makh;
    const { tenkh, diachi, dt, cmnd } = req.body;

    if (!tenkh || tenkh.trim() === '') {
        return res.status(400).json({ message: 'Tên khách hàng không được để trống' });
    }

    const query = `
        UPDATE khachhang 
        SET tenkh = ?, diachi = ?, dt = ?, cmnd = ?
        WHERE makh = ?
    `;

    db.query(query, [tenkh, diachi || null, dt || null, cmnd || null, makh], (err, result) => {
        if (err) {
            console.error('Update error:', err);
            return res.status(500).json({ message: 'Lỗi cập nhật thông tin khách hàng' });
        }

        if (result.affectedRows === 0) {
            return res.status(404).json({ message: 'Không tìm thấy khách hàng' });
        }

        return res.status(200).json({ message: 'Cập nhật thành công' });
    });
});

// delete khachhang
app.delete('/khachhang/:makh', (req, res) => {
    const makh = req.params.makh;

    const query = `DELETE FROM khachhang WHERE makh = ?`;

    db.query(query, [makh], (err, result) => {
        if (err) {
            console.error('Delete error:', err);
            return res.status(500).json({ message: 'Lỗi xóa khách hàng' });
        }

        if (result.affectedRows === 0) {
            return res.status(404).json({ message: 'Không tìm thấy khách hàng để xóa' });
        }

        return res.status(200).json({ message: 'Xóa khách hàng thành công' });
    });
});

// update dienke
app.put('/dienke/:madk', (req, res) => {
    const madk = req.params.madk;
    const { makh, ngaysx, ngaylap, mota, trangthai } = req.body;

    if (!madk || !makh || !ngaysx || !ngaylap || trangthai === undefined) {
        return res.status(400).json({ message: 'Thiếu thông tin cần thiết để cập nhật điện kế' });
    }

    const formattedNgaysx = dayjs(ngaysx, 'DD/MM/YYYY').format('YYYY-MM-DD');
    const formattedNgaylap = dayjs(ngaylap, 'DD/MM/YYYY').format('YYYY-MM-DD');

    const sql = `
        UPDATE dienke
        SET makh = ?, ngaysx = ?, ngaylap = ?, mota = ?, trangthai = ?
        WHERE madk = ?
    `;
    

    db.query(sql, [makh, formattedNgaysx, formattedNgaylap, mota || null, trangthai, madk], (err, result) => {
        if (err) {
            console.error("Lỗi cập nhật:", err);
            return res.status(500).json({ message: 'Lỗi cập nhật thông tin điện kế' });
        }

        if (result.affectedRows === 0) {
            return res.status(404).json({ message: 'Không tìm thấy điện kế' });
        }

        return res.status(200).json({ message: 'Cập nhật điện kế thành công' });
    });
});

// delete dienke
app.delete('/dienke/:madk', (req, res) => {
    const madk = req.params.madk;

    db.query('DELETE FROM dienke WHERE madk = ?', [madk], (err, result) => {
        if (err) {
            console.error("Lỗi khi xóa:", err);
            return res.status(500).json({ message: 'Lỗi khi xóa điện kế' });
        }

        if (result.affectedRows === 0) {
            return res.status(404).json({ message: 'Không tìm thấy điện kế để xóa' });
        }

        return res.status(200).json({ message: 'Xóa điện kế thành công' });
    });
});

app.listen(3000, '0.0.0.0', () => {
    console.log('🚀 Server running on http://0.0.0.0:3000');
});
