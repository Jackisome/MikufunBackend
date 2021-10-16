package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.constant.FavoriteStatusEnum;
import com.devteam.mikufunbackend.dto.*;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.feign.ResourceInfoClient;
import com.devteam.mikufunbackend.feign.ResourceClient;
import com.devteam.mikufunbackend.service.serviceInterface.FavoriteService;
import com.devteam.mikufunbackend.service.serviceInterface.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Resource
    private ResourceClient resourceClient;

    @Resource
    private ResourceInfoClient resourceInfoClient;

    @Resource
    private FavoriteService favoriteService;

    @Override
    public List<SearchSubGroupRespVO> getSubgroups() {
        List<SubGroupDTO> subGroupDTOS = resourceClient.getSubgroup().getSubGroups();
        return subGroupDTOS.stream().map(subGroupDTO -> SearchSubGroupRespVO.builder()
                .id(subGroupDTO.getId().toString())
                .name(subGroupDTO.getName())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<SearchResourceTypeRespVO> getResourceTypes() {
        List<ResourceTypeDTO> resourceTypeDTOS = resourceClient.getType().getTypes();
        return resourceTypeDTOS.stream().map(resourceTypeDTO -> SearchResourceTypeRespVO.builder()
                .id(resourceTypeDTO.getId().toString())
                .name(resourceTypeDTO.getName())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<SearchResourceRespVO> getResource(String keyword, Integer typeId, Integer subgroupId) {
        ResourceRespDTO resourceRespDTO = resourceClient.getResource(keyword, typeId, subgroupId);
        return resourceRespDTO.getResources().stream().map(resourceDTO -> {
            SearchResourceRespVO searchResourceRespVO = new SearchResourceRespVO();
            BeanUtils.copyProperties(resourceDTO, searchResourceRespVO);
            // todo
            searchResourceRespVO.setDownloadStatus("undownload");
            return searchResourceRespVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SearchResourceIntroductionVO> getResourceIntroduction(String keyword, Integer resourceId) {
        List<BangumiDetailDTO> bangumiDetailDTOS;
        if (resourceId == 0) {
            bangumiDetailDTOS = this.getResourceInfoByKeyword(keyword);
        } else {
            bangumiDetailDTOS = this.getResourceInfoById(resourceId);
        }
        if (CollectionUtils.isEmpty(bangumiDetailDTOS)) {
            return new ArrayList<>();
        }
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        // 获取番剧收藏状态
        List<Integer> resourceIds = bangumiDetailDTOS.stream().map(BangumiDetailDTO::getResourceId).collect(Collectors.toList());
        Map<Integer, String> statusMap = this.favoriteService.getFavoriteStatusListById(resourceIds);

        return bangumiDetailDTOS.stream().map(bangumiDetailDTO -> {
            SearchResourceIntroductionVO searchResourceIntroductionVO = new SearchResourceIntroductionVO();
            BeanUtils.copyProperties(bangumiDetailDTO, searchResourceIntroductionVO);
            searchResourceIntroductionVO.setResourceId(bangumiDetailDTO.getResourceId().toString());
            // 设置番剧的收藏状态
            String status = statusMap.get(bangumiDetailDTO.getResourceId());
            if (status == null) {
                status = FavoriteStatusEnum.NOT_SET.getValue();
            }
            searchResourceIntroductionVO.setStatus(status);
            // 设定日期
            searchResourceIntroductionVO.setAirDate(bangumiDetailDTO.reformatAirDate(fmt));
            return searchResourceIntroductionVO;
        }).collect(Collectors.toList());
    }

    public List<BangumiDetailDTO> getResourceInfoByKeyword(String keyword) {
        SearchAnimeRespDTO animeRespDTO = resourceInfoClient.getAnimeDetailByKeyword(keyword);
        // 没有查到相关的信息
        if (CollectionUtils.isEmpty(animeRespDTO.getAnimes())) {
            return new ArrayList<>();
        }
        List<SearchAnimeDetailDTO> animeDetailDTOs = animeRespDTO.getAnimes();
        List<BangumiRespDTO> bangumiRespDTOS = animeDetailDTOs.stream()
                .map(animeDetailDTO -> resourceInfoClient.getResourceDetailById(animeDetailDTO.getAnimeId()))
                .collect(Collectors.toList());
        return bangumiRespDTOS.stream().map(BangumiRespDTO::getBangumi).collect(Collectors.toList());
    }

    public List<BangumiDetailDTO> getResourceInfoById(Integer resourceId) {
        List<BangumiDetailDTO> bangumiDetailDTOS = new ArrayList<>();
        BangumiRespDTO bangumiDetailDTO = resourceInfoClient.getResourceDetailById(resourceId);
        if (bangumiDetailDTO == null) {
            return bangumiDetailDTOS;
        }
        bangumiDetailDTOS.add(bangumiDetailDTO.getBangumi());
        return bangumiDetailDTOS;
    }

}

