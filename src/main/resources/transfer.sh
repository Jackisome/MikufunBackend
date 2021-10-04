#!/bin/bash

option="${1}"
case ${option} in
transfer-mkv) fileName="${2}"
  uuid="${3}"
  # -acodec copy -vcodec copy
  ffmpeg -i "${fileName}" -vf subtitles="${fileName}" "/docker/temp/${uuid}.mp4"
  mkdir "/docker/resource/${uuid}"
              ffmpeg -i "/docker/temp/${uuid}.mp4" -profile:v baseline -level 3.0 -start_number 0 -hls_time 10 -hls_list_size 0 -f hls "/docker/resource/${uuid}/index.m3u8"
              rm -rf "/docker/temp/${uuid}.mp4"
;;
transfer-mp4) fileName="${2}"
  uuid="${3}"
  mkdir "/docker/resource/${uuid}"
  ffmpeg -i "${fileName}" -profile:v baseline -level 3.0 -start_number 0 -hls_time 10 -hls_list_size 0 -f hls "/docker/resource/${uuid}/index.m3u8"
;;
delede) fileName="${2}"
  rm -rf "${fileName}"
;;
make-image) fileName="${2}"
  uuid="${3}"
  ffmpeg -ss 00:00:10 -i "${fileName}" -frames:v 1 -y "/docker/image/${uuid}.jpg"
;;
esac