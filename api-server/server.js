const express = require('express');
const mysql = require('mysql2');
const cors = require('cors');

const app = express();
app.use(cors());
app.use(express.json());

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

app.get('/nhanvien', (req, res) => {
    db.query('SELECT * FROM nhanvien', (err, results) => {
        if (err) return res.status(500).json({ error: err });
        res.json(results);
    });
});

app.listen(3000, () => {
    console.log('🚀 Server running on http://localhost:3000');
});
