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
        <tr>
            <th class='mediumText'>Name</th>
            <th class='mediumText'>Score</th>
        </tr>
        <#list scores as score>
            <tr>
                <td class='smallText'>${score.name}</td>
                <td class='smallText'>${score.score}</td>
            </tr>
        </#list>
    </table>
</body>
</html>