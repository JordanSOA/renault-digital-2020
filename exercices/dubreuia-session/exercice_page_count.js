var express = require('express')
var session = require('express-session')
var app = express()

app.use(session({
    secret: 'super-secret-key-for-signing-the-cookie-id',
    resave: false,
    saveUninitialized: true
}))

app.use(function (req, res, next) {
    // Ce "middleware" est appelé avant TOUTE requête,
    // ce qui permet de faire des opérations d'initialisation
    // et d'incrémentation...
    if (!req.session.count) {
        // TODO utiliser req.session.count pour initialiser un compteur
        req.session.count = 0;
    }
    req.session.count += 1;
    // TODO incrementer le compteur
    next();
})

app.get('/', function (req, res) {
    // TODO imprimer "vous avez vu cette page x fois" avec x etant le compteur
    res.send("vous avez vu cette page "+ req.session.count +" fois");
})

app.listen(3000, () => {
    console.log(`App started on: http://127.0.0.1:3000`);
});
