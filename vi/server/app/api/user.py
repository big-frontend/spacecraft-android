import json

from flask import jsonify, request
from flask_restful import Resource

from app.form.user import RegistrationForm
from app.storage import user as user_dao

from sqlalchemy.ext.declarative import DeclarativeMeta


class AlchemyEncoder(json.JSONEncoder):

    def default(self, obj):
        if isinstance(obj.__class__, DeclarativeMeta):
            # an SQLAlchemy class
            fields = {}
            for field in [x for x in dir(obj) if not x.startswith('_') and x != 'metadata']:
                data = obj.__getattribute__(field)
                try:
                    json.dumps(data)  # this will fail on non-encodable values, like other classes
                    fields[field] = data
                except TypeError:
                    fields[field] = None
            # a json-encodable dict
            return fields

        return json.JSONEncoder.default(self, obj)


class UserApi(Resource):
    def get(self, user_id=None):
        # print(json.dumps(User.query.all(),cls= AlchemyEncoder))
        return jsonify({
            'code': 1,
            'data': user_dao.get(id=user_id) if user_id else user_dao.get()
        })

    def post(self, user_id=None, action=None):
        form = RegistrationForm(request.form, csrf=False)
        # 校验
        if form.validate_on_submit():
            user_info = form.form2dict()
            u = user_dao.add(**user_info)
            return jsonify(u)
        return jsonify(form.errors)

    def put(self, user_id, action=None):
        return "post"

    def delete(self, user_id):
        ret = user_dao.remove(id=user_id)
        return "删除成功" if ret == 1 else "删除失败"
