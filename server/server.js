var express = require("express")
var app = express()
const PORT = 3000;
var path = require('path')
var formidable = require('formidable');
const fs = require("fs")
const fsPromises = require("fs").promises

const uploadFolder = path.join(__dirname, "uploads");

app.use(express.static('uploads'))
app.use(express.json());

app.get('/', (req, res) => {
    res.send('Hello!')
})

app.post('/upload', function (req, res) {
    let form = formidable({});
    form.keepExtensions = true
    form.multiples = true

    form.uploadDir = uploadFolder // folder do zapisu zdjęcia

    form.parse(req, function (err, fields, files) {

        console.log("----- przesłane formularzem pliki ------");
        console.log(files);

    });

    res.send('upload')
});

app.get('/photosJson', (req, res) => {
    const data = [];
    fs.readdir(__dirname + '/uploads/', (err, files) => {
        if (err) {
            return;
        }
        console.log(files);
        files.forEach(file => {
            console.log(path.join(uploadFolder, file))
            const stats = fs.statSync(path.join(uploadFolder, file))

            let obj = {
                name: file,
                creationTime: stats.birthtimeMs,
                size: stats.size,
                url: "/uploads/" + file,
            }
            console.log(obj)
            data.push(obj)
        });
        res.json(data);
    })
})

app.get('/photo', (req,res) => {
    let imageName = req.query.imgName
    res.sendFile(path.join(uploadFolder, imageName));
})

app.post('/get_files', function (req, res) {

    fs.readdir(__dirname + '/uploads/', function (err, files) {

        if (err) console.log(err)
        else {
            // console.log(files);
            res.send(files)
        }
    })
})

app.listen(PORT, () => {
    console.log(`Example app listening on port ${PORT}`)
})