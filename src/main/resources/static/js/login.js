function sendData() {
    const email = document.getElementById('inputEmail').value;
    const password = document.getElementById('inputPassword').value;
    const data = { email: email, password: password };

    axios.post('/auth/login', data)
        .then(function (response) {
            const jwtToken = response.data.token;
            document.cookie = "jwtToken=" + jwtToken;
            window.location.href = '/my-collections';
        })
        .catch(function (error) {
            document.getElementById('inputEmail').classList.add("is-invalid");
            document.getElementById('inputPassword').classList.add("is-invalid");
            console.error('Error:', error);
        });
}