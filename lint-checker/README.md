依赖lint-checker的项目，在lint时其依赖项也会被链式的lint
```
 implementation project(':lint-checker')
```
如果只是像指定某一个项目lint，则应该这么引入代码
```
lintChecks project(path: ':lint-rules')
```