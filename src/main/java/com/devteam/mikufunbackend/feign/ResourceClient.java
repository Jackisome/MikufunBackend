package com.devteam.mikufunbackend.feign;

import com.devteam.mikufunbackend.dto.*;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.net.URI;

public interface ResourceClient {

    @RequestLine("GET /subgroup")
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 Edg/94.0.992.37")
    SubGroupRespDTO getSubgroup();

    @RequestLine("GET /subgroup")
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 Edg/94.0.992.37")
    SubGroupRespDTO getSubgroup(URI uri);

    @RequestLine("GET /type")
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 Edg/94.0.992.37")
    ResourceTypeRespDTO getType();

    @RequestLine("GET /type")
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 Edg/94.0.992.37")
    ResourceTypeRespDTO getType(URI uri);

    @RequestLine("GET /list?keyword={keyword}&subgroup={subgroup}&type={type}")
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 Edg/94.0.992.37")
    ResourceRespDTO getResource(@Param("keyword") String keyword,
                                @Param("subgroup") Integer subgroupId, @Param("type") Integer typeId);

    @RequestLine("GET /list?keyword={keyword}&subgroup={subgroup}&type={type}")
    @Headers("user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.61 Safari/537.36 Edg/94.0.992.37")
    ResourceRespDTO getResource(URI uri, @Param("keyword") String keyword,
                                @Param("subgroup") Integer subgroupId, @Param("type") Integer typeId);

}
