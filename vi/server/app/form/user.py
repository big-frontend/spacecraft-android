from flask_wtf import FlaskForm
import re
from werkzeug.security import generate_password_hash
from wtforms import PasswordField, StringField
from wtforms import validators, ValidationError
from wtforms.validators import Regexp
from datetime import datetime

from app.storage import user as user_dao
from app.storage.user import User

validator_regx_password = r"^.*(?=.*[a-z])(?=.*[A-Z])(?=.*\d){6,}"


class UserForm(FlaskForm):
    email = StringField('email', [validators.email()])
    password = PasswordField('Password', [])
    username = StringField('Username', [validators.Length(min=1, max=50)])
    role = StringField('role', [])

    def validate_email(self, field):
        if user_dao.get(email=field.data):
            raise ValidationError('Email already register')

    def validate_password(self, field):
        if field.data and not re.match(validator_regx_password, field.data):
            raise ValidationError('密码至少6个字符，至少1个大写字母，1个小写字母，1个数字')

    def form2dict(self):
        return {
            'username': self.username.data,
            'password': generate_password_hash(self.password.data),
            'email': self.email.data,
            'role': self.role.data if self.role.data else '',
            'created_at': datetime.now(),
            'updated_at': datetime.now(),

        }


class RegistrationForm(UserForm):

    def validate_username(self, field):
        """ username muse be unique """
        if User.query.filter(User.username == field.data, User.status != -1).count():
            raise ValidationError('此用户名已经被注册')


class UserUpdateForm(FlaskForm):
    username = StringField('username', [])
    password = PasswordField('Password', [])

    def validate_password(self, field):
        if field.data and not re.match(validator_regx_password, field.data):
            raise ValidationError('密码至少6个字符，至少1个大写字母，1个小写字母，1个数字')


class LoginForm(FlaskForm):
    email = StringField('email', [validators.Length(min=6, max=35),
                                  Regexp(r'^(.+)@(.+)\.(.+)', message='邮箱格式不正确')])
    password = PasswordField('Password', [validators.Length(min=6)])
