const express = require('express');
const session = require('express-session');
const bodyParser = require('body-parser');
const app = express();

app.use(session({
    secret: 'super-secret-key-for-signing-the-cookie-id',
    saveUninitialized: true,
    resave: true
}));
// This is used for POST using json encoded body
app.use(bodyParser.json());
// This is used for POST from basic HTML forms <form><input stuff></input></form>
app.use(bodyParser.urlencoded({ extended: true }));

app.get('/', (req, res) => {
    res.send('<form action="/login" method="POST">\n' +
        '    <input type="text" placeholder="username" name="username"><br/>\n' +
        '    <input type="password" placeholder="password" name="password"><br/>\n' +
        '    <input type="submit" value="Submit" id="submit">\n' +
        '</form>');
});

app.post('/login', (req, res) => {
    // TODO verifier le login
    if (req.body.username == "admin" && req.body.password == "password") {
        req.session.username = req.body.username;
        req.session.password = req.body.password;
        req.session.isLogged = true;
        res.redirect('/admin');
    } else {

        res.send(`<p>Wrong Crendtials, please try to <a href="/">Login</a> again</p>`)
    }
});

app.get('/admin', (req, res) => {
    // TODO afficher du contenu sur l'utilisateur loggué
    if (!req.session.isLogged) {
        res.send(`<p> Please <a href="/">Login</a> before visit</p>`)
    } else {
        res.send(`<div>
        <h2> Hello ${req.session.username} ! </h2>
        <button><a href="/logout" style="text-decoration: none;color: black;">Log Out</a></button>
        </div>`);

    }
});

app.get('/logout', (req, res) => {
    // TODO supprimer la session de l'utilisateur
    req.session.destroy();
    res.redirect('/');
});

app.listen(3000, () => {
    console.log(`App started on: http://127.0.0.1:3000`);
});