# -*- coding: utf8 -*-
from django.core.exceptions import ObjectDoesNotExist
import csv
from smart_req.models import SubjectStructure, ManagingOrganization


def import_subject_structure(filename, ModelsDict):
    with open(filename, newline='', encoding='utf-8') as csvfile:
        reader = csv.DictReader(csvfile, delimiter=';')
        for row in reader:
            try:
                obj = ModelsDict.objects.get(_id=row['id'])
            except ObjectDoesNotExist:
                obj = ModelsDict()
            for key in row.keys():
                if key == 'id':
                    obj._id = row[key]
                elif key == 'management_organization_id':
                    if row[key]:
                        obj.management_organization_id = ManagingOrganization.objects.filter(_id=row[key]).first()
                else:
                    setattr(obj, key, row[key])
            obj.save()


def import_all(request):
    import_subject_structure('kursk.csv', ManagingOrganization)
    import_subject_structure('export-reestrmkd-46-20190701.csv', SubjectStructure)

