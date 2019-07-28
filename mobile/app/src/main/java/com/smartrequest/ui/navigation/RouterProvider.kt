package com.smartrequest.ui.navigation

import ru.terrakok.cicerone.Router

interface RouterProvider {
	fun getRouter(): Router
}
