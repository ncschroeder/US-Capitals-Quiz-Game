<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin</title>
    <script src="/admin.js" defer></script>
</head>
<body>
    <form method="post" action="/logout">
        <button type="submit">Log out</button>
    </form>

    <h1>Admin</h1>

    <form id="upload-form" method="post" action="/states/upload" enctype="multipart/form-data">
        <label for="file">Upload states CSV:</label>
        <input type="file" id="file" name="file" required>
        <button type="submit">Upload</button>
    </form>
    <div id="upload-result"></div>

    <button id="delete-capitals-btn" type="button">Delete capitals scores</button>
    <button id="delete-states-btn" type="button">Delete states scores</button>
    <div id="delete-result"></div>
</body>
</html>