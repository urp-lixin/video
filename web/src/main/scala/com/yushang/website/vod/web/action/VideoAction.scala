package com.yushang.website.vod.web.action

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.action.ServletSupport
import org.beangle.webmvc.api.annotation.{ mapping, param }
import org.beangle.webmvc.api.view.View

import com.yushang.website.vod.core.model.{ Video, VideoStat }
import org.beangle.commons.lang.Strings

class VideoAction extends VodSupport with ServletSupport {

  @mapping("{id}")
  def videoPlayer(@param("id") id: String): View = {
    indexSetting

    val video = entityDao.get(classOf[Video], id.toLong)
    put("video", video)

    loadRecommends(Some(video.nav.id))

    val videoStat = loadVideoStat(video)

    val ip = getRemoteHost

    if (null == videoStat.info(ip)) {
      if (!videoStat.persisted) {
        videoStat.video = video
      }
      videoStat.addIp(ip)

      entityDao.saveOrUpdate(videoStat)
    }

    forward()
  }

  private def loadVideoStat(video: Video): VideoStat = {
    val videoStats = entityDao.search(OqlBuilder.from(classOf[VideoStat], "videoStat").where("videoStat.video = :video", video))
    val videoStat = if (videoStats.isEmpty) new VideoStat else videoStats(0)
    put("videoStat", videoStat)
    videoStat
  }

  @mapping("image/{id}")
  def image(): View = {
    indexSetting
    forward(to(classOf[com.yushang.website.vod.web.action.admin.VideoAction], "image"))
  }

  @mapping("video/{id}")
  def video(): View = {
    indexSetting
    forward(to(classOf[com.yushang.website.vod.web.action.admin.VideoAction], "video"))
  }

  @mapping(value = "goodAjax/{id}", view = "videoStat")
  def goodAjax(@param("id") id: String): View = {
    val video = entityDao.get(classOf[Video], id.toLong)

    val videoStat = loadVideoStat(video)

    val ip = getRemoteHost

    var info = videoStat.info(ip)
    if (!info.give) {
      videoStat.good += 1
      info.give = true

      entityDao.saveOrUpdate(videoStat)
    }

    forward()
  }

  private def getRemoteHost(): String = {
    var ip = request.getHeader("x-forwarded-for");
    if (Strings.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (Strings.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (Strings.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    if (ip.equals("0:0:0:0:0:0:0:1")) "127.0.0.1" else ip;
  }
  
  
  @mapping(value = "badAjax/{id}", view = "videoStat")
  def badAjax(@param("id") id: String): View = {
    val video = entityDao.get(classOf[Video], id.toLong)

    val videoStat = loadVideoStat(video)

    val ip = getRemoteHost

    var info = videoStat.info(ip)
    if (!info.give) {
      videoStat.bad += 1
      info.give = true

      entityDao.saveOrUpdate(videoStat)
    }

    forward()
  }
}