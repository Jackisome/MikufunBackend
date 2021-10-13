package com.devteam.mikufunbackend.service.serviceInterface;

import com.devteam.mikufunbackend.entity.SearchResourceIntroductionVO;
import com.devteam.mikufunbackend.entity.SearchResourceRespVO;
import com.devteam.mikufunbackend.entity.SearchSubGroupRespVO;
import com.devteam.mikufunbackend.entity.SearchResourceTypeRespVO;

import java.util.List;

public interface SearchService {

    List<SearchSubGroupRespVO> getSubgroups();

    List<SearchResourceTypeRespVO> getResourceTypes();

    List<SearchResourceRespVO> getResource(String keyword, Integer typeId,
                                           Integer subgroupId);

    List<SearchResourceIntroductionVO> getResourceIntroduction(String keyword, Integer resourceId);

}
