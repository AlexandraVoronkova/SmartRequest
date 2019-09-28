package com.smartrequest.ui.navigation

import java.util.HashMap

import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class LocalCiceroneHolder {
	private val containers: HashMap<String, Cicerone<Router>>

	init {
		containers = HashMap()
	}

	fun getCicerone(containerTag: String): Cicerone<Router> {
		if (!containers.containsKey(containerTag)) {
			containers[containerTag] = Cicerone.create()
		}
		return containers[containerTag]!!
	}
}
