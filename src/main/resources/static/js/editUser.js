function sendData() {
    const firstName = document.getElementById('InputFirstName').value;
    const lastName = document.getElementById('InputLastName').value;
    const email = document.getElementById('InputEmail').value;
    const password = document.getElementById('user-password').value;


    const data = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
        // Add the profile picture data later
    };

    axios.post('/api/v1/users/' + userId, data)
        .then(function (response) {
            console.log(response.data);
            if (response.status === 200) {
                window.location.href = '/my-collections';
            }
        })
        .catch(function (error) {
            // Handle errors
            console.error('Error:', error);
        });
}
