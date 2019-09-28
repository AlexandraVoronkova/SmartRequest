# Generated by Django 2.2.3 on 2019-07-28 10:52

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('smart_req', '0004_auto_20190728_1349'),
    ]

    operations = [
        migrations.AlterField(
            model_name='request',
            name='status',
            field=models.IntegerField(blank=True, choices=[(1, 'В обработке'), (2, 'В работе'), (3, 'На доработке'), (4, 'Выполнена')], default=1, max_length=200, null=True),
        ),
    ]