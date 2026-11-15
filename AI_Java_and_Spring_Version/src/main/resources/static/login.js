(function () {
    const params = new URLSearchParams(location.search);
    const messageEl = document.getElementById('message');

    if (params.has('error')) {
        messageEl.textContent = 'Invalid username or password.';
    } else if (params.has('logout')) {
        messageEl.textContent = 'You have been logged out.';
    }
})();
