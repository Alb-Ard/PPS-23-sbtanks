package org.aas.sbtanks.entities.bullet

import org.aas.sbtanks.entities.bullet.view.BulletView

class MockBulletView() extends BulletView:
    override def look(rotation: Double) = ()
    override def move(x: Double, y: Double) = ()