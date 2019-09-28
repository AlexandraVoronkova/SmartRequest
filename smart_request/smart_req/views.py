from django.shortcuts import render, redirect
from smart_req.models import Request, Problem


# Create your views here.
def get_requests(request):
    req = Request.objects.all().first()
    return render(request, 'requests_main.html', dict(req=req))


def get_request(request, id_req):
    req = Request.objects.filter(id=id_req).first()
    return render(request, 'request.html', {'req': req})


def get_problems(request):
    return render(request, 'problems.html')


def new_problem(request):
    # req = Request.objects.get(id=req_id)
    # problem = Problem.objects.create(cat=req.cat, address_fias=req.address_fias, address_text=req.address_text)
    # req.problem = problem
    # req.save()
    return redirect('/problems/')


def get_templates(request):
    return render(request, 'response_pattern.html')


def get_analitics(request):
    return render(request, 'analytics.html')


def edit_template(request, template_id):
    return render(request, 'pattern.html')
