<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
    <link rel="icon" type="image/svg+xml" href="/favicon.svg">
    <link rel="stylesheet" href="/common.css">
    <link rel="stylesheet" href="/admin.css">
    <script src="/admin.js" defer></script>
</head>
<body>
    <a id="home-link" href="/">Back to the home page</a>

    <form class="logout-form" method="post" action="/logout">
        <button type="submit">Log out</button>
    </form>

    <h1>Admin</h1>

    <section class="section">
        <h2>Upload states</h2>
        <form id="upload-form" method="post" action="/states/upload" enctype="multipart/form-data">
            <label for="file">Upload states CSV:</label>
            <input type="file" id="file" name="file" required>
            <button type="submit">Upload</button>
        </form>
        <div id="upload-result"></div>
    </section>

    <section class="section">
        <h2>Delete scores</h2>
        <div class="button-row">
            <button id="delete-capitals-btn" type="button">Delete capitals scores</button>
            <button id="delete-states-btn" type="button">Delete states scores</button>
        </div>
        <div id="delete-result"></div>
    </section>

    <section class="section">
        <h2>View states data</h2>
        <div class="button-row">
            <button id="states-from-db-btn" type="button">Load states from database</button>
            <button id="states-from-service-btn" type="button">Load states from state service</button>
            <button id="markdown-rows-btn" type="button">Load states table rows markdown</button>
        </div>
    </section>

    <div id="states-tables"></div>
</body>
</html>