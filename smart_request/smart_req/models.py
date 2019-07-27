from django.db import models
from django.contrib.auth.models import User


class UserReq(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    surname = models.CharField(u"Фамилия", max_length=200, default="")
    name = models.CharField(u"Имя", max_length=200, default="")
    patronymic = models.CharField(u"Отчество", max_length=200, default="")
    email = models.CharField(u"Почта", max_length=200, default="")
    phone = models.CharField(u"Номер телефона", max_length=200, default="")
    address = models.CharField(u"Адрес", max_length=200, default="")

    def __str__(self):
        return self.surname + " " + self.name + " " + self.patronymic


class ManagingOrganization(models.Model):
    id = models.IntegerField(u"ID Управляющей организации на Портале", null=True, blank=True)
    subject_rf = models.CharField(u"Субъект РФ", max_length=200, default="")
    name_full = models.CharField(u"Фирменное наименование юр лица", max_length=200, default="")
    name_short = models.CharField(u"Сокращенное наименование", max_length=200, default="")
    name_employee = models.CharField(u"ФИО руководителя", max_length=200, default="")
    inn = models.CharField(u"ИНН", max_length=200, default="")
    ogrn = models.CharField(u"ОГРН", max_length=200, default="")
    legal_address = models.CharField(u"Адрес юридического лица", max_length=200, default="")
    actual_address = models.CharField(u"Адрес фактического местонахождения органов управления", max_length=200,
                                      default="")
    phone = models.CharField(u"Адрес фактического местонахождения органов управления", max_length=200, default="")
    actual_address = models.CharField(u"Телефон", max_length=200, default="")
    fax = models.CharField(u"Факс", max_length=200, default="")
    email = models.CharField(u"Адрес электронной почты", max_length=200, default="")
    site = models.CharField(u"Официальный сайт ", max_length=200, default="")
    count_mkd = models.CharField(u"Количество домов, находящихся в управлении", max_length=200, default="")
    area_total = models.CharField(u"Площадь домов, находящихся в управлении", max_length=200, default="")
    w_summ = models.CharField(u"Присвоенный рейтинг на дату выгрузки", max_length=200, default="")


class SubjectStructure(models.Model):
    id = models.IntegerField(u"ID дома на Портале", null=True, blank=True)
    region_id = models.IntegerField(u"Субъект РФ (код ФИАС)", null=True, blank=True)
    area_id = models.IntegerField(u"Район (код ФИАС)", null=True, blank=True)
    city_id = models.IntegerField(u"Населенный пункт (код ФИАС)", null=True, blank=True)
    street_id = models.IntegerField(u"Улица (код ФИАС)", null=True, blank=True)
    shortname_region = models.CharField(u"Тип Субъекта РФ", max_length=200, default="")
    formalname_region = models.CharField(u"Субъект РФ", max_length=200, default="")
    shortname_area = models.CharField(u"Тип района", max_length=200, default="")
    formalname_area = models.CharField(u"Район ", max_length=200, default="")
    shortname_city = models.CharField(u"Тип населенного пункта", max_length=200, default="")
    formalname_city = models.CharField(u"Населенный пункт", max_length=200, default="")
    shortname_street = models.CharField(u"Тип улицы", max_length=200, default="")
    formalname_street = models.CharField(u"Улица", max_length=200, default="")
    house_number = models.IntegerField(u"Номер дома", null=True, blank=True)
    building = models.IntegerField(u"Строение", null=True, blank=True)
    block = models.IntegerField(u"Корпус", null=True, blank=True)
    letter = models.CharField(u"Литера", max_length=200, default="")
    address = models.CharField(u"Адрес дома", max_length=200, default="")
    houseguid = models.IntegerField(u"Глобальный уникальный идентификатор дома", null=True, blank=True)
    management_organization_id = models.ForeignKey(ManagingOrganization,
                                                   verbose_name=u"ID Управляющей организации на Портале", blank=True,
                                                   null=True, on_delete=models.CASCADE)
    built_year = models.IntegerField(u"Год постройки", null=True, blank=True)
    exploitation_start_year = models.IntegerField(u"Год ввода в эксплуатацию", null=True, blank=True)
    project_type = models.CharField(u"Серия, тип постройки здания", max_length=200, default="")
    house_type = models.CharField(u"Тип дома", max_length=200, default="")
    is_alarm = models.NullBooleanField(u'Факт признания дома аварийным', blank=True)


class CategoryReq(models.Model):
    name = models.CharField(u'Наименование категории', max_length=200, null=True, blank=True)
    parent = models.ForeignKey('self', verbose_name=u"Подкатегория", blank=True, null=True, on_delete=models.CASCADE)

    def __str__(self):
        return self.name


class Request(models.Model):
    STATUS_CHOICES = ((1, 'В обработке'),
                      (2, 'В работе'),
                      (3, 'На доработке'),
                      (4, 'Выполнена'))
    user_req = models.ForeignKey(UserReq, verbose_name=u"UserInfo", blank=True, null=True, on_delete=models.CASCADE)
    cat = models.ForeignKey(CategoryReq, verbose_name=u"CategoryInfo", blank=True, null=True, on_delete=models.CASCADE)
    status = models.CharField(default=1, max_length=200, choices=STATUS_CHOICES, null=True, blank=True)
    text = models.CharField(u"Текст запроса", max_length=1000, default="")
    date_req = models.DateField(u"Время последнего изменения заявки", null=True, blank=True, auto_now_add=True)
