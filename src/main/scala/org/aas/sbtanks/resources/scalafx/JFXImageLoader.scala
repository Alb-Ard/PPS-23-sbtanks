package org.aas.sbtanks.resources.scalafx

import scalafx.scene.image.Image

trait JFXImageLoader:
    def loadFromResources(path: String, tileSize: Double, viewScale: Double): Image

object JFXImageLoader extends JFXImageLoader:
    def loadFromResources(path: String, tileSize: Double, viewScale: Double): Image =
        Image(path, tileSize * viewScale, tileSize * viewScale, true, false)

    extension (loader: JFXImageLoader)
        def loadFromResources(paths: Seq[String], tileSize: Double, viewScale: Double): Seq[Image] =
            paths.map(p => loadFromResources(p, tileSize, viewScale))