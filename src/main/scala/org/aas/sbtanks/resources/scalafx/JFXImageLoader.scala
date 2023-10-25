package org.aas.sbtanks.resources.scalafx

import scalafx.scene.image.Image

object JFXImageLoader:
    def loadFromResources(path: String, viewScale: Double) =
        Image(path, 16 * viewScale, 16 * viewScale, true, false)
