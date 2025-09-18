<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='UTF-8'>
    <title>${titleAndHeading}</title>
    <link rel='stylesheet' href='common.css'>
</head>
<body>
    <a href='/'>Main Menu</a>
    <h1>${titleAndHeading}</h1>
    
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Score</th>
                <th>Date and Time</th>
            </tr>
        </thead>
        <tbody>
            <#list scoreDataList as scoreData>
                <tr>
                    <td>${scoreData.name}</td>
                    <td>${scoreData.score}</td>
                    <td class='dateTimeCell'></td>
                </tr>
            </#list>
        </tbody>
    </table>

    <script id='dateTimeStringsJsonScript' type='application/json'>
        ${dateTimeStringsJson}
    </script>
    <script src='scores.js'></script>
</body>
</html>