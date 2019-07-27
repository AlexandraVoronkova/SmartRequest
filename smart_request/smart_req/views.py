from django.shortcuts import render


# Create your views here.
def get_requests(request):
    return render(request, 'requests_main.html')


def get_problems(request):
    return render(request, 'problems.html')


def get_templates(request):
    return render(request, 'response_pattern.html')
