function hashPassword() {
    var password = document.login.j_password;
    password.value = CryptoJS.SHA256(password.value);
    return true;
}
