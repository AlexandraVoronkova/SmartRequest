from django.shortcuts import render


# Create your views here.
def get_base(request):
    return render(request, 'base.html')
