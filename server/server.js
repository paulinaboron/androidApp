var express = require("express")
var app = express()
const PORT = 3000;
var path = require('path')
var formidable = require('formidable');
const fs = require("fs")
const fsPromises = require("fs").promises

app.use(express.static('uploads'))
app.use(express.json());

app.get('/', (req, res) => {
  res.send('Hello!')
})

app.post('/upload', function (req, res) {
    let form = formidable({});
    form.keepExtensions = true
    form.multiples = true

    form.uploadDir = __dirname + '/uploads/' // folder do zapisu zdjęcia

    form.parse(req, function (err, fields, files) {

        console.log("----- przesłane formularzem pliki ------");
        console.log(files);

    });

    res.send('upload')
});

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