from rest_framework import serializers
from .models import *


class CategoryReqSerializer(serializers.ModelSerializer):
    class Meta:
        model = CategoryReq
        fields = ('id', 'name',)


class ReqSerializer(serializers.ModelSerializer):
    class Meta:
        model = Request
        fields = ('user_req', 'cat', 'text',)
