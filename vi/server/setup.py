# -*- coding: utf-8 -*-
from setuptools import find_packages, setup

# python setup.py install
setup(
    name='viserver',
    version='1.0.0',
    packages=find_packages(include=['app']),
    include_package_data=True,
    zip_safe=False,
    install_requires=[
        'flask',
    ],

    author="chenjinfa",
    author_email="chenjinfa@oppo.com",
    description="1234",
    long_description='123456',
    license="Apache2",
    # keywords="",
    # url='',
    classifiers=[
        # How mature is this project? Common values are
        #   3 - Alpha
        #   4 - Beta
        #   5 - Production/Stable
        'Development Status :: 5 - Production/Stable',

        # Pick your license as you wish (should match "license" above)
        'License :: OSI Approved :: Apache Software License',

        # Specify the Python versions you support here. In particular, ensure
        # that you indicate whether you support Python 2, Python 3 or both.
        'Programming Language :: Python :: 2.7',
        'Programming Language :: Python :: 3',
        'Programming Language :: Python :: 3.3',
        'Programming Language :: Python :: 3.4',
        'Programming Language :: Python :: 3.5',
        'Programming Language :: Python :: 3.6',
    ],
    entry_points={
        'console_scripts': [
            'app=app:create_app'
        ],
        'flask.commands': [
            'my-command=extension.commands:cli'
        ],
    }
)
