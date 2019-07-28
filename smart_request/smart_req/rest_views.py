from .serializers import *
from rest_framework import generics
from .models import *
from rest_framework.response import Response
from rest_framework import status
from djantimat.helpers import PymorphyProc


class CategoryReqListView(generics.ListAPIView):
    queryset = CategoryReq.objects.filter(parent=None)
    serializer_class = CategoryReqSerializer

class CategoryReqView(generics.RetrieveAPIView):
    queryset = CategoryReq.objects.all()
    serializer_class = CategoryReqSerializer

    def get(self, request, *args, **kwargs):
        cats = CategoryReq.objects.filter(parent=kwargs['cat_id'])
        cats_ser = CategoryReqSerializer(cats, many=True).data
        return Response(cats_ser)

class ReqCreateView(generics.CreateAPIView):
    queryset = Request.objects.all()
    serializer_class = ReqSerializer

    def post(self, request, *args, **kwargs):
        data = request.data
        print(data)
        serializer = ReqSerializer(data=data)

        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
