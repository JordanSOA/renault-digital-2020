var express = require('express')
var session = require('express-session')
var parseurl = require('parseurl')
var app = express()

app.use(session({
    secret: 'super-secret-key-for-signing-the-cookie-id',
    resave: false,
    saveUninitialized: true
}))

app.use(function (req, res, next) {
    // TODO déclarer / incrémenter les compteurs

    if (!req.session.views){
        req.session.views = {};
    }
    var pathName = parseurl(req).pathname;
    req.session.views[pathName] = (req.session.views[pathName] || 0 ) + 1;
    next();
})

// TODO écrire les routes pour "page1" et "page2"
app.get('/page1', function (req, res) {
    // TODO imprimer "vous avez vu cette page x fois" avec x etant le compteur
    let pageCount = req.session.views['/page1'];
    res.send("vous avez vu cette page "+ pageCount+" fois");
})
app.get('/page2', function (req, res) {
    let pageCount = req.session.views['/page2'];
    res.send("vous avez vu cette page "+ pageCount+" fois");
})
app.listen(3000, () => {
    console.log(`App started on: http://127.0.0.1:3000`);
});