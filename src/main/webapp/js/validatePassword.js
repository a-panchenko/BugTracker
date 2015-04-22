function validatePassword(password, retypePassword) {
    if (password != retypePassword) {
        alert('Passwords does not match');
        return false;
    }
}
