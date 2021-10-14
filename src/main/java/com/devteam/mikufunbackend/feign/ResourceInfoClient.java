package com.devteam.mikufunbackend.feign;

import com.devteam.mikufunbackend.dto.BangumiRespDTO;
import com.devteam.mikufunbackend.dto.SearchAnimeRespDTO;
import feign.Param;
import feign.RequestLine;

public interface ResourceInfoClient {

    @RequestLine("GET /search/anime?keyword={keyword}")
    SearchAnimeRespDTO getAnimeDetailByKeyword(@Param("keyword") String keyword);

    @RequestLine("GET /bangumi/{animeId}")
    BangumiRespDTO getResourceDetailById(@Param("animeId") Integer animeId);

}
