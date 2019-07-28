from django.shortcuts import render
from smart_req.models import Request


# Create your views here.
def get_requests(request):
    return render(request, 'requests_main.html')


def get_request(request, id_req):
    req = Request.objects.filter(id=id_req).first()
    return render(request, 'request.html', {'req': req})


def get_problems(request):
    return render(request, 'problems.html')


def get_templates(request):
    return render(request, 'response_pattern.html')


def get_analitics(request):
    return render(request, 'analytics.html')


def edit_template(request, template_id):
    return render(request, 'pattern.html')
