package com.devteam.mikufunbackend.service.serviceImpl;

import com.devteam.mikufunbackend.dto.*;
import com.devteam.mikufunbackend.entity.*;
import com.devteam.mikufunbackend.feign.ResourceInfoClient;
import com.devteam.mikufunbackend.feign.ResourceClient;
import com.devteam.mikufunbackend.service.serviceInterface.FavoriteService;
import com.devteam.mikufunbackend.service.serviceInterface.SearchService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        // convert
        return bangumiDetailDTOS.stream().map(bangumiDetailDTO -> {
            SearchResourceIntroductionVO searchResourceIntroductionVO = new SearchResourceIntroductionVO();
            BeanUtils.copyProperties(bangumiDetailDTO, searchResourceIntroductionVO);
            searchResourceIntroductionVO.setResourceId(bangumiDetailDTO.getResourceId().toString());
            String status = favoriteService.getFavoriteStatus(bangumiDetailDTO.getResourceId());
            searchResourceIntroductionVO.setStatus(status);
            if (CollectionUtils.isEmpty(bangumiDetailDTO.getEpisodes())) {
                return searchResourceIntroductionVO;
            }
            // todo
            try {
                Date airDate = fmt.parse(bangumiDetailDTO.getEpisodes().get(0).getAirDate());
                searchResourceIntroductionVO.setAirDate(fmt.format(airDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return searchResourceIntroductionVO;
        }).collect(Collectors.toList());
    }

    public List<BangumiDetailDTO> getResourceInfoByKeyword(String keyword) {
        SearchAnimeRespDTO animeRespDTO = resourceInfoClient.getAnimeDetailByKeyword(keyword);
        List<SearchAnimeDetailDTO> animeDetailDTOs = animeRespDTO.getAnimes();
        List<BangumiRespDTO> bangumiRespDTOS = animeDetailDTOs.stream()
                .map(animeDetailDTO -> resourceInfoClient.getResourceDetailById(animeDetailDTO.getAnimeId()))
                .collect(Collectors.toList());
        return bangumiRespDTOS.stream().map(BangumiRespDTO::getBangumi).collect(Collectors.toList());
    }

    public List<BangumiDetailDTO> getResourceInfoById(Integer resourceId) {
        List<BangumiDetailDTO> bangumiDetailDTOS = new ArrayList<>();
        bangumiDetailDTOS.add(resourceInfoClient.getResourceDetailById(resourceId).getBangumi());
        return bangumiDetailDTOS;
    }


}

