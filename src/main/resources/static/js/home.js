function sendData() {
    const email = "demo@email.com";
    const password = "demo";
    const data = { email: email, password: password };

    axios.post('/auth/login', data)
        .then(function (response) {
            const jwtToken = response.data.token;
            document.cookie = "jwtToken=" + jwtToken;
            window.location.href = '/dashboard/1';
        })
        .catch(function (error) {
            console.error('Error:', error);
        });
}