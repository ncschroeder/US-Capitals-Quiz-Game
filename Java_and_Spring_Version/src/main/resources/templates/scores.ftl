<!DOCTYPE html>
<html lang='en'>
<head>
    <title>${titleAndHeading}</title>
    <meta charset='UTF-8'>
    <link rel='stylesheet' href='styles.css'>
</head>
<body>
    <a href='index.html' class='smallText'>Main Menu</a>
    <h1 class='largeText'>${titleAndHeading}</h1>
    
    <table>
        <thead>
            <tr>
                <th class='mediumText'>Name</th>
                <th class='mediumText'>Score</th>
                <th class='mediumText'>Date and Time</th>
            </tr>
        </thead>
        <tbody>
            <#list scoreDataList as scoreData>
                <tr>
                    <td class='smallText'>${scoreData.name}</td>
                    <td class='smallText'>${scoreData.score}</td>
                    <td class='smallText dateTimeCell'></td>
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