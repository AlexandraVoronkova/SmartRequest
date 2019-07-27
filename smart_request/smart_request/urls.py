"""smart_request URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path
from django.conf.urls import url
from smart_req import rest_views

from smart_req import views

urlpatterns = [
    path('admin/', admin.site.urls),
    url(r'^cats/$', view=rest_views.CategoryReqListView.as_view(), name="categories_list"),
    url(r'^cats/(?P<cat_id>\d+)/$', view=rest_views.CategoryReqView.as_view(), name="categories_children_list"),
    url(r'^req/', view=rest_views.ReqCreateView.as_view(), name="req_post"),
    path('requests/', views.get_requests),
    path('problems/', views.get_problems),
    path('templates/', views.get_templates)
]
