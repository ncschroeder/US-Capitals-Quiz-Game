<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="utf-8" />
    <title>${titleAndHeading}</title>
    <link rel="icon" href="us-favicon.png" type="image/x-icon" />
    <link rel="stylesheet" href="common.css">
    <style>
        h1 {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <a href="/" id="top-left-home-link">Home</a>
    <h1>${titleAndHeading}</h1>

    <#-- 
    Make this table start out invisible since the date time cells start out empty and JavaScript fills them.
    If this table started out visible then it would start out being a certain size for a short time and those
    cells would get filled and this table would get resized. This resizing is sometimes visible to a user.
    I'd rather have the table start out invisible and then appear after the cells get filled.
    -->
    <table id="scores-table" style="visibility: hidden;">
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
                    <td class="date-time-cell"></td>
                </tr>
            </#list>
        </tbody>
    </table>

    <script id="score-epoch-milliseconds-json-script" type="application/json">
        ${scoreEpochMillisecondsJson}
    </script>
    <script src="scores.js"></script>
</body>
</html>