# -*- coding: utf-8 -*-
from django.urls import path
from django.conf.urls import url
from start import import_models

app_name = 'smart_req'

urlpatterns = [
    path('import/', import_models.import_all),
]



