from app.storage import statement as statement_dao
from app.storage.statement import  Statement
from flask_restful import Resource, abort
from flask import jsonify, request,current_app
import json,os,zipfile
from sqlalchemy.ext.declarative import DeclarativeMeta
from werkzeug.utils import secure_filename
class ApkStatementAPI(Resource):
    bizs = ['apk', 'fwk']
    def put(self,biz):
        """
        /apkanalyzer
        """
        return jsonify(statement_dao.update(biz,request.json))
    def post(self,biz):
        """
        /apkanalyzer
        """
        package = request.files['package']
        package_filename = secure_filename(package.filename)
        package_path = os.path.join(current_app.config['UPLOAD_FOLDER'], f'{biz}_{package_filename}')
        package.save(package_path)
        with zipfile.ZipFile(package_path, 'r') as zip_ref:
            unzip = os.path.join(current_app.config['UPLOAD_FOLDER'],biz)
            if not os.path.exists(unzip):
                os.mkdir(unzip)
            zip_ref.extractall(unzip)

        statement = request.files['statement']
        statement_filename = secure_filename(statement.filename)
        statement.save(os.path.join(current_app.config['UPLOAD_FOLDER'], f'{biz}_{statement_filename}'))
        with open(os.path.join(current_app.config['UPLOAD_FOLDER'],f'{biz}_{statement_filename}'), encoding='utf-8') as f:
            s = json.load(f)
        s = statement_dao.add(biz,s)
        version_code = s.version_code
        s =  jsonify(s)
        return s
    
    def delete(self,biz,version):
        """
        /apkanalyzer/60400
        """
        return jsonify(statement_dao.delete(biz,version))

    def get(self,biz, version=None):
        """
        /apkanalyzer/60400
        """
        if version:
            data = statement_dao.get(biz=biz,version_code=version)
        else:
            data = statement_dao.part_query(Statement.version_code,Statement.version_name,Statement.package,Statement.min_sdk_version,Statement.target_sdk_version,Statement.total_size,biz=biz)
            data = [tuple(row) for row in data]
        return jsonify({
            'code': 200,
            'data': data
        })