package org.aas.sbtanks.resources.scalafx

import scalafx.scene.image.Image

trait JFXImageLoader:
    def loadFromResources(path: String, tileSizeX: Double, tileSizeY: Double, viewScale: Double): Image

object JFXImageLoader extends JFXImageLoader:
    def loadFromResources(path: String, tileSizeX: Double, tileSizeY: Double, viewScale: Double): Image =
        Image(path, tileSizeX * viewScale, tileSizeY * viewScale, true, false)

    extension (loader: JFXImageLoader)
        def loadFromResources(path: String, tileSize: Double, viewScale: Double): Image =
            loader.loadFromResources(path, tileSize, tileSize, viewScale)

        def loadFromResources(paths: Seq[String], tileSizeX: Double, tileSizeY: Double, viewScale: Double): Seq[Image] =
            paths.map(p => loader.loadFromResources(p, tileSizeX, tileSizeY, viewScale))

        def loadFromResources(paths: Seq[String], tileSize: Double, viewScale: Double): Seq[Image] =
            loader.loadFromResources(paths, tileSize, tileSize, viewScale)