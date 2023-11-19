<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Список статей</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/gh/yegor256/tacit@gh-pages/tacit-css-1.6.0.min.css"/>
</head>

<body>

<h1>Список статей</h1>
<table>
    <tr>
        <th>Название</th>
        <th>Теги</th>
        <th>Комментарии</th>
    </tr>
    <#list articles as article>
    <tr>
        <td>${article.name}</td>
        <td>
            <#list article.tags as tag>
            <p>${tag}</p>
            </#list>
        </td>
        <td>
            <#list article.comments as comment>
            <p>${comment.content}</p>
            </#list>
        </td>
    </tr>
    </#list>
</table>

</body>

</html>