/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2005, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.yushang.website.vod.core.model

import java.util.Date

import org.beangle.data.model.pojo.Named

import com.yushang.website.vod.core.VideoIndexNo

class Video extends VideoIndexNo with Named {

  var author: String = _

  var description: String = _

  var imageName: String = _

  var imageUrl: String = _

  var videoName: String = _

  var videoUrl: Option[String] = null

  var publishedAt: Date = _

  var nav: Nav = _

  var wwwUrl: Option[String] = null

  var localPath: Option[String] = null
}
