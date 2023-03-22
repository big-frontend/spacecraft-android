import flask
from flask import request, current_app, jsonify
from flask_login import logout_user, login_user, current_user
from flask_restful import Resource, abort
from werkzeug.security import check_password_hash

from app.form.user import LoginForm
from app.storage import user as user_dao
from app.util import l


class PassportAPI(Resource):
    actions = ['login', 'logout']

    def post(self, action):
        """
        /passport/login
        or
        /passport/logout
        :param action:
        :return:
        """
        if action in self.actions:
            self_action = getattr(self, action.lower())
            return self_action()
        else:
            abort(404)

    def login(self):
        form = LoginForm(request.form, csrf=False)
        if form.validate_on_submit():
            email = form.email.data
            pwd = form.password.data
            u = user_dao.query(email=email).first()
            if not u:
                return jsonify({
                    'message': f"登录失败,账号不存在{email} {pwd}"
                })

            if not check_password_hash(u.password, pwd):
                return jsonify({
                    'message': f"登录失败,密码不匹配{email} {pwd}"
                })

            if pwd and check_password_hash(u.password, pwd):
                try:
                    remember = False
                    if current_app.config.get("COOKIE_ENABLE"):
                        remember = True
                    login_user(u, remember=remember)
                    l.i(f'remember me(记住我)功能是否开启,{remember}')
                    current_app.logger.info(f'remember me(记住我)功能是否开启,{remember}')
                    flask.flash('Logged in successfully.')
                except BaseException as e:
                    return "登录失败1"
                return jsonify(current_user.to_json())
                # return flask.redirect(flask.url_for('index'))
            else:
                return "登录失败"
        else:
            return jsonify(form.errors)

    def logout(self):
        logout_user()
        return "退出成功"
