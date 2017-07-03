${(film.title)!}

<#if film.originalTitle??>Оригинальное название: ${film.originalTitle}
</#if><#if film.year??>Год: ${film.year}
</#if><#if film.director??>Режиссер: ${film.director}
</#if><#if film.roles??>В ролях: ${film.roles}
</#if><#if film.genre??>Жанр: ${film.genre}
</#if><#if film.country??>Страна: ${film.country}
</#if><#if film.kpRating??>Рейтинг КР: ${film.kpRating}
</#if><#if film.url??>URL: ${film.url}
</#if>

${(film.description)!}
