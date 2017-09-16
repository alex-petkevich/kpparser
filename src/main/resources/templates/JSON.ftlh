{
   "title": ${(film.title)!},
<#if film.originalTitle??>"originalTitle": ${film.originalTitle},
</#if><#if film.year??>"year": ${film.year},
</#if><#if film.director??>"director": ${film.director},
</#if><#if film.roles??>"roles": ${film.roles},
</#if><#if film.genre??>"genre": ${film.genre},
</#if><#if film.country??>"country": ${film.country},
</#if><#if film.kpRating??>"kpRating": ${film.kpRating},
</#if><#if film.url??>"url": ${film.url},
</#if>"description": ${(film.description)!}
}
