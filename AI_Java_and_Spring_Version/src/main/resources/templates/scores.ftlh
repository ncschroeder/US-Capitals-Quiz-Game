<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${heading}</title>
    <link rel="icon" type="image/svg+xml" href="/favicon.svg">
    <link rel="stylesheet" href="/common.css">
    <link rel="stylesheet" href="/scores.css">
</head>
<body>
    <a id="home-link" href="/">Back to the home page</a>
    <h1>${heading}</h1>
    <#if scores?size == 0>
        <p>No scores yet.</p>
    <#else>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Score</th>
                    <th>Submitted at</th>
                </tr>
            </thead>
            <tbody>
                <#list scores as score>
                    <tr>
                        <td>${score.name}</td>
                        <td>${score.score}</td>
                        <td class="submitted-at"></td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </#if>
    <script id="submitted-ats-data" type="application/json">${submittedAtsJson?no_esc}</script>
    <script src="/scores.js"></script>
</body>
</html>