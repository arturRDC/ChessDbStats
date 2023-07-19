function sendData() {
    const firstName = document.getElementById('firstName').value;
    const lastName = document.getElementById('lastName').value;
    const email = document.getElementById('inputEmail').value;
    const password = document.getElementById('inputPassword').value;
    const repeatPassword = document.getElementById('repeatPassword').value;

    const data = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        repeatPassword: repeatPassword
    };

    axios.post('/auth/register', data)
        .then(function (response) {
            if (response.status === 200) {
                window.location.href = '/login';
            }
        })
        .catch(function (error) {

            console.error('Error:', error);
        });
}