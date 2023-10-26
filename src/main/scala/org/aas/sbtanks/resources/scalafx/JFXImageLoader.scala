package org.aas.sbtanks.resources.scalafx

import scalafx.scene.image.Image

trait JFXImageLoader:
    def loadFromResources(path: String, viewScale: Double): Image

object JFXImageLoader extends JFXImageLoader:
    def loadFromResources(path: String, viewScale: Double): Image =
        Image(path, 16 * viewScale, 16 * viewScale, true, false)

    extension (loader: JFXImageLoader)
        def loadFromResources(paths: Seq[String], viewScale: Double): Seq[Image] =
            paths.map(p => loadFromResources(p, viewScale))